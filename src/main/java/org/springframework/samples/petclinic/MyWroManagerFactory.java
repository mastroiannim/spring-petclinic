package org.springframework.samples.petclinic;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;

import ro.isdc.wro.manager.factory.ConfigurableWroManagerFactory;
import ro.isdc.wro.model.factory.WroModelFactory;
import ro.isdc.wro.model.factory.XmlModelFactory;

public class MyWroManagerFactory extends ConfigurableWroManagerFactory {

    @Autowired
    ResourceLoader resourceLoader;

    @Override
    protected WroModelFactory newModelFactory() {
        return new XmlModelFactory() {
            @Override
            protected InputStream getModelResourceAsStream() throws IOException {
                final Resource resource = resourceLoader.getResource("classpath:/wro/wro.xml");
                final InputStream stream = resource.getInputStream();
                return stream;
            }
        };
    }
}