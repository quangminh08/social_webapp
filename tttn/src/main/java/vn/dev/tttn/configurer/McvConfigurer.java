package vn.dev.tttn.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import vn.dev.tttn.illconst.Constants;

@Configuration
public class McvConfigurer implements WebMvcConfigurer, Constants{
	// container 
		@Bean
		public ViewResolver viewResolver() {
			InternalResourceViewResolver bean = new InternalResourceViewResolver();
			bean.setViewClass(JstlView.class);
			bean.setPrefix("/WEB-INF/views/");
			bean.setSuffix(".jsp");
			return bean;
		}
		
		@Override
		public void addResourceHandlers(final ResourceHandlerRegistry registry) {
			
			registry.addResourceHandler("/user/**").addResourceLocations("classpath:/user/");
			registry.addResourceHandler("/admin/**").addResourceLocations("classpath:/admin/");
			registry.addResourceHandler("/css-js/**").addResourceLocations("classpath:/css-js/");
			
			registry.addResourceHandler("/StorageFolder/**").addResourceLocations("file:" + STORAGE_FOLDER);
		}
}
