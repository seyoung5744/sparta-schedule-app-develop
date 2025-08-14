package com.example.schedule.common.config;

import com.example.schedule.domain.auth.filter.AuthCheckFilter;
import com.example.schedule.common.session.SessionService;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final SessionService sessionService;

    @Bean
    public FilterRegistrationBean<Filter> loginFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new AuthCheckFilter(sessionService));
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
