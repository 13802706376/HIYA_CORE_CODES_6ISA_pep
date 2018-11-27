package yunnex.pep.admin.system;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

import com.alibaba.dubbo.config.annotation.Reference;

import lombok.extern.slf4j.Slf4j;
import yunnex.common.config.utils.PropertiesFileReader;
import yunnex.pep.common.util.SystemUtil;


import static yunnex.pep.common.constant.Constant.DubboRegisterParams.DUBBO_REGISTER_ADDRESS;
import static yunnex.pep.common.constant.Constant.DubboRegisterParams.ZKSTRING;
import static yunnex.pep.common.constant.Constant.PepModBizDubboParams.DUBBO_APPLICATION_ID;
import static yunnex.pep.common.constant.Constant.PepModBizDubboParams.DUBBO_APPLICATION_NAME;
import static yunnex.pep.common.constant.Constant.PepModBizDubboParams.DUBBO_CONSUMER_TIMEOUT_KEY;
import static yunnex.pep.common.constant.Constant.PepModBizDubboParams.DUBBO_CONSUMER_TIMEOUT_VAL;
import static yunnex.pep.common.constant.Constant.PepWebAdminDubboParams.PEP_WEB_ADMIN;

@Slf4j
public class Dubbo4AdminService
{
	 private static final String BOOT_FILE = "file:" + System.getProperty("user.home") + File.separatorChar + ".yunnex" + File.separatorChar + "boot";
	     
	@SuppressWarnings("unchecked")
	public static void doDebugger()
	{
		if(SystemUtil.SigleInstance.INSTANCE.getInstance().isLocalDebugger())
		{ 
			Set<Class<?>> set = null;
			try
			{
				set = DubboReferenceScanner.getClasses("yunnex.pep.admin");
				Iterator<Class<?>> iterator = set.iterator();
				while (iterator.hasNext())
				{
					Class<?> reClass = iterator.next();
					Field fieldArr[] = reClass.getDeclaredFields();
					for (Field filed : fieldArr)
					{
						if (filed.isAnnotationPresent(Reference.class))
						{
							Reference refObj = filed.getAnnotation(Reference.class);
							InvocationHandler refIh = Proxy.getInvocationHandler(refObj);
							Field hField = refIh.getClass().getDeclaredField("memberValues");
							hField.setAccessible(true);
							Map<String, String> memberValues = (Map<String, String>) hField.get(refIh);
							memberValues.put("url", "dubbo://localhost:12345");
						}
					}
				}
				log.info("Dubbo4AdminListenerImpl.doDebugger sucess.");
			} catch (Exception e)
			{
				log.error("Dubbo4AdminListenerImpl.doDebugger: ", e);
			}
		}
	}

	public static void doRegister(ConfigurableEnvironment environment)
	{
		MutablePropertySources mps = environment.getPropertySources();
		SystemUtil.SigleInstance.INSTANCE.getInstance()
		.registerSpringEnv(mps, DUBBO_APPLICATION_ID, PEP_WEB_ADMIN,false,false)
		.registerSpringEnv(mps, DUBBO_APPLICATION_NAME, PEP_WEB_ADMIN,false,false)
		.registerSpringEnv(mps, DUBBO_REGISTER_ADDRESS, "zookeeper://"+PropertiesFileReader.readProperties(BOOT_FILE).getProperty(ZKSTRING,""),true,false)
		.registerSpringEnv(mps, DUBBO_CONSUMER_TIMEOUT_KEY, DUBBO_CONSUMER_TIMEOUT_VAL,true,true);
	}
}
