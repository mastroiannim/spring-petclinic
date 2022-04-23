package org.springframework.samples.petclinic;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/webjars/**") //We will refer to the WebJars via the /webjars/ path. 
            .addResourceLocations("/webjars/").resourceChain(false); //The resourceChain() method must be called for version-agnostic WebJars.
        registry
            .addResourceHandler("/resources/**")
            .addResourceLocations("/resources/", "classpath:/static/resources/");
    }
}
