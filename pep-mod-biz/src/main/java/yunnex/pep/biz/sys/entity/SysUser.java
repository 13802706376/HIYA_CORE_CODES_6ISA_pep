package yunnex.pep.biz.sys.entity;

import java.io.Serializable;

import java.time.LocalDateTime;
import yunnex.pep.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 用户信息 实体类
 *
 * @author 政经平台
 * @since 2018-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
public class SysUser extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 姓名
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;
    
    /**
     * 工号
     */
    private String no;
    

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 锁定标识
     */
    private String lockFlag;

    /**
     * 最后登陆IP
     */
    private String lastLoginedIp;

    /**
     * 最后登陆时间
     */
    private LocalDateTime lastLoginedDate;


}
