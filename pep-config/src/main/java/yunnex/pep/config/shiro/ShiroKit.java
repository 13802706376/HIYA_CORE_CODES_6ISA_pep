package yunnex.pep.config.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha1Hash;

import yunnex.pep.biz.sys.dto.SysUserDto;
import yunnex.pep.common.constant.Constant;

/**
 * Shiro工具类
 */
public abstract class ShiroKit {

    /**
     * 获取登录用户信息，要求先登录
     * @return
     */
    public static SysUserDto getUser() {
        return getUser(true);
    }

    /**
     * 获取登录用户信息，如不检查登录并且没有登录用户，返回null
     * @param checkLogin
     * @return
     */
    public static SysUserDto getUser(boolean checkLogin) {
        SysUserDto loginUser = (SysUserDto) SecurityUtils.getSubject().getPrincipal();
        if (checkLogin && loginUser == null) {
            throw new AuthenticationException("用户未登录！");
        }
        return loginUser;
    }

    /**
     * 获取登录用户ID，要求先登录
     * @return
     */
    public static String getUserId() {
        return getUserId(true);
    }

    /**
     * 获取登录用户ID，如不检查登录并且没有登录用户，返回空
     * @param checkLogin
     * @return
     */
    public static String getUserId(boolean checkLogin) {
        SysUserDto loginUser = getUser(checkLogin);
        return loginUser != null ? loginUser.getId() : Constant.Symbol.EMPTY;
    }

    /**
     * 随机生成盐
     * @return
     */
    public static String genSalt() {
        SecureRandomNumberGenerator generator = new SecureRandomNumberGenerator();
        return generator.nextBytes().toBase64();
    }

    /**
     * SHA1加密
     * @param password
     * @param salt
     * @return
     */
    public static String sha1Encrypt(String password, String salt) {
        return new Sha1Hash(password, salt).toHex();
    }

}
