package yunnex.pep.biz.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;

import yunnex.pep.biz.sys.dto.SysRoleDto;
import yunnex.pep.biz.sys.dto.SysRolePageReqDto;
import yunnex.pep.biz.sys.dto.SysRolePageRespDto;
import yunnex.pep.common.base.BaseService;
import yunnex.pep.common.result.BizOptional;
import yunnex.pep.common.result.BizResult;

/**
 * 角色信息 服务接口
 *
 * @author 政经平台
 * @since 2018-10-19
 */
public interface SysRoleService<T> extends BaseService<T> {

    BizOptional<List<SysRoleDto>> findUserRoles();

    BizResult<Object> saveRole(SysRoleDto sysRoleDto);

    BizResult<SysRoleDto> findRoleInfoById(String id);

    BizResult<Object> deletRoleInfoById(String id);
    
    BizResult<IPage<SysRolePageRespDto>> getRolePage(SysRolePageReqDto sysRolePageReqDto);
}
