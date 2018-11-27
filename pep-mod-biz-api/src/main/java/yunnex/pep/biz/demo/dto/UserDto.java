package yunnex.pep.biz.demo.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import yunnex.pep.common.base.BaseDto;
import yunnex.pep.common.util.excel.ExcelField;
import yunnex.pep.common.util.validate.Default;
import yunnex.pep.common.util.validate.Validate;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
public class UserDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    @ExcelField(title = "姓名", sort = 3, groups = "abc")
    @Length(min = 3, message = "长度不能小于3个字符", groups = Default.class)
    private String name;

    /**
     * 年龄
     */
    @ExcelField(title = "年龄", sort = 1, groups = { "abc", "xyz" })
    @Range(min = 0, max = 150, message = "年龄必须介于0到150之间", groups = { Default.class, Validate.class })
    private Integer age;

    /**
     * 邮箱
     */
    @ExcelField(title = "邮箱", sort = 4, groups = { "abc", "xyz" })
    @Email(message = "邮箱格式不正确", groups = Default.class)
    private String email;

    /**
     * 乐观锁版本号
     */
    @ExcelField(title = "版本号", sort = 2)
    private Long version;

    /**
     * 策略
     */
    private String strategy;

    @ExcelField(title = "月薪", sort = 6)
    private Double salary;

    @ExcelField(title = "生日", sort = 5, groups = "abc")
    private LocalDateTime birth;

}
