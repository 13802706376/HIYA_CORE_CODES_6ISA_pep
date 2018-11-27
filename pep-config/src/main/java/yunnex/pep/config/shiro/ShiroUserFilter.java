package yunnex.pep.config.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;

import yunnex.pep.common.constant.CodeMsg;
import yunnex.pep.common.result.BizResult;
import yunnex.pep.common.util.WebUtils;

/**
 * 重写认证失败逻辑，返回json响应。
 */
public class ShiroUserFilter extends UserFilter {

    @Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        saveRequest(request);
        WebUtils.printlnJson((HttpServletResponse) response, BizResult.builder(CodeMsg.FORBIDDEN).build());
    }

}
