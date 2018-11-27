package yunnex.pep.config.db;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;


import static yunnex.pep.common.constant.Constant.Flag.TRUE;
import static yunnex.pep.common.constant.Constant.Sys.SYS_PREFIX;

/**
 * Mybatis Plus配置
 */
@Configuration
@ConditionalOnProperty(prefix = SYS_PREFIX, name = "mybatis-plus.enabled", havingValue = TRUE, matchIfMissing = true)
public class MybatisPlusConfig {

    // 分页插件
    @Bean
    @ConditionalOnMissingBean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


    // 逻辑删除
    @Bean
    @ConditionalOnMissingBean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }


    /**
     * SQL执行效率插件
     */
    @Bean
    @ConditionalOnMissingBean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    /**
     * 公共属性值自动填充
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public MetaObjectHandler commonMetaObjectHandler() {
        return new MybatisPlusMetaObjectHandler();
    }

}
