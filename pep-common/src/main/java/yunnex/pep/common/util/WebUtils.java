package yunnex.pep.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSONObject;

import yunnex.pep.common.constant.Constant;
import yunnex.pep.common.result.BizResult;

/**
 * Web工具类
 */
public final class WebUtils {

    public static final String ACCESS_CONTROL_MAX_AGE_VALUE = "604800";
    public static final String ATTACHMENT_FILENAME = "attachment; filename=\"%s\"";

    private WebUtils() {
    }

    /**
     * 跨域的header设置。允许所有跨源请求。
     */
    public static void corsHeader(HttpServletResponse response) {
        // 允许所有跨域请求
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, Constant.Symbol.ASTERISK);
        // 允许所有请求方法
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, Constant.Symbol.ASTERISK);
        // 允许所有请求头
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, Constant.Symbol.ASTERISK);
        // 允许客户端访问的响应头
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, Constant.HttpHeader.EXPOSE_HEADERS);
        // 预检请求缓存时间
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, ACCESS_CONTROL_MAX_AGE_VALUE);
        // 不允许浏览器携带用户身份信息(cookie)
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, Constant.Flag.FALSE);
    }

    /**
     * 响应json数据
     *
     * @param response
     */
    public static void jsonHeader(HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

    /**
     * 下载内容响应头设置
     */
    public static void fileDownloadHeader(HttpServletResponse response, String fileName) {
        // 中文文件名支持
        String encodeFileName = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format(ATTACHMENT_FILENAME, encodeFileName));
    }

    /**
     * 输出数据到客户端
     *
     * @param response
     * @param str
     * @throws IOException
     */
    public static void println(HttpServletResponse response, String str) throws IOException {
        PrintWriter out = response.getWriter();
        out.println(str);
        out.flush();
        out.close();
    }

    /**
     * 输出json数据到客户端
     *
     * @param response
     * @param result
     * @throws IOException
     */
    public static void printlnJson(HttpServletResponse response, BizResult result) throws IOException {
        jsonHeader(response);
        println(response, JSONObject.toJSONString(result));
    }

    /**
     * 获取用户代理
     * @param request
     * @return
     */
    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader(Constant.HttpHeader.USER_AGENT);
    }

    /**
     * 获取用户IP地址
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String xff = request.getHeader(Constant.HttpHeader.X_FORWARDED_FOR);
        if (StringUtils.isNotEmpty(xff) && !Constant.HttpHeader.UNKNOWN.equalsIgnoreCase(xff)) {
            // 反向代理后第一个才是真实ip
            int index = xff.indexOf(Constant.Symbol.COMMA);
            if (index != Constant.Num.NEGATIVE_ONE) {
                return xff.substring(Constant.Num.ZERO, index);
            } else {
                return xff;
            }
        }
        xff = request.getHeader(Constant.HttpHeader.X_REAL_IP);
        if (StringUtils.isNotEmpty(xff) && !Constant.HttpHeader.UNKNOWN.equalsIgnoreCase(xff)) {
            return xff;
        }
        if (StringUtils.isBlank(xff) || Constant.HttpHeader.UNKNOWN.equalsIgnoreCase(xff)) {
            xff = request.getHeader(Constant.HttpHeader.PROXY_CLIENT_IP);
        }
        if (StringUtils.isBlank(xff) || Constant.HttpHeader.UNKNOWN.equalsIgnoreCase(xff)) {
            xff = request.getHeader(Constant.HttpHeader.WL_PROXY_CLIENT_IP);
        }
        if (StringUtils.isBlank(xff) || Constant.HttpHeader.UNKNOWN.equalsIgnoreCase(xff)) {
            xff = request.getHeader(Constant.HttpHeader.HTTP_CLIENT_IP);
        }
        if (StringUtils.isBlank(xff) || Constant.HttpHeader.UNKNOWN.equalsIgnoreCase(xff)) {
            xff = request.getHeader(Constant.HttpHeader.HTTP_X_FORWARDED_FOR);
        }
        if (StringUtils.isBlank(xff) || Constant.HttpHeader.UNKNOWN.equalsIgnoreCase(xff)) {
            xff = request.getRemoteAddr();
        }
        return xff;
    }

}
