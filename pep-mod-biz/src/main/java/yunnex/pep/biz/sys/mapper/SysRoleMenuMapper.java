package yunnex.pep.biz.sys.mapper;

import yunnex.pep.biz.sys.entity.SysRoleMenu;

import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 角色菜单关联关系 Mapper 接口
 *
 * @author 政经平台
 * @since 2018-10-19
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    int addBatchRoleMenuInfo(Map<String, Object>map);
}
