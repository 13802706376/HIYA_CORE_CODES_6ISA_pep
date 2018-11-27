package yunnex.pep.config.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.LogoutFilter;

import yunnex.pep.biz.sys.dto.SysLogDto;
import yunnex.pep.common.result.BizResult;
import yunnex.pep.common.util.WebUtils;
import yunnex.pep.config.log.LogAsync;


import static yunnex.pep.common.constant.Constant.Symbol.EMPTY;

/**
 * 退出登录过滤器。重写退出成功逻辑，返回json响应。
 */
public class ShiroLogoutFilter extends LogoutFilter {

    private static final String LOGOUT_TITLE = "退出登录";
    private static final String LOGOUT_METHOD = "logout";
    public static final String LOG_START_TIME_MILLIS = "__logStartTimeMillis";
    public static final String LOG_USER_ID = "__logUserId";


    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        request.setAttribute(LOG_START_TIME_MILLIS, System.currentTimeMillis());
        // 在退出前记录用户ID
        request.setAttribute(LOG_USER_ID, ShiroKit.getUserId(false));
        return super.preHandle(request, response);
    }

    @Override
    protected void issueRedirect(ServletRequest request, ServletResponse response, String redirectUrl) throws Exception {
        SysLogDto logDto = new SysLogDto().setTitle(LOGOUT_TITLE).setMethod(LOGOUT_METHOD).setMethodParams(EMPTY).setException(EMPTY);
        long start = Long.parseLong(request.getAttribute(LOG_START_TIME_MILLIS).toString());
        logDto.setCost(Integer.valueOf(String.valueOf(System.currentTimeMillis() - start)));
        logDto.setUserId(request.getAttribute(LOG_USER_ID).toString());
        LogAsync.log((HttpServletRequest) request, logDto);
        WebUtils.printlnJson((HttpServletResponse) response, BizResult.success());
    }

}
