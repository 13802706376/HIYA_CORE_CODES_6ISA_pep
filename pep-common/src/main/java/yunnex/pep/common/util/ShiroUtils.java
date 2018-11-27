package yunnex.pep.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.rpc.RpcContext;

import yunnex.pep.common.constant.Constant;

/**
 * Shiro工具类。基于dubbo传递的登录用户信息。
 */
@Component
public class ShiroUtils {

    /** 权限缓存KEY */
    private static final String CACHE_KEY = "shiro:cache:yunnex.pep.config.shiro.ShiroRealm.authorizationCache:%s";

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 获取当前登录用户ID。要求先登录。
     *
     * @return
     */
    public static String getUserId() {
        return getUserId(true);
    }

    /**
     * 从dubbo上下文获取当前登录用户ID。要求dubbo提供端传递此参数才能获取。
     *
     * @param checkLogin 是否检查登录，如不检查并且没有登录，返回空字符串
     * @return
     */
    public static String getUserId(boolean checkLogin) {
        String id = RpcContext.getContext().getAttachment(Constant.Security.LOGIN_USER_ID);
        boolean isBlank = StringUtils.isBlank(id);
        if (checkLogin && isBlank) {
            throw new AuthenticationException("从dubbo上下文获取登录用户ID为空！");
        }
        return isBlank ? Constant.Symbol.EMPTY : id;
    }

    /**
     * dubbo提供端获取当前登录用户的权限。配合 checkPermission() 和 checkRole() 查检是否拥有权限。
     * @return
     */
    public AuthorizationInfo getAuthorizationInfo() {
        String key = String.format(CACHE_KEY, getUserId());
        return (AuthorizationInfo) redisTemplate.opsForValue().get(key);
    }

    /**
     * 检查是否拥有权限
     * @param auth
     * @param perm
     * @return
     */
    public boolean checkPermission(AuthorizationInfo auth, String perm) {
        return auth != null && auth.getStringPermissions() != null && auth.getStringPermissions().contains(perm);
    }

    /**
     * 检查是否拥有角色
     * @param auth
     * @param role
     * @return
     */
    public boolean checkRole(AuthorizationInfo auth, String role) {
        return auth != null && auth.getRoles() != null && auth.getRoles().contains(role);
    }

    /**
     * 检查是否拥有权限
     * @param perm
     * @return
     */
    public boolean isPermitted(String perm) {
        return checkPermission(getAuthorizationInfo(), perm);
    }

    /**
     * 检查是否拥有角色
     * @param role
     * @return
     */
    public boolean hasRole(String role) {
        return checkRole(getAuthorizationInfo(), role);
    }

}
