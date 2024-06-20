package com.domobility.wavemarket.apiprocessor.configuration;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.domobility.wavemarket.multidispatcherservlet.oauth.controller.OauthConfig;

@Configuration
public class MultiDispatcherServletConfiguration {


	/**
	 * For secondary DispatcherServlet's controllers not to be recognized by default 
	 * DispatcherServlet, place them into another package, so that default ComponentScan 
	 * does not find them.
	 *  
	 */
	@Bean
	public ServletRegistrationBean oauth() {
		DispatcherServlet dispatcherServlet = new DispatcherServlet();   
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(OauthConfig.class);
		dispatcherServlet.setApplicationContext(applicationContext);
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dispatcherServlet, "/oauth/*");
		servletRegistrationBean.setName("oauth");
		servletRegistrationBean.setLoadOnStartup(1);
		return servletRegistrationBean;
	}
}
