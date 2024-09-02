package kr.granblue.gbfsearch.config;

import kr.granblue.gbfsearch.interceptor.HttpLogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfigurer 구현체.
 * 인터셉터 등록과 리소스 요청경로 수정에 사용됨.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 인터셉터 등록
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new HttpLogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**", "/css/**",
                        "https://maxcdn.bootstrapcdn.com/**", "/js/**", "/static-image/**");
    }

}
