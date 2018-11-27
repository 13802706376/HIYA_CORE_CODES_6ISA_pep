package yunnex.pep.config.log;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import yunnex.pep.biz.sys.dto.SysLogDto;
import yunnex.pep.common.annotation.Log;
import yunnex.pep.common.constant.Constant;


import static yunnex.pep.common.constant.Constant.Symbol.EMPTY;

/**
 * 日志记录
 */
@Component
@Aspect
public class LogAspect {

    @Autowired
    private HttpServletRequest request;

    @Pointcut("@annotation(yunnex.pep.common.annotation.Log)")
    public void log() {}

    /**
     * 记录日志
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("log()")
    public Object logAround(ProceedingJoinPoint point) throws Throwable {
        Object result;
        SysLogDto logDto = new SysLogDto().setException(EMPTY);
        long start = System.currentTimeMillis();
        try {
            // 记录方法签名
            Signature signature = point.getSignature();
            Log logAnnotation = ((MethodSignature) signature).getMethod().getAnnotation(Log.class);
            String title = logAnnotation.value();
            String method = signature.toString();
            method = method.substring(method.indexOf(Constant.Symbol.SPACE) + Constant.Num.ONE);
            Object[] args = point.getArgs();
            String methodParams = args != null ? JSON.toJSONString(args) : EMPTY;
            logDto.setTitle(title).setMethod(method).setMethodParams(methodParams);

            // 调用目标方法
            result = point.proceed(args);
        } catch (Throwable throwable) {
            logDto.setExFlag(Constant.Flag.Y);
            logDto.setException(ExceptionUtils.getStackTrace(throwable));
            throw throwable;
        } finally {
            logDto.setCost(Integer.valueOf(String.valueOf(System.currentTimeMillis() - start)));
            LogAsync.log(request, logDto);
        }
        return result;
    }

}
