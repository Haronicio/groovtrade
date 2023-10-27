package fr.haron.groovtrade.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomWebConfig implements WebMvcConfigurer {
    // @Override
    // public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //     registry.addResourceHandler("/images/**")
    //             .addResourceLocations("file:./data/images");
    //     registry.addResourceHandler("/audio/**")
    //             .addResourceLocations("file:./data/audio/");
    // }
}
