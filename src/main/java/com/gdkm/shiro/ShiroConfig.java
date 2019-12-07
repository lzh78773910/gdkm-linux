package com.gdkm.shiro;

import com.gdkm.shiro.filter.CorsBasicFilter;
import com.gdkm.shiro.filter.ShiroLoginFilter;
import com.gdkm.shiro.filter.ShiroRoleFilter;
import com.gdkm.shiro.session.CustomSessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro的配置类
 * @author lenovo
 *
 */
@Configuration
public class ShiroConfig {


	/**
	 * 创建ShiroFilterFactoryBean
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		
		//设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		
		//添加Shiro内置过滤器
		/**
		 * Shiro内置过滤器，可以实现权限相关的拦截器
		 *    常用的过滤器：
		 *       anon: 无需认证（登录）可以访问
		 *       authc: 必须认证才可以访问
		 *       user: 如果使用rememberMe的功能可以直接访问
		 *       perms： 该资源必须得到资源权限才可以访问
		 *       roles: 该资源必须得到角色权限才可以访问
		 */
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("roles",shiroRoleFilter());
        filters.put("authc",shiroLoginFilter());

        Map<String,String> filterMap = new LinkedHashMap<String,String>();

       filterMap.put("/byuser/**","authc");
//        filterMap.put("/byuser/home","anon");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        shiroFilterFactoryBean.setFilters(filters);

		return shiroFilterFactoryBean;
	}

    @Bean(name="shiroLoginFilter")
    public ShiroLoginFilter shiroLoginFilter() {
        return new ShiroLoginFilter();
    }
    @Bean(name="shiroRoleFilter")
    public ShiroRoleFilter shiroRoleFilter() {
        return new ShiroRoleFilter();
    }

    @Bean(name="corsBasicFilter")
    public CorsBasicFilter corsBasicFilter() {
        return new CorsBasicFilter();
    }

	/**
	 * 创建DefaultWebSecurityManager
	 */
	@Bean(name="securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("loginRealm")LoginRealm userRealm){
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//关联realm
		securityManager.setRealm(userRealm);
        //将自定义的会话管理器注册到安全管理器中
        securityManager.setSessionManager(sessionManager());
        //将自定义的redis缓存管理器注册到安全管理器中
//        securityManager.setCacheManager(cacheManager());
		return securityManager;
	}
	
	/**
	 * 创建Realm
	 */
	@Bean(name="loginRealm")
	public LoginRealm getRealm(){
		return new LoginRealm();
	}



    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;

    /**
     * 1.redis的控制器，操作redis
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setTimeout(7000);
        return redisManager;
    }

    /**
     * 2.sessionDao
     */
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO sessionDAO = new RedisSessionDAO();
        sessionDAO.setRedisManager(redisManager());
        return sessionDAO;
    }

    /**
     * 3.会话管理器
     */
    public DefaultWebSessionManager sessionManager() {
        //自定义子类
        CustomSessionManager sessionManager = new CustomSessionManager();
//        sessionManager.setSessionDAO(redisSessionDAO());
        //禁用所有的Cookie 这样自己设置的session就不起作用了
//        sessionManager.setSessionIdCookieEnabled(false);
//        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    /**
     * 4.缓存管理器
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }




    //开启对shior注解的支持
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
//        advisor.setSecurityManager(securityManager);
//        return advisor;
//    }

//	@Bean
//	public ShiroDialect getShiroDialect(){
//		return new ShiroDialect();
//	}
}
