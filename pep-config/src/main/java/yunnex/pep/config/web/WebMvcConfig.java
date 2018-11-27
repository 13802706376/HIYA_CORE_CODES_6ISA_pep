package yunnex.pep.config.web;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import yunnex.pep.common.constant.Constant;


import static yunnex.pep.common.constant.Constant.Flag.TRUE;
import static yunnex.pep.common.constant.Constant.Sys.SYS_PREFIX;


@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    private static final String CORS_FILTER = "corsFilter";

    /**
     * 全局跨域
     */
    @Bean
    @ConditionalOnProperty(prefix = SYS_PREFIX, name = "mvc.cors.enabled", havingValue = TRUE, matchIfMissing = true)
    public FilterRegistrationBean registrationBean() {
        FilterRegistrationBean corsFilter = new FilterRegistrationBean();
        corsFilter.setFilter(new CorsFilter());
        corsFilter.addUrlPatterns(Constant.Pattern.FILTER_ALL);
        corsFilter.setName(CORS_FILTER);
        // 给予较高优先级
        corsFilter.setOrder(Constant.Num.ZERO);
        return corsFilter;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // 登录用户参数解析器
        argumentResolvers.add(loginUserArgResolver());
    }

    @Bean
    public LoginUserArgResolver loginUserArgResolver() {
        return new LoginUserArgResolver();
    }

}
