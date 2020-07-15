package com.backstage.config.interceptor;//package com.blogger.config.interceptor;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
///**
// * 拦截器入口
// *
// * @author Liu wei
// * @date 2020-03-31 16:00
// */
//@Configuration
//public class InterceptorConfig extends WebMvcConfigurerAdapter {
//
//    /**
//     * 重写了addInterceptors这个方法，进行拦截器的配置，
//     * 主要配置项就两个，一个是指定拦截器，第二个是指定拦截的URL。
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        // 例子: 添加自定义拦截器和拦截路径，此处对所有请求进行拦截，
//        // 除了登录界面和登录接口,/#/user/**拦截的是Url路径
//        registry.addInterceptor(new LogCostInterceptor()).addPathPatterns("/**").excludePathPatterns("/#/user/**", "/#/article/**", "/login");
//        super.addInterceptors(registry);
//    }
//}
