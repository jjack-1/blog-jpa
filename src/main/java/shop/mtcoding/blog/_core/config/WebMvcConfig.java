package shop.mtcoding.blog._core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shop.mtcoding.blog._core.interceptor.LoginInterceptor;

@Configuration // Ioc 컨테이너에 설정파일을 등록
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/user/**")
                .addPathPatterns("/board/**")
                .excludePathPatterns("/board/{id:\\d+}")
                .addPathPatterns("/love/**")
                .addPathPatterns("/reply/**")
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/check-username-available/{username}");
    }
}
