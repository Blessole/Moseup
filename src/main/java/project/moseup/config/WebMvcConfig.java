//package project.moseup.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    @Autowired
//    private MyInterceptor myInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(myInterceptor)
//                .addPathPatterns("/*") //적용할 url
//                .excludePathPatterns("/members/login"); // 제외할 url
//        WebMvcConfigurer.super.addInterceptors(registry);
//    }
//}
