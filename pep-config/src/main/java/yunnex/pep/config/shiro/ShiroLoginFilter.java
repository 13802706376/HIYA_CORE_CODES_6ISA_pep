package yunnex.pep.config.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;
import yunnex.pep.biz.sys.dto.SysLogDto;
import yunnex.pep.common.constant.CodeMsg;
import yunnex.pep.common.constant.Constant;
import yunnex.pep.common.result.BizResult;
import yunnex.pep.common.util.WebUtils;
import yunnex.pep.config.log.LogAsync;


import static yunnex.pep.common.constant.Constant.Symbol.EMPTY;

/**
 * 登录过滤器。重写登录成功和失败事件。
 */
@Slf4j
public class ShiroLoginFilter extends FormAuthenticationFilter {

    private static final String LOGIN_TITLE_SUCCESS = "登录成功";
    private static final String LOGIN_TITLE_FAIL = "登录失败";
    private static final String LOGIN_METHOD = "login";
    public static final String LOG_START_TIME_MILLIS = "__logStartTimeMillis";

    @Override
    protected AuthenticationToken createToken(String username, String password, boolean rememberMe, String host) {
        return new ShiroToken(username, Constant.Security.DEFAULT_PASSWORD, rememberMe, host, password);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        request.setAttribute(LOG_START_TIME_MILLIS, System.currentTimeMillis());
        return super.executeLogin(request, response);
    }

    /**
     * 登录成功
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.printlnJson((HttpServletResponse) response, BizResult.success());
        log(request, token, null, LOGIN_TITLE_SUCCESS, EMPTY);
        return false;
    }

    /**
     * 登录失败
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        String logMsg = EMPTY;
        Exception logEx = null; // 已知异常不记录堆栈，如密码不正确等
        String showMsg = e.getMessage();
        BizResult.BizResultBuilder<String> builder = BizResult.builder(CodeMsg.UNAUTHORIZED);
        if (e instanceof AccountException || e instanceof LockedAccountException) {
            builder.msg(showMsg);
            logMsg = showMsg;
            log.info(logMsg);
        } else if (e instanceof UnknownAccountException) {
            builder.msg(showMsg);
            logMsg = "用户名不正确！";
            log.info(logMsg);
        } else if (e instanceof IncorrectCredentialsException) {
            builder.msg(showMsg);
            logMsg = "密码不正确！";
            log.info(logMsg);
        }
        // 未知异常
        else if (e instanceof AuthenticationException) {
            log.error(showMsg, e);
            showMsg = "登录失败！";
            builder.msg(showMsg);
            logMsg = showMsg;
            logEx = e;
        }
        try {
            WebUtils.printlnJson((HttpServletResponse) response, builder.build());
        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
            throw new AuthenticationException(ioe);
        }
        log(request, token, logEx, LOGIN_TITLE_FAIL, logMsg);
        return false;
    }

    /**
     * 日志记录
     * @param request
     * @param token
     * @param e
     * @param remarks
     */
    private void log(ServletRequest request, AuthenticationToken token, Exception e, String title, String remarks) {
        ShiroToken shiroToken = (ShiroToken) token;
        shiroToken.setRealPassword(null);
        shiroToken.setPassword(null);
        SysLogDto logDto = (SysLogDto) new SysLogDto().setTitle(title).setMethod(LOGIN_METHOD)
                .setMethodParams(JSON.toJSONString(token)).setRemarks(remarks);
        if (e != null) {
            logDto.setExFlag(Constant.Flag.Y).setException(ExceptionUtils.getStackTrace(e));
        } else {
            logDto.setException(EMPTY);
        }
        long start = Long.parseLong(request.getAttribute(LOG_START_TIME_MILLIS).toString());
        logDto.setCost(Integer.valueOf(String.valueOf(System.currentTimeMillis() - start)));
        HttpServletRequest httpReq = (HttpServletRequest) request;
        // 操作日志
        LogAsync.log(httpReq, logDto);
        // 登录日志
        LogAsync.loginLog(httpReq, shiroToken.getUsername());
    }

}
