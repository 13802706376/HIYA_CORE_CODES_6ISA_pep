package yunnex.pep.config.dubbo;

import java.lang.reflect.Method;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ReflectUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcResult;
import com.alibaba.dubbo.rpc.service.GenericService;

import yunnex.pep.common.base.BaseService;

/**
 * 重写dubbo的异常过滤器，对BaseService接口及其子接口抛出的自定义异常和第三方jar包异常，直接抛出原始异常，
 * 否则还按原来的逻辑处理。
 * 为了避免消费端反序列化异常实例失败（比如消费端没有引入所抛异常类时），dubbo对以上异常统一封装成RuntimeException抛出，
 * 这样做的一个弊端是消费端无法精确的捕获异常进行相应的处理。此处对原过滤器添加了一个逻辑：对特定的接口返回的异常原样抛出，
 * 以使消费端可以有针对性的处理。为此需要禁用dubbo原来的异常过滤器，并启用重写之后的该过滤器。
 */
@Activate(group = Constants.PROVIDER)
public class ExceptionFilter implements Filter {

    private final Logger logger;

    public ExceptionFilter() {
        this(LoggerFactory.getLogger(com.alibaba.dubbo.rpc.filter.ExceptionFilter.class));
    }

    public ExceptionFilter(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            Result result = invoker.invoke(invocation);
            if (result.hasException() && GenericService.class != invoker.getInterface()) {
                try {
                    Throwable exception = result.getException();

                    // directly throw if it's checked exception
                    if (!(exception instanceof RuntimeException) && (exception instanceof Exception)) {
                        return result;
                    }
                    // directly throw if the exception appears in the signature
                    try {
                        Method method = invoker.getInterface().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
                        Class<?>[] exceptionClassses = method.getExceptionTypes();
                        for (Class<?> exceptionClass : exceptionClassses) {
                            if (exception.getClass().equals(exceptionClass)) {
                                return result;
                            }
                        }
                    } catch (NoSuchMethodException e) {
                        return result;
                    }

                    // for the exception not found in method's signature, print ERROR message in server's log.
                    logger.error("Got unchecked and undeclared exception which called by " + RpcContext.getContext().getRemoteHost()
                            + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName()
                            + ", exception: " + exception.getClass().getName() + ": " + exception.getMessage(), exception);

                    // directly throw if exception class and interface class are in the same jar file.
                    String serviceFile = ReflectUtils.getCodeBase(invoker.getInterface());
                    String exceptionFile = ReflectUtils.getCodeBase(exception.getClass());
                    if (serviceFile == null || exceptionFile == null || serviceFile.equals(exceptionFile)) {
                        return result;
                    }
                    // directly throw if it's JDK exception
                    String className = exception.getClass().getName();
                    if (className.startsWith("java.") || className.startsWith("javax.")) {
                        return result;
                    }
                    // directly throw if it's dubbo exception
                    if (exception instanceof RpcException) {
                        return result;
                    }

                    // =========== start =========== //
                    /** 此为添加的逻辑，对BaseService接口及其子接口抛出的异常原样返回 */
                    if (BaseService.class.isAssignableFrom(invoker.getInterface())) {
                        return result;
                    }
                    // ============ end ============ //

                    // otherwise, wrap with RuntimeException and throw back to the client
                    return new RpcResult(new RuntimeException(StringUtils.toString(exception)));
                } catch (Throwable e) {
                    logger.warn("Fail to ExceptionFilter when called by " + RpcContext.getContext().getRemoteHost()
                            + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName()
                            + ", exception: " + e.getClass().getName() + ": " + e.getMessage(), e);
                    return result;
                }
            }
            return result;
        } catch (RuntimeException e) {
            logger.error("Got unchecked and undeclared exception which called by " + RpcContext.getContext().getRemoteHost()
                    + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName()
                    + ", exception: " + e.getClass().getName() + ": " + e.getMessage(), e);
            throw e;
        }
    }

}
