package project.moseup.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/search/**")
        .addResourceLocations("file:///C:/Users/jixmx/Desktop/project/meseup/teamPhotos/");
	}
}
