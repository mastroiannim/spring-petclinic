package org.springframework.samples.petclinic;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import ro.isdc.wro.http.ConfigurableWroFilter;


public class SpringWroFilter extends ConfigurableWroFilter {
    public SpringWroFilter(){
        super();
        Resource resource = new ClassPathResource("classpath:/wro/wro.properties");
        try (InputStream input = resource.getInputStream()) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            setProperties(prop);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
