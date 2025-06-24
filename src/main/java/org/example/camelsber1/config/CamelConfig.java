package org.example.camelsber1.config;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig {

    @Bean
    public ServletRegistrationBean camelServletRegistrationBean() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(
                new CamelHttpTransportServlet());
        servlet.setName("CamelServlet");
        return servlet;
    }
}
