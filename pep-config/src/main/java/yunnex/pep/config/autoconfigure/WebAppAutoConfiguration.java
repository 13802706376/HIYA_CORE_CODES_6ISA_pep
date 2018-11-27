package yunnex.pep.config.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import yunnex.pep.config.common.JacksonConfig;
import yunnex.pep.config.shiro.ShiroConfig;

/**
 * web应用默认启用的配置，可通过对应的 enabled 属性进行开关，或自定义Bean进行覆盖
 */
@Configuration
@ConditionalOnWebApplication
@ComponentScan(basePackages = {"yunnex.pep.config.web", "yunnex.pep.config.redis", "yunnex.pep.config.log"})
@Import({ShiroConfig.class, JacksonConfig.class})
public class WebAppAutoConfiguration {

}
