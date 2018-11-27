package yunnex.pep.config.shiro;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.dubbo.config.annotation.Reference;

import yunnex.pep.biz.sys.dto.LoginUser;
import yunnex.pep.biz.sys.dto.SysMenuDto;
import yunnex.pep.biz.sys.dto.SysRoleDto;
import yunnex.pep.biz.sys.dto.SysUserDto;
import yunnex.pep.biz.sys.ldap.LdapService;
import yunnex.pep.biz.sys.service.SysUserService;
import yunnex.pep.common.constant.Constant;
import yunnex.pep.common.result.BizResult;

public class ShiroRealm extends AuthorizingRealm {

    @Value("${spring.profiles.active}")
    private String env;
    @Reference
    private SysUserService userService;
    @Reference
    private LdapService ldapService;


    // 登录，认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        ShiroToken shiroToken = (ShiroToken) token;
        String loginName = shiroToken.getUsername();
        String realPassword = shiroToken.getRealPassword();

        if (StringUtils.isBlank(loginName)) {
            throw new AccountException("用户名不能为空！");
        }
        if (StringUtils.isBlank(realPassword)) {
            throw new AccountException("密码不能为空！");
        }

        BizResult<SysUserDto> result = userService.findByLoginName(loginName);
        SysUserDto userDB = null;
        if (BizResult.isSuccessful(result)) {
            userDB = result.getData();
        }

        if (userDB == null) {
            throw new UnknownAccountException("用户名或密码不正确！");
        }

        if (Constant.Flag.Y.equals(userDB.getLockFlag())) {
            throw new LockedAccountException("用户已被锁定！");
        }

        boolean isPwdCorrect;
        // 管理员走数据库密码验证
        if (Constant.Security.ADMIN_LOGIN_NAME.equals(userDB.getLoginName())) {
            isPwdCorrect = ShiroKit.sha1Encrypt(realPassword, userDB.getSalt()).equals(userDB.getPassword());
        } else {
            // 开发和测试环境免密码验证
            if (Constant.Sys.DEV.equals(env) || Constant.Sys.TEST.equals(env)) {
                // isPwdCorrect = true;
                BizResult<Boolean> authResult = ldapService.auth(loginName, realPassword);
                isPwdCorrect = BizResult.isSuccessful(authResult) && authResult.getData();
            }
            // 其他环境走LDAP密码验证
            else {
                BizResult<Boolean> authResult = ldapService.auth(loginName, realPassword);
                isPwdCorrect = BizResult.isSuccessful(authResult) && authResult.getData();
            }
        }

        if (!isPwdCorrect) {
            throw new IncorrectCredentialsException("用户名或密码不正确！");
        }

        userDB.setPassword(null);
        userDB.setSalt(null);
        return new SimpleAuthenticationInfo(userDB, new String(shiroToken.getPassword()), getName());
    }

    // 获取用户角色和权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthenticationException("未登录或登录已失效！");
        }

        SysUserDto user = (SysUserDto) getAvailablePrincipal(principals);

        BizResult<LoginUser> result = userService.findRoleMenus(new LoginUser().setUser(user), null, false, false);
        if (!BizResult.isSuccessful(result)) {
            return null;
        }

        SimpleAuthorizationInfo info  = new SimpleAuthorizationInfo();
        LoginUser loginUser = result.getData();
        // 角色
        List<SysRoleDto> roleList = loginUser.getRoles();
        if (CollectionUtils.isNotEmpty(roleList)) {
            Set<String> roles = roleList.stream().filter(role -> StringUtils.isNotBlank(role.getEnname()))
                    .map(role -> role.getEnname()).collect(Collectors.toSet());
            info.setRoles(roles);
        }
        // 菜单
        List<SysMenuDto> menuList = loginUser.getMenus();
        if (CollectionUtils.isNotEmpty(menuList)) {
            Set<String> menus = menuList.stream().filter(menu -> StringUtils.isNotBlank(menu.getPermission()))
                    .map(menu -> menu.getPermission()).collect(Collectors.toSet());
            info.setStringPermissions(menus);
        }
        return info;
    }

}
