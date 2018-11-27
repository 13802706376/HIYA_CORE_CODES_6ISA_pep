package yunnex.pep.config.web;

import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import yunnex.pep.biz.sys.dto.LoginUser;
import yunnex.pep.biz.sys.dto.SysUserDto;
import yunnex.pep.common.annotation.Auth;
import yunnex.pep.common.constant.Constant;
import yunnex.pep.config.shiro.ShiroRealm;

/**
 * 登录用户参数解析器. 向Controller方法参数注入LoginUser信息
 */
public class LoginUserArgResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private ShiroRealm shiroRealm;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return LoginUser.class.equals(parameter.getParameterType());
    }

    /**
     * 根据注解@Auth的值设置对应的登录用户信息
     *
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        LoginUser loginUser = new LoginUser();

        Auth auth = parameter.getParameterAnnotation(Auth.class);
        if (auth == null) {
            return loginUser;
        }

        switch (auth.value()) {
            case Constant.Auth.AUTHENTICATION:
                setAuthentication(loginUser);
                break;
            case Constant.Auth.AUTHORIZATION:
                setAuthorization(loginUser);
                break;
            case Constant.Auth.ALL:
                setAuthentication(loginUser);
                setAuthorization(loginUser);
                break;
            case Constant.Auth.ID: default:
                setLoginUserId(loginUser);
                break;
        }

        return loginUser;
    }

    /**
     * 设置认证信息
     *
     * @return
     */
    private void setAuthentication(LoginUser loginUser) {
        loginUser.setUser(getAuthentication());
    }

    /**
     * 获取当前登录用户的认证信息
     *
     * @return
     */
    private SysUserDto getAuthentication() {
        SysUserDto user = (SysUserDto) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            throw new AuthenticationException();
        }
        return user;
    }

    /**
     * 设置当前登录用户ID
     * @param loginUser
     */
    private void setLoginUserId(LoginUser loginUser) {
        loginUser.setUser((SysUserDto) new SysUserDto().setId(getAuthentication().getId()));
    }

    /**
     * 设置授权信息
     * @param loginUser
     */
    private void setAuthorization(LoginUser loginUser) {
        Cache<Object, AuthorizationInfo> cache = shiroRealm.getAuthorizationCache();
        if (cache == null) {
            return;
        }
        String id = getAuthentication().getId();
        AuthorizationInfo authorizationInfo = cache.get(id);
        if (authorizationInfo == null) {
            // 触发获取权限方法
            SecurityUtils.getSubject().isPermitted(Constant.Auth.AUTHORIZATION);
            authorizationInfo = cache.get(id);
        }
        if (authorizationInfo != null) {
            loginUser.setRolesStr((Set<String>) authorizationInfo.getRoles());
            loginUser.setMenusStr((Set<String>) authorizationInfo.getStringPermissions());
        }
    }

}
