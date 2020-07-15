package com.backstage.config.interceptor;//package com.blogger.config.interceptor;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
///**
// * 拦截器方法
// * 进行权限验证，或者是来判断用户是否登陆，日志记录，或者限制时间点访问，记录登录时间。
// *
// * @author Liu wei
// * @date 2020-03-31 16:00
// */
//public class LogCostInterceptor implements HandlerInterceptor {
//    private final static Logger logger = LoggerFactory.getLogger(LogCostInterceptor.class);
//
//
//    /**
//     * preHandle是请求执行前执行的，
//     * 应用场景：统一日志处理，统一异常处理
//     *
//     * @param httpServletRequest
//     * @param httpServletResponse
//     * @param o
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
////        logger.info("SessionInterceptor preHandle方法，在请求方法之前调用，Controller方法调用之前");
////        logger.info("拦截器url:" + httpServletRequest.getRequestURI());
//        return true;
//
////        //  获取session的值
////        HttpSession session = httpServletRequest.getSession();
////
////        Object user = session.getAttribute("user");
////        if (user != null) {
////            // 用户已登录
////            System.out.println("自定义拦截器-用户已登录");
////            return true;
////        } else {
////            //用户未登录，直接跳转登录页面
////            httpServletResponse.sendRedirect("/login");
////            System.out.println("自定义拦截器-用户未登录—跳转到login页面");
////            return false;
////        }
//    }
//
//    /**
//     * postHandler是请求结束执行的，但只有preHandle方法返回true的时候才会执行，
//     * 应用场景从modelAndView出发，将公用模型数据（如菜单导航）在这里传到视图，也可以在这里统一制定视图
//     *
//     * @param httpServletRequest
//     * @param httpServletResponse
//     * @param o
//     * @param modelAndView
//     * @throws Exception
//     */
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
////        logger.info("SessionInterceptor postHandle方法，请求处理之后调用，但是在视图被渲染之前（Controller方法调用之后）");
//
//    }
//
//    /**
//     * afterCompletion是视图渲染完成后才执行，同样需要preHandle返回true，该方法通常用于清理资源等工作。
//     * 应用场景：如身份认证，身份授权
//     *
//     * @param httpServletRequest
//     * @param httpServletResponse
//     * @param o
//     * @param e
//     * @throws Exception
//     */
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
////        logger.info("SessionInterceptor afterCompletion方法，在整个请求结束之后调用，也就是在Dispatcher渲染了整个视图之后进行（主要进行资源清理工作）");
//
//    }
//}
