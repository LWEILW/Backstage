package com.backstage.config.shiro;//package com.blogger.config.shiro;


import com.backstage.shiro.ShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Shiro安全框架
 *
 * @author 作者 LW
 * @date 创建时间：2019年6月25日 下午13:14:38
 */
@Configuration
public class ShiroConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

    /**
     * 配置 Shiro 过滤器：
     *
     * @param securityManager 安全管理器
     * @return ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        // 定义 shiroFactoryBean
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 设置自定义的 securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 设置默认登录的 URL，身份认证失败会访问该 URL
        shiroFilterFactoryBean.setLoginUrl("/admin/notLogin");

        // 设置成功之后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/backstage/HomePage");

        // 设置未授权界面，权限认证失败会访问该 URL
        shiroFilterFactoryBean.setUnauthorizedUrl("/admin/notRole");

        // LinkedHashMap 是有序的，进行顺序拦截器配置
        Map<String, String> filterChainMap = new LinkedHashMap<>();

        // 配置可以匿名访问的地址，可以根据实际情况自己添加，放行一些静态资源等，anon 表示放行
        filterChainMap.put("/static/**", "anon");
        filterChainMap.put("/templates/**", "anon");


        // 登录 放行
        filterChainMap.put("/admin/**", "anon");
//        // 用户 放行
//        filterChainMap.put("/user/**", "anon");
//        // 角色 放行
//        filterChainMap.put("/role/**", "anon");
//        // 权限 放行
//        filterChainMap.put("/permission/**", "anon");
//        // 文章 放行
//        filterChainMap.put("/article/**", "anon");


//        // “/user/student” 开头的用户需要角色认证，是“admin”才允许
//        filterChainMap.put("/user/student*/**", "roles[admin]");
//
//        // “/user/teacher” 开头的用户需要权限认证，是“user:create”才允许
//        filterChainMap.put("/user/teacher*/**", "perms[\"user:create\"]");

        // 配置 logout 过滤器
        filterChainMap.put("/logout", "logout");

        // authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问【放行】
        filterChainMap.put("/**", "authc");

        // 设置 shiroFilterFactoryBean 的 FilterChainDefinitionMap
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);

        logger.info("====shiroFilterFactoryBean注册完成====");
        return shiroFilterFactoryBean;
    }


    /**
     * 配置安全管理器 SecurityManager：
     *
     * @return SecurityManager
     */
    @Bean
    public SecurityManager securityManager() {
        // 将自定义 shiroAuthRealm 加进来
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroAuthRealm());

        logger.info("====securityManager注册完成====");
        return securityManager;
    }


    /**
     * 配置身份认证 shiroAuthRealm (账号密码校验；权限等)
     *
     * @return ShiroRealm
     */
    @Bean
    public ShiroRealm shiroAuthRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        // 告诉realm,使用credentialsMatcher加密算法类来验证密文
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        shiroRealm.setCachingEnabled(false);

        logger.info("====myRealm注册完成=====");
        return shiroRealm;
    }

    /**
     * 加密配置
     *
     * @return
     */
    @Bean(name = "credentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(2);
        // storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }


    /**
     * 管理shiro bean的生命周期
     *
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     *
     * @return
     */
    // DefaultAdvisorAutoProxyCreator是用来扫描上下文，寻找所有的Advistor(通知器）
    // 将这些Advisor应用到所有符合切入点的Bean中。所以必须在lifecycleBeanPostProcessor创建之后创建
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    // 上面配置的DefaultAdvisorAutoProxyCreator相当于一个切面，
    // 下面这个类就相当于切点了，两个一起才能实现注解权限控制。
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }


}
