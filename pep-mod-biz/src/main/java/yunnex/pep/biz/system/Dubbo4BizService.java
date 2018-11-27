package yunnex.pep.biz.system;

import static yunnex.pep.common.constant.Constant.PepModBizDubboParams.*;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import yunnex.pep.common.util.SystemUtil;

/**
 * 拦截 dubbo的 监听器(OverrideDubboConfigApplicationListener)
 * 解决注册问题 ,屏蔽本地注册 
 * @author caozhijun
 */
public class Dubbo4BizService  
{
	public static void doRegister(ConfigurableEnvironment environment)
	{
		MutablePropertySources mps = environment.getPropertySources();
		SystemUtil.SigleInstance.INSTANCE.getInstance()
     	.registerSpringEnv(mps, DUBBO_APPLICATION_ID, PEP_MOD_BIZ,false,false)
     	.registerSpringEnv(mps, DUBBO_APPLICATION_NAME, PEP_MOD_BIZ,false,false)
     	.registerSpringEnv(mps, DUBBO_REGISTRY_KEY, DUBBO_REGISTRY_BIZ_VAL,false,false)
     	.registerSpringEnv(mps, DUBBO_BASE_PACKAGE_KEY, DUBBO_BASE_PACKAGE_VAL,false,false)
     	.registerSpringEnv(mps, DUBBO_PROTOCOL_PORT_KEY, DUBBO_PROTOCOL_PORT_VAL,false,false);
	}
}
