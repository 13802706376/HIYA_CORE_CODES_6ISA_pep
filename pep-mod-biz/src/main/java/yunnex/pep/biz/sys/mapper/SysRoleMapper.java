package yunnex.pep.biz.sys.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import yunnex.pep.biz.sys.dto.SysRolePageReqDto;
import yunnex.pep.biz.sys.dto.SysRolePageRespDto;
import yunnex.pep.biz.sys.dto.SysRoleDto;
import yunnex.pep.biz.sys.entity.SysRole;

/**
 * 角色信息 Mapper 接口
 *
 * @author 政经平台
 * @since 2018-10-19
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<SysRolePageRespDto> getRolePage(SysRolePageReqDto sysRolePageReqDto);
    /**
     * 查找用户拥有的所有角色
     * @param userId
     * @return
     */
    List<SysRoleDto> findUserRoles(String userId);

}
