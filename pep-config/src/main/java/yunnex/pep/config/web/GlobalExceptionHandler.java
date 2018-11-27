package yunnex.pep.config.web;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alibaba.dubbo.rpc.RpcException;

import lombok.extern.slf4j.Slf4j;
import yunnex.pep.common.constant.CodeMsg;
import yunnex.pep.common.exception.ValidateException;
import yunnex.pep.common.result.BizResult;

/**
 * 全局异常处理
 */
@Slf4j
@Configuration
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 打印错误堆栈，返回响应结果
     *
     * @param e
     * @param codeMsg
     * @return
     */
    private BizResult log(Exception e, CodeMsg codeMsg) {
        log.error(e.getMessage(), e);
        return BizResult.builder(codeMsg).build();
    }

    // 能捕获到指定异常类的子异常类
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public BizResult handleException(Exception e) {
        return log(e, CodeMsg.ERROR);
    }

    // Dubbo远程调用异常
    @ExceptionHandler(RpcException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public BizResult handleRpcException(RpcException e) {
        return log(e, CodeMsg.DUBBO_RPC_ERROR);
    }

    // 请求参数错误
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BizResult handleBadRequestException(MissingServletRequestParameterException e) {
        return log(e, CodeMsg.BAD_REQUEST);
    }

    // 不支持的请求方式
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    public BizResult handleHttpMethodException(HttpRequestMethodNotSupportedException e) {
        return log(e, CodeMsg.METHOD_NOT_ALLOWED);
    }

    // 认证失败
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public BizResult handleAuthenticationException(AuthenticationException e) {
        return log(e, CodeMsg.UNAUTHORIZED);
    }

    // 授权失败
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public BizResult handleAuthorizationException(AuthorizationException e) {
        return log(e, CodeMsg.FORBIDDEN);
    }

    // 校验失败
    @ExceptionHandler(ValidateException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public BizResult handleValidateException(ValidateException e) {
        return BizResult.illegalArg(e.getMessage());
    }

}
