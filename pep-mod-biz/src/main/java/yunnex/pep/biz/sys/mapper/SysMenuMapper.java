package yunnex.pep.biz.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import yunnex.pep.biz.sys.dto.SysMenuDto;
import yunnex.pep.biz.sys.dto.SysRoleDto;
import yunnex.pep.biz.sys.entity.SysMenu;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author mybatis-plus
 * @since 2018-10-11
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    
    public List<SysMenu> findByParentIdsLike(SysMenu menu);

    public List<SysMenu> findByUserId(SysMenu menu);
    
    public int updateParentIds(SysMenu menu);
    
    public int updateSort(SysMenu menu);
    
    public List<SysMenuDto> findAllList(); 
    
    public  SysMenuDto getMenuInfoById(String id);  
  
    public int deleteMenuInfoById(String id);

    /**
     * 查找角色及其关联的所有菜单权限
     *
     * @param roles 角色列表
     * @param menuShowFlag 是否只查显示/隐藏菜单，null表示不限制
     * @param isSort 是否排序
     * @return
     */
    List<SysMenuDto> findRoleMenus(@Param("roles") List<SysRoleDto> roles, @Param("menuShowFlag") String menuShowFlag,
                                   @Param("isSort") boolean isSort);

}
