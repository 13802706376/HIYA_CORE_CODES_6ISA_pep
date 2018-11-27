package yunnex.pep.config.log;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

import lombok.extern.slf4j.Slf4j;
import yunnex.pep.biz.sys.dto.SysLogDto;
import yunnex.pep.biz.sys.dto.SysUserDto;
import yunnex.pep.biz.sys.service.SysLogService;
import yunnex.pep.biz.sys.service.SysUserService;
import yunnex.pep.common.util.WebUtils;
import yunnex.pep.config.common.SpringContextHolder;
import yunnex.pep.config.shiro.ShiroKit;


import static yunnex.pep.common.constant.Constant.Symbol.EMPTY;

/**
 * 异步记录日志
 */
@Slf4j
@Component
public class LogAsync {

    @Reference
    private SysLogService logService;
    @Reference
    private SysUserService userService;


    /**
     * 补充request请求信息。
     * 为不方便注入SpringBean的地方记录日志提供静态方法
     * @param request
     * @param logDto
     */
    public static void log(HttpServletRequest request, SysLogDto logDto) {
        if (logDto == null) {
            logDto = new SysLogDto();
        }
        try {
            String queryString = request.getQueryString();
            queryString = StringUtils.isBlank(queryString) ? EMPTY : URLDecoder.decode(queryString, StandardCharsets.UTF_8.name());
            logDto.setQueryParams(queryString);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        String requestURI = request.getRequestURI();
        String ip = WebUtils.getIp(request);
        logDto.setHttpMethod(request.getMethod()).setRequestUri(requestURI).setUserAgent(WebUtils.getUserAgent(request)).setIp(ip);
        // 登录用户（操作人）
        SysUserDto loginUser = ShiroKit.getUser();
        if (loginUser != null) {
            logDto.setUserId(loginUser.getId());
        }
        SpringContextHolder.getBean(LogAsync.class).log(logDto);
    }

    /**
     * 记录用户登录日志（登录IP和时间）。
     * @param request
     */
    public static void loginLog(HttpServletRequest request, String loginName) {
        SysUserDto userLog = new SysUserDto().setLoginName(loginName).setLastLoginedIp(WebUtils.getIp(request))
                .setLastLoginedDate(LocalDateTime.now());
        SpringContextHolder.getBean(LogAsync.class).loginLog(userLog);
    }

    /**
     * 异步记录操作日志。
     * 异步方法不可以使用static修饰
     */
    @Async
    public void log(SysLogDto logDto) {
        logService.saveRes(logDto);
    }

    /**
     * 异步记录登录日志。
     * @param userDto
     */
    @Async
    public void loginLog(SysUserDto userDto) {
        userService.updateByLoginName(userDto);
    }

}
