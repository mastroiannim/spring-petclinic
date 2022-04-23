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

        //  .addResourceHandler("/webjars/**")
        //  .addResourceLocations("/webjars/");
            .addResourceHandler("/resources/**")
            .addResourceLocations("/resources/", "classpath:/static/resources/");
    }
}
