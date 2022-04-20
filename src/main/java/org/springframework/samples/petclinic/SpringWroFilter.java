package org.springframework.samples.petclinic;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import ro.isdc.wro.http.ConfigurableWroFilter;
import ro.isdc.wro.manager.factory.ConfigurableWroManagerFactory;
import ro.isdc.wro.manager.factory.WroManagerFactory;
import ro.isdc.wro.model.factory.WroModelFactory;
import ro.isdc.wro.model.factory.XmlModelFactory;


public class SpringWroFilter extends ConfigurableWroFilter {

    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private Properties wroProperties() {
        Resource resource = new ClassPathResource("wro/wro.properties");
        try (InputStream input = resource.getInputStream()){
            Properties prop = new Properties();
            prop.load(input);
            return prop;
        }catch (IOException ex) {
            LOGGER.warn("Resource_resource_[" + resource + "]");
            return null;
        }
    }

    public SpringWroFilter(){
        super();
        setProperties(wroProperties());
    }

    @Override
    protected WroManagerFactory newWroManagerFactory() {
        return new ConfigurableWroManagerFactory(){
            @Override
            protected WroModelFactory newModelFactory() {
                return new XmlModelFactory() {
                    @Override
                    protected InputStream getModelResourceAsStream() throws IOException {
                        final Resource resource = new ClassPathResource("wro/wro.xml");
                        LOGGER.warn("Resource_resource_[" + resource + "]");
                        final InputStream stream = resource.getInputStream();
                        return stream;
                    }
                };
            }
        };
    }
}

