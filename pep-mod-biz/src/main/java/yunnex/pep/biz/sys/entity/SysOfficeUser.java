package yunnex.pep.biz.sys.entity;

import java.io.Serializable;

import yunnex.pep.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 人员部门关联关系表 实体类
 *
 * @author 政经平台
 * @since 2018-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
public class SysOfficeUser extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 人员id
     */
    private String userId;

    /**
     * 部门id
     */
    private String officeId;
    
    /**
     * 部门dn（部门唯一标识）
     */
    private String officeDn;
    
    /**
     * 人员cn（人员登录名）
     */
    private String userCn;

}
