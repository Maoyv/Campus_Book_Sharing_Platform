package tsl.campus_book_sharing_platform.uitl;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author TSL
 * 配置拦截器拦截路径
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(new UserLoginInterceptor());
//        所有路径都被拦截
        registration.addPathPatterns("/**");
//        添加不拦截路径
        registration.excludePathPatterns(
                // 用户注册
                "/user/registered",
                // 登录路径
                "/user/login",
                // 退出登录路径
                "/user/exit",
                // html静态资源
                "/**/*.html",
                // js静态资源
                "/**/*.js",
                // css静态资源
                "/**/*.css"
        );
    }
}
