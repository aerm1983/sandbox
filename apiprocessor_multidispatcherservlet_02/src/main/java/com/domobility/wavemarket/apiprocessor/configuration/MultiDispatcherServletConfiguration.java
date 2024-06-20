package com.domobility.wavemarket.apiprocessor.configuration;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.domobility.wavemarket.apiprocessor.multidispatcherservlet.api.controller.BulkFileConfig;
import com.domobility.wavemarket.apiprocessor.multidispatcherservlet.oauth.controller.OauthConfig;

@Configuration
public class MultiDispatcherServletConfiguration {

	/**
	 * This would be default DispatcherServlet.
	 * In this strategy, it is initiated as a one of the secondary ones.
	 * 
	 */
	@Bean
	public ServletRegistrationBean api() {
		DispatcherServlet dispatcherServlet = new DispatcherServlet();
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(BulkFileConfig.class);
		dispatcherServlet.setApplicationContext(applicationContext);
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dispatcherServlet, "/api/*");
		servletRegistrationBean.setName("api");
		servletRegistrationBean.setLoadOnStartup(1);
		return servletRegistrationBean;
	}	

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
