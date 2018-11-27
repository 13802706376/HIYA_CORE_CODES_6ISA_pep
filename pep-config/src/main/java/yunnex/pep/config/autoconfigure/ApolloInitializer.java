package yunnex.pep.config.autoconfigure;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 在初始化阶段加载apollo配置
 */
public class ApolloInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String APOLLO_CONFIG = "classpath*:/META-INF/spring/app-config-client.xml";

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        applicationContext.setParent(new ClassPathXmlApplicationContext(APOLLO_CONFIG));
    }

}
