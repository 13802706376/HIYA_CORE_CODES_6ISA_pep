package yunnex.pep.config.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import yunnex.pep.common.constant.Constant;
import yunnex.pep.config.redis.RedisProperties;


import static yunnex.pep.common.constant.Constant.Flag.TRUE;
import static yunnex.pep.common.constant.Constant.Sys.SYS_PREFIX;

/**
 * Shiro配置
 */
@Configuration
@ConditionalOnProperty(prefix = SYS_PREFIX, name = "shiro.enabled", havingValue = TRUE, matchIfMissing = true)
public class ShiroConfig {

    @Value("${pep.shiro.session.timeout:1800}")
    private Integer sessionTimeout;
    @Value("${pep.shiro.remember-me.cipher:12345678912345ab}")
    private String rememberMeCipher;

    /**
     * 认证与授权
     * @return
     */
    @Bean
    public ShiroRealm shiroRealm() {
        return new ShiroRealm();
    }

    /**
     * 拦截&过滤
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();

        // 过滤器
        Map<String, Filter> filtersMap = new LinkedHashMap<>();
        filtersMap.put("shiroLogin", new ShiroLoginFilter());
        filtersMap.put("shiroLogout", new ShiroLogoutFilter());
        filtersMap.put("shiroUser", new ShiroUserFilter());
        shiroFilter.setFilters(filtersMap);

        // 请求过滤器，要保证顺序
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/login", "shiroLogin");
        filterChainDefinitionMap.put("/logout", "shiroLogout");
        // 通过记住我登录也放行
        filterChainDefinitionMap.put("/admin/**", "shiroUser");
        // 监控访问控制
        filterChainDefinitionMap.put("/actuator/**", "roles[admin]");
        filterChainDefinitionMap.put("/**", "anon");
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);

        shiroFilter.setLoginUrl("/login");
        shiroFilter.setUnauthorizedUrl("/403");

        shiroFilter.setSecurityManager(securityManager);

        return shiroFilter;
    }

    /**
     * Web安全管理器
     * @param realm
     * @param redisCacheManager
     * @param rememberMeManager
     * @param sessionManager
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(ShiroRealm realm, RedisCacheManager redisCacheManager,
           RememberMeManager rememberMeManager, HeaderSessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setCacheManager(redisCacheManager);
        securityManager.setSessionManager(sessionManager);
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }

    /**
     * redis缓存配置
     * @return
     */
    @Bean
    public RedisManager redisManager(RedisProperties properties) {
        RedisManager redisManager = new RedisManager();
        try
		{
			redisManager.setDatabase(properties.getDatabase());
			redisManager.setHost(properties.getHost());
			redisManager.setPort(properties.getPort());
		} catch (Exception e)
		{
		}
        return redisManager;
    }

    /** Redis缓存 */
    @Bean
    public RedisCacheManager redisCacheManager(RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        // 缓存时间应该和sessionId时间一致
        redisCacheManager.setExpire(sessionTimeout);
        return redisCacheManager;
    }

    /**
     * redis session dao
     * @param redisManager
     * @return
     */
    @Bean
    public RedisSessionDAO redisSessionDAO(RedisManager redisManager) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager);
        return redisSessionDAO;
    }

    /**
     * session管理器，从Header中获取sessionId
     * @param sessionDao
     * @return
     */
    @Bean
    public HeaderSessionManager defaultWebSessionManager(RedisSessionDAO sessionDao) {
        HeaderSessionManager sessionManager = new HeaderSessionManager();
        sessionManager.setGlobalSessionTimeout(sessionTimeout * Constant.Num.THOUSAND);
        sessionManager.setSessionDAO(sessionDao);
        return sessionManager;
    }

    /**
     * 记住我管理器，从Header中获取记住我信息
     * @return
     */
    @Bean
    public HeaderRememberMeManager rememberMeManager() {
        HeaderRememberMeManager rememberMeManager = new HeaderRememberMeManager();
        // 密钥
        rememberMeManager.setCipherKey(rememberMeCipher.getBytes());
        return rememberMeManager;
    }

    // 初始化和释放资源
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
       return new LifecycleBeanPostProcessor();
    }

    /**
     * 如果您在使用shiro注解配置的同时，引入了spring aop的starter，会有一个奇怪的问题，导致shiro注解的请求，不能被映射，加入以下配置
     * setUsePrefix(false)用于解决这个奇怪的bug。在引入spring aop的情况下
     * 在@Controller注解的类的方法中加入@RequiresRole等shiro注解，会导致该方法无法映射请求，导致返回404。
     * 加入这项配置能解决这个bug
     */
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

}
