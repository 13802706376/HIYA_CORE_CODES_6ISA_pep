package yunnex.pep.biz.sys.service;

import java.util.List;
import java.util.Map;

import yunnex.pep.biz.sys.dto.SysMenuDto;
import yunnex.pep.biz.sys.dto.SysRoleDto;
import yunnex.pep.common.result.BizOptional;
import yunnex.pep.common.result.BizResult;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author mybatis-plus
 * @since 2018-10-11
 */
public interface SysMenuService  {

    BizResult<Object> saveMenu(SysMenuDto menu);
    
    BizResult<List<SysMenuDto>> findAllMenu();
    
    BizResult<SysMenuDto> form(String Id);
    BizResult<Object> deleteMenu(String Id);
   
    BizResult <List<Map<String, Object>>> getMemutreeData(String extId,String isShowHide);

    BizOptional<List<SysMenuDto>> findRoleMenus(List<SysRoleDto> roles, String menuShowFlag, boolean isSort, boolean isTreeData);

}
