package com.java110.service.configuration;

import com.java110.service.filter.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wuxw on 2018/5/2.
 */
@Configuration
public class ServiceConfiguration {
    @Bean
    public FilterRegistrationBean jwtFilter() {
        StringBuffer exclusions = new StringBuffer();
        exclusions.append("/callComponent/login/*,");
        exclusions.append("/callComponent/register/*,");
        exclusions.append("/callComponent/validate-code/*,");
        exclusions.append("/callComponent/validate-tel/*,");
        exclusions.append("/flow/login,");
        exclusions.append("/flow/register,");
        exclusions.append("/flow/advertVedioFlow,");//放开 广告页面是不需要登录的
        exclusions.append("/callComponent/advertVedioView/*,");//放开 广告页面是不需要登录的
        exclusions.append("/callComponent/download/getFile/file");//放开 下载图片也不需要登录
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.addUrlPatterns("/");
        registrationBean.addUrlPatterns("/callComponent/*");
        registrationBean.addUrlPatterns("/flow/*");
        registrationBean.addInitParameter("excludedUri",exclusions.toString());

        return registrationBean;
    }

}
