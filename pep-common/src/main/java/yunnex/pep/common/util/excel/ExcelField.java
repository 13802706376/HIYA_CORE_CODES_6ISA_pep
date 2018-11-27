package yunnex.pep.common.util.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel列注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

    /**
     * 标题
     */
    String title();

    /**
     * 排序
     */
    int sort() default 0;

    /**
     * 分组（根据分组导出）
     */
    String[] groups() default {};

}
