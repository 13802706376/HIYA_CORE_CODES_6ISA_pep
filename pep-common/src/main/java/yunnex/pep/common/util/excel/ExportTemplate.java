package yunnex.pep.common.util.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import yunnex.pep.common.constant.Constant;
import yunnex.pep.common.exception.ExcelException;
import yunnex.pep.common.util.DateUtil;
import yunnex.pep.common.util.WebUtils;


import static yunnex.pep.common.constant.Constant.Num.ZERO;

/**
 * Excel导出模板类。优先使用ExportSettings进行配置，不满足需要时重写对应的方法。
 */
@Slf4j
@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public abstract class ExportTemplate {

    public static final String MSG_FAIL = "Excel导出异常！";

    protected ExportSettings settings = new ExportSettings();

    protected SXSSFWorkbook workbook;
    protected Sheet sheet;

    private List<?> data;
    private String group;
    private String filename;

    /**
     * 使用默认设置
     */
    protected ExportTemplate() {
        this(new ExportSettings());
    }

    /**
     * 自定义设置
     *
     * @param settings
     */
    protected ExportTemplate(ExportSettings settings) {
        workbook = new SXSSFWorkbook(settings.getWindowSize());
        sheet = workbook.createSheet();
    }

    /**
     * 模板方法。根据设置导出Excel到指定位置。
     *
     * @param os
     * @throws IOException
     */
    protected void export(OutputStream os) throws IOException {
        calcExportFields();
        setHeaderCellStyle();
        buildHeader();
        setBodyCellStyle();
        buildBody();
        workbook.write(os);
    }

    /**
     * 导出Excel到本地文件系统
     *
     * @param data
     * @param filename 文件完整路径
     */
    public void exportToFile(List<?> data, String filename) {
        exportToFile(data, null, filename);
    }

    /**
     * 按分组导出Excel到本地文件系统。
     *
     * @param data
     * @param group
     * @param filename 文件完整路径
     */
    public void exportToFile(List<?> data, String group, String filename) {
        setExportInfo(data, group, filename);

        try (OutputStream os = new FileOutputStream(getFilename())) {
            export(os);
        } catch (IOException e) {
            log.error(MSG_FAIL, e);
            throw new ExcelException(e);
        }
    }

    /**
     * 导出Excel到客户端（下载）
     *
     * @param data
     * @param filename 文件名加后缀
     * @param response
     */
    public void exportToClient(List<?> data, String filename, HttpServletResponse response) {
        exportToClient(data, null, filename, response);
    }

    /**
     * 按分组导出Excel到客户端（下载）
     *
     * @param data
     * @param group
     * @param filename 文件名加后缀
     * @param response
     */
    public void exportToClient(List<?> data, String group, String filename, HttpServletResponse response) {
        setExportInfo(data, group, filename);

        try (OutputStream os = response.getOutputStream()) {
            WebUtils.fileDownloadHeader(response, getFilename());
            export(os);
        } catch (IOException e) {
            log.error(MSG_FAIL, e);
            throw new ExcelException(e);
        }
    }

    /**
     * 计算要导出的列
     */
    protected void calcExportFields() {
        if (settings.getExportFields() != null) {
            return;
        }

        checkDataNotEmpty();
        Class<?> clazz = getData().get(ZERO).getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null) {
            log.warn("类{}未声明属性！", clazz);
            throw new ExcelException("未指定导出列！");
        }
        // 要导出的列
        List<Field> exportFields = new ArrayList<>();
        Arrays.stream(fields).forEach(field -> {
            ExcelField excelField = field.getAnnotation(ExcelField.class);
            if (excelField == null) {
                return;
            }
            // 没有指定分组时，全部校验
            if (StringUtils.isBlank(getGroup())) {
                exportFields.add(field);
            }
            // 指定分组并且匹配时，校验
            else if (Arrays.stream(excelField.groups()).anyMatch(_group -> _group.equals(getGroup()))) {
                exportFields.add(field);
            }
        });
        if (CollectionUtils.isEmpty(exportFields)) {
            log.warn("没有声明@ExcelField注解或者没有匹配的分组！");
            throw new ExcelException("未指定导出列！");
        }
        // 排序
        exportFields.sort(fieldSortComparator());
        settings.setExportFields(exportFields);
    }

    /**
     * 构建头部
     */
    protected abstract void buildHeader();

    /**
     * 构建主体（数据）
     */
    protected abstract void buildBody();

    /**
     * 头部样式
     */
    protected void setHeaderCellStyle() {
        if (settings.getHeaderCellStyle() == null) {
            settings.setHeaderCellStyle(defaultCellStyle(settings.getHeaderBoldWeight(), settings.getHeaderAlignment()));
        }
    }

    /**
     * 主体样式
     */
    protected void setBodyCellStyle() {
        if (settings.getBodyCellStyle() == null) {
            settings.setBodyCellStyle(defaultCellStyle(settings.getBodyBoldWeight(), settings.getBodyAlignment()));
        }
    }

    protected void setExportInfo(List<?> data, String group, String filename) {
        if (StringUtils.isBlank(filename)) {
            throw new ExcelException("Excel文件名不能为空！");
        }

        this.data = data;
        this.group = group;
        this.filename = filename;
    }

    protected void checkDataNotEmpty() {
        if (CollectionUtils.isEmpty(getData())) {
            throw new ExcelException("数据为空！");
        }
    }

    /**
     * 单元格样式
     *
     * @return
     */
    protected CellStyle defaultCellStyle(short boldWeight, short alignment) {
        CellStyle cellStyle = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setBoldweight(boldWeight);
        cellStyle.setFont(font);
        cellStyle.setAlignment(alignment);

        short borderColor = settings.getBorderColor();
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(borderColor);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(borderColor);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(borderColor);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(borderColor);
        return cellStyle;
    }

    /**
     * 计算列宽
     *
     * @param cell
     * @param columnWidth
     * @return
     */
    protected int calcColWidth(Cell cell, int columnWidth) {
        if (HSSFCell.CELL_TYPE_STRING == cell.getCellType()) {
            int length = cell.getStringCellValue().getBytes().length;
            if (columnWidth < length) {
                columnWidth = length;
            }
        }
        return columnWidth;
    }

    /**
     * 设置单元格的值
     *
     * @param cell
     * @param type
     * @param value
     */
    protected void setCellValue(Cell cell, Class<?> type, Object value) {
        if (value == null) {
            cell.setCellValue(Constant.Symbol.EMPTY);
            return;
        }

        String typeName = type.getSimpleName();
        if (String.class.getSimpleName().equalsIgnoreCase(typeName)) {
            cell.setCellValue(String.valueOf(value));
        } else if (Integer.class.getSimpleName().equalsIgnoreCase(typeName)) {
            cell.setCellValue(Integer.parseInt(value.toString()));
        } else if (Long.class.getSimpleName().equalsIgnoreCase(typeName)) {
            cell.setCellValue(Long.parseLong(value.toString()));
        } else if (Double.class.getSimpleName().equalsIgnoreCase(typeName)) {
            cell.setCellValue(Double.parseDouble(value.toString()));
        } else if (Date.class.getSimpleName().equalsIgnoreCase(typeName)) {
            cell.setCellValue(DateFormatUtils.format((Date) value, Constant.DateFormat.DATE_TIME));
        } else if (LocalDateTime.class.getSimpleName().equalsIgnoreCase(typeName)) {
            cell.setCellValue(DateUtil.formatDateTime((LocalDateTime) value));
        } else {
            cell.setCellValue(value.toString());
        }
    }

    /**
     * 按 ExcelField 注解的 sort 属性值排序
     *
     * @return
     */
    protected Comparator<Field> fieldSortComparator() {
        return (Field f1, Field f2) -> {
            ExcelField ef1 = f1.getAnnotation(ExcelField.class);
            ExcelField ef2 = f2.getAnnotation(ExcelField.class);
            if (ef1 != null && ef2 != null) {
                return Integer.compare(ef1.sort(), ef2.sort());
            }
            return ZERO;
        };
    }

}
