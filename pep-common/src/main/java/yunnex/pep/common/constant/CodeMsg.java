package yunnex.pep.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 结果状态码
 */
@Getter
@AllArgsConstructor
public enum CodeMsg {

    /** 1000 以下的状态码保留，与HTTP状态码保持一致 */

    SUCCESS(200, "请求成功！"),
    BAD_REQUEST(400, "请求参数错误！"),
    UNAUTHORIZED(401, "认证失败！"),
    FORBIDDEN(403, "授权失败！"),
    NOT_FOUND(404, "找不到请求的资源！"),
    METHOD_NOT_ALLOWED(405, "不支持的请求方式！"),
    ERROR(500, "系统异常！"),


    ILLEGAL_ARG(40000, "参数不合法！"),

    // dubbo服务异常
    DUBBO_ERROR(60000, "服务异常！"),
    // dubbo服务调用异常：RpcException
    DUBBO_RPC_ERROR(60001, "服务繁忙，请稍后重试！");

    private int code;
    private String msg;

}
