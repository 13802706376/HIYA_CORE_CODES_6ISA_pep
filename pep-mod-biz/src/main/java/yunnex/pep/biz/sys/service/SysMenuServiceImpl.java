package yunnex.pep.biz.sys.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import yunnex.pep.biz.sys.dto.SysMenuDto;
import yunnex.pep.biz.sys.dto.SysRoleDto;
import yunnex.pep.biz.sys.entity.SysMenu;
import yunnex.pep.biz.sys.mapper.SysMenuMapper;
import yunnex.pep.common.constant.CodeMsg;
import yunnex.pep.common.result.BizOptional;
import yunnex.pep.common.result.BizResult;
import yunnex.pep.common.util.tree.TreeUtils;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author mybatis-plus
 * @since 2018-10-11
 */
@com.alibaba.dubbo.config.annotation.Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysMenuMapper menuMapper;
    
    
    @Transactional(readOnly = false)
    @Override
    public BizResult<Object> saveMenu(SysMenuDto menuDto) {
        // 获取修改前的parentIds，用于更新子节点的parentIds
       String oldParentIds = menuDto.getParentIds();
        // 设置新的父节点串
       
       String  parentIds  =    menuDto.getParent().getParentIds()==null?"":menuDto.getParent().getParentIds() + menuDto.getParent().getId() + ",";
       SysMenu menu = new SysMenu();
       BeanUtils.copyProperties(menuDto, menu);
       menu.setParentIds(parentIds);
       menu.setParentId(menuDto.getParent().getId());
       menu.setType(menuDto.getType());
        // 保存或更新实体
        if (StringUtils.isBlank(menuDto.getId())) {
            save(menu) ;
        } else {
           updateById(menu) ;
        }
        // 更新子节点 parentIds
        SysMenu m = new SysMenu();
        m.setParentIds("%," + menu.getId() + ",%");
        List<SysMenu> list = menuMapper.findByParentIdsLike(m);
        for (SysMenu e : list) {
            e.setParentIds(e.getParentIds().replaceAll(oldParentIds, menu.getParentIds()));
            menuMapper.updateParentIds(e);
        }
        return BizResult.builder(CodeMsg.SUCCESS).build();        
    }


    @Override
    public BizResult<List<SysMenuDto>> findAllMenu() {
        List<SysMenuDto> menus = menuMapper.findAllList();
        List<SysMenuDto> result = TreeUtils.tree(menus);
        return BizResult.success(result);
    }



    @Override
    public BizResult<SysMenuDto> form(String Id) {
        SysMenuDto menudto= menuMapper.getMenuInfoById(Id);
        return BizResult.builder(CodeMsg.SUCCESS, menudto).build();   
    }


    @Override
    public BizResult<Object> deleteMenu(String Id) {
        menuMapper.deleteMenuInfoById(Id);
        return BizResult.builder(CodeMsg.SUCCESS).build();     
    }


    @Override
    public BizResult<List<Map<String, Object>>> getMemutreeData(String extId,String isShowHide) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<SysMenuDto> list = menuMapper.findAllList();
        for (int i=0; i<list.size(); i++){
            SysMenuDto e = list.get(i);
            if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
                if(isShowHide != null && isShowHide.equals("0") && e.getIsShow().equals("0")){
                    continue;
                }
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParentId());
                map.put("name", e.getName());
                map.put("pIds", e.getParentIds());
                mapList.add(map);
            }
        }
        return BizResult.builder(CodeMsg.SUCCESS, mapList).build(); 
    }

    /**
     * 根据角色列表获取对应的菜单权限
     * @param roles
     * @param menuShowFlag
     * @param isSort
     * @param isTreeData
     * @return
     */
    @Override
    public BizOptional<List<SysMenuDto>> findRoleMenus(List<SysRoleDto> roles, String menuShowFlag,
        boolean isSort, boolean isTreeData) {
        if (CollectionUtils.isEmpty(roles)) {
            return BizOptional.empty();
        }
        List<SysMenuDto> roleMenus = menuMapper.findRoleMenus(roles, menuShowFlag, isSort);
        if (isTreeData) {
            roleMenus = TreeUtils.tree(roleMenus);
        }
        return BizOptional.of(roleMenus);
    }

}
