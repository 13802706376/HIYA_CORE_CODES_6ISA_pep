package yunnex.pep.config.dubbo;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;

import yunnex.pep.common.constant.Constant;
import yunnex.pep.config.shiro.ShiroKit;

/**
 * dubbo用户过滤器, 传递登录用户信息
 */
@Activate(group = Constants.CONSUMER)
public class UserFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 传递登录用户ID
        RpcContext.getContext().setAttachment(Constant.Security.LOGIN_USER_ID, ShiroKit.getUserId(false));
        return invoker.invoke(invocation);
    }

}
