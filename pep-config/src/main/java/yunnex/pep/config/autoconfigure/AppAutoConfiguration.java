package yunnex.pep.config.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import yunnex.pep.common.util.ShiroUtils;
import yunnex.pep.config.common.JacksonConfig;
import yunnex.pep.config.db.MybatisPlusConfig;
import yunnex.pep.config.redis.RedisConfig;

/**
 * 非web应用默认启用的配置，可通过对应的 enabled 属性进行开关，或自定义Bean进行覆盖
 */
@Configuration
@Import({MybatisPlusConfig.class, JacksonConfig.class, RedisConfig.class, ShiroUtils.class})
@ConditionalOnNotWebApplication
public class AppAutoConfiguration {

}
