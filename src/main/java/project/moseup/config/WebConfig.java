package project.moseup.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/teamPhotos/**")
        .addResourceLocations("file:///C:/DevSpace/sts4Src/moseup/src/main/resources/static/teamPhotos/");
	}
}
