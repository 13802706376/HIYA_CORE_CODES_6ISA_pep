package yunnex.pep.biz.sys.entity;

import java.io.Serializable;

import yunnex.pep.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 角色菜单关联关系 实体类
 *
 * @author 政经平台
 * @since 2018-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
public class SysRoleMenu extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编号
     */
    private String roleId;

    /**
     * 菜单编号
     */
    private String menuId;


}
