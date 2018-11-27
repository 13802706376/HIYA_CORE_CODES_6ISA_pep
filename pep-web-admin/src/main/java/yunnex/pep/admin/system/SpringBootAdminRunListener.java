package yunnex.pep.admin.system;
              
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;

@Order(0)
public class SpringBootAdminRunListener  implements SpringApplicationRunListener
{
	@SuppressWarnings("unused")
	private final SpringApplication application;

	@SuppressWarnings("unused")
	private final String[] args;

	private final SimpleApplicationEventMulticaster initialMulticaster;
	
	public SpringBootAdminRunListener(SpringApplication application, String[] args) 
	{
		this.application = application;
		this.args = args;
		this.initialMulticaster = new SimpleApplicationEventMulticaster();
		for (ApplicationListener<?> listener : application.getListeners()) {
			this.initialMulticaster.addApplicationListener(listener);
		}
	}
	
	@Override
	public void starting()
	{
		Dubbo4AdminService.doDebugger();
	}

	@Override
	public void environmentPrepared(ConfigurableEnvironment environment)
	{
		Dubbo4AdminService.doRegister(environment);
	}

	@Override
	public void contextPrepared(ConfigurableApplicationContext context)
	{}

	@Override
	public void contextLoaded(ConfigurableApplicationContext context)
	{}

	@Override
	public void finished(ConfigurableApplicationContext context, Throwable exception)
	{}
}
