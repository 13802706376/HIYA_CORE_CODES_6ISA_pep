package yunnex.pep.common.util;

import static yunnex.pep.common.constant.Constant.DubboRegisterParams.DUBBO_ENV_VAR;
import java.util.Optional;
import java.util.Properties;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import com.alibaba.dubbo.common.utils.ConfigUtils;

public class SystemUtil
{
	public enum SigleInstance 
    {
        INSTANCE;
        public SystemUtil instance;

        SigleInstance() 
        {
            instance = new SystemUtil();
        }

        public SystemUtil getInstance() 
        {
            return instance;
        }
    }
	
	public SystemUtil registerSpringEnv(MutablePropertySources mps,String registerKey,String registerVal,boolean isDubboProperties,boolean isLocalDebugger)
	{
		if(!isLocalDebugger || (isLocalDebugger && isLocalDebugger()))
		{
			Properties properties = new Properties();
	    	properties.put(registerKey, registerVal);
	    	mps.addLast(new PropertiesPropertySource(registerKey, properties));
	    	if(isDubboProperties)
	    	{
	    		ConfigUtils.getProperties().setProperty(registerKey, registerVal);
	    	}
		}
    	return SigleInstance.INSTANCE.getInstance();
	}
	
	public boolean isLocalDebugger()
	{
		 return Optional.ofNullable(System.getenv(DUBBO_ENV_VAR)).isPresent();
	}
}
