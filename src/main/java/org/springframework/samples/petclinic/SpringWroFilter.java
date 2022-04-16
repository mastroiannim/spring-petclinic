package org.springframework.samples.petclinic;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import ro.isdc.wro.http.ConfigurableWroFilter;


public class SpringWroFilter extends ConfigurableWroFilter {
    public SpringWroFilter(){
        super();
        try (InputStream input = new FileInputStream("src/main/wro/wro.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            setProperties(prop);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
