package yunnex.pep.biz.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import yunnex.pep.common.base.BaseEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author mybatis-plus
 * @since 2018-09-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 乐观锁版本号
     */
    private Long version;

    /**
     * 策略
     */
    private String strategy;


}
