package springstudy.bookstore.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springstudy.bookstore.util.validation.argumentResolver.LoginUserArgumentResolver;
import springstudy.bookstore.util.validation.interceptor.LogInterceptor;
import springstudy.bookstore.util.validation.interceptor.LoginCheckInterceptor;

import java.util.List;
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/shop/css/**", "/*.ico", "/shop/assets/**", "/shop/js/**");
        // "/swagger-resources","/swagger-resources/**", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html"

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/users/signUp", "/api/users",
                                     "/users/login", "/api/users/login",
                                     "/api/users/logout",
                                     "/shop/css/**", "/*.ico", "/shop/assets/**", "/shop/js/**", "/error");

    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserArgumentResolver());
    }

}
