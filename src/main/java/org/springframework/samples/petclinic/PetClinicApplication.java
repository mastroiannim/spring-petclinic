/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import ro.isdc.wro.http.ConfigurableWroFilter;
import ro.isdc.wro.manager.factory.ConfigurableWroManagerFactory;
import ro.isdc.wro.manager.factory.WroManagerFactory;
import ro.isdc.wro.model.factory.WroModelFactory;
import ro.isdc.wro.model.factory.XmlModelFactory;

/**
 * PetClinic Spring Boot Application.
 *
 * @author Dave Syer
 *
 */
@SpringBootApplication
public class PetClinicApplication {

    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        SpringApplication.run(PetClinicApplication.class, args);
    }

    @Bean
    ServletRegistrationBean<MyServlet> myServletRegistration () {
        ServletRegistrationBean<MyServlet> srb = new ServletRegistrationBean<MyServlet>();
        srb.setServlet(new MyServlet());
        srb.setUrlMappings(Arrays.asList("/path2/*"));
        return srb;
    }

    @Autowired
    ResourceLoader resourceLoader;

    @Bean
    public Properties wroProperties() {
        //LOGGER.warn(System.getProperty("java.class.path"));
        Resource resource = resourceLoader.getResource("classpath:/wro/wro.properties");
        try (InputStream input = resource.getInputStream()){
            Properties prop = new Properties();
            prop.load(input);
            return prop;
        }catch (IOException ex) {
            return null;
        }
    }
    
    @Bean
    FilterRegistrationBean<ConfigurableWroFilter> webResourceOptimizer() {
        FilterRegistrationBean<ConfigurableWroFilter> registration = new FilterRegistrationBean<ConfigurableWroFilter>();
        ConfigurableWroFilter filter = new ConfigurableWroFilter(){
            @Override
            protected WroManagerFactory newWroManagerFactory() {
                LOGGER.warn("..newWroManagerFactory done!");
                //return new MyWroManagerFactory();
                return new ConfigurableWroManagerFactory(){
                    @Override
                    protected WroModelFactory newModelFactory() {
                        return new XmlModelFactory() {
                            @Override
                            protected InputStream getModelResourceAsStream() throws IOException {
                                final String resName = "wro/wro.xml";
                                final Resource resource = new ClassPathResource(resName);
                                LOGGER.warn("Resource_resource_[" + resource + "]");
                                final InputStream stream = resource.getInputStream();
                                return stream;
                            }
                        };
                    }
                };
            }
        };
        filter.setProperties(wroProperties());
        registration.setFilter(filter);
        registration.setEnabled(true);
        registration.addUrlPatterns("/webjars/*", "/resources/*");
        //registration.addUrlPatterns("/wro/*");
        registration.setOrder(1);
        if(wroProperties() != null)
            LOGGER.warn(wroProperties().getProperty("managerFactoryClassName"));
        else
            LOGGER.warn("is null :(");
        return registration;
    }
}
