package yunnex.pep.common.util.excel;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Excel导出设置
 */
@Data
@Accessors(chain = true)
public class ExportSettings {

    private int windowSize = 300;
    // 列宽
    private int columnWidth = 256;
    // 头部起始行
    private int headerStartRow = 0;
    // 主体起始行
    private int bodyStartRow = 1;

    // 边框颜色
    private short borderColor = HSSFColor.GREY_80_PERCENT.index;
    // 头部边框大小
    private short headerBoldWeight = Font.BOLDWEIGHT_BOLD;
    // 头部字体对齐方式
    private short headerAlignment = CellStyle.ALIGN_CENTER;
    // 主体边框大小
    private short bodyBoldWeight = Font.BOLDWEIGHT_NORMAL;
    // 主体字体对齐方式
    private short bodyAlignment = CellStyle.ALIGN_GENERAL;

    // 要导出的列
    private List<Field> exportFields;
    private CellStyle headerCellStyle;
    private CellStyle bodyCellStyle;

}
