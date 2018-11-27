package yunnex.pep.biz.sys.service;

import yunnex.pep.biz.sys.dto.LoginUser;
import yunnex.pep.biz.sys.dto.SysUserDto;
import yunnex.pep.common.base.BaseService;
import yunnex.pep.common.result.BizResult;

/**
 * 用户信息 服务接口
 *
 * @author 政经平台
 * @since 2018-10-19
 */
public interface SysUserService<T> extends BaseService<T> {

    BizResult<SysUserDto> findByLoginName(String loginName);

    BizResult<LoginUser> findRoleMenus(LoginUser loginUser, String menuShowFlag, boolean isSort, boolean isTreeData);

    BizResult<Boolean> updateByLoginName(SysUserDto userDto);

    BizResult<Object> syncUserData();

}
