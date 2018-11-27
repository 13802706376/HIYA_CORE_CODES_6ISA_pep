package yunnex.pep.config.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

import lombok.Data;

@Data
public class ShiroToken extends UsernamePasswordToken {

    /**
     * 用户输入的密码
     */
    private String realPassword;

    public ShiroToken() {
    }

    public ShiroToken(String username, String password, boolean rememberMe, String host, String realPassword) {
        super(username, password, rememberMe, host);
        this.realPassword = realPassword;
    }

}
