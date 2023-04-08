package forexbet.tradingforecasts.config;

import forexbet.tradingforecasts.interceptors.IpBlackListInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    private final IpBlackListInterceptor ipBlackListInterceptor;

    public InterceptorConfiguration(IpBlackListInterceptor blackListInterceptor) {
        this.ipBlackListInterceptor = blackListInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipBlackListInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
