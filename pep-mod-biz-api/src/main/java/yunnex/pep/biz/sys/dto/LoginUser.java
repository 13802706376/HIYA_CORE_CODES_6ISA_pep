package yunnex.pep.biz.sys.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 登录用户信息
 */
@Data
@Accessors(chain = true)
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户信息
     */
    private SysUserDto user;
    /**
     * 用户的角色
     */
    private List<SysRoleDto> roles;
    /**
     * 用户的菜单（权限）
     */
    private List<SysMenuDto> menus;

    /**
     * 用户的角色（英文名）
     */
    private Set<String> rolesStr;
    /**
     * 用户的菜单（权限字符串）
     */
    private Set<String> menusStr;

    /**
     * 判断用户是否拥有指定权限
     * @param permission
     * @return
     */
    public boolean isPermitted(String permission) {
        return menusStr != null && menusStr.contains(permission);
    }

    /**
     * 判断用户是否拥有指定角色
     * @param role
     * @return
     */
    public boolean hasRole(String role) {
        return rolesStr != null && rolesStr.contains(role);
    }

}
