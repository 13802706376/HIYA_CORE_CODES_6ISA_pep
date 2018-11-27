package yunnex.pep.common.util.excel;

import java.lang.reflect.Field;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import lombok.extern.slf4j.Slf4j;
import yunnex.pep.common.exception.ExcelException;


import static yunnex.pep.common.constant.Constant.Num.ZERO;

/**
 * Excel导出默认实现类
 */
@Slf4j
public class ExportExcel extends ExportTemplate {

    /**
     * 表头
     */
    @Override
    protected void buildHeader() {
        Row titleRow = sheet.createRow(settings.getHeaderStartRow());
        for (int i = 0; i < settings.getExportFields().size(); i++) {
            Field field = settings.getExportFields().get(i);
            String title = field.getAnnotation(ExcelField.class).title();
            Cell cell = titleRow.createCell(i);
            cell.setCellValue(title);
            cell.setCellStyle(settings.getHeaderCellStyle());
        }
    }

    /**
     * 主体
     */
    @Override
    protected void buildBody() {
        checkDataNotEmpty();
        try {
            for (int i = 0; i < getData().size(); i++) {
                Object obj = getData().get(i);
                if (obj == null) {
                    continue;
                }
                int columnWidth = ZERO;
                Row row = sheet.createRow(i + settings.getBodyStartRow());
                for (int j = 0; j < settings.getExportFields().size(); j++) {
                    Field field = settings.getExportFields().get(j);
                    field.setAccessible(true);
                    Cell cell = row.createCell(j);
                    cell.setCellStyle(settings.getBodyCellStyle());
                    setCellValue(cell, field.getType(), field.get(obj));
                    columnWidth = calcColWidth(cell, columnWidth);
                }
                sheet.setColumnWidth(i, columnWidth * settings.getColumnWidth());
            }
        } catch (IllegalAccessException e) {
            throw new ExcelException(MSG_FAIL);
        }
    }

}
