package org.springframework.samples.petclinic;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import ro.isdc.wro.manager.factory.ConfigurableWroManagerFactory;
import ro.isdc.wro.model.factory.WroModelFactory;
import ro.isdc.wro.model.factory.XmlModelFactory;

public class MyWroManagerFactory extends ConfigurableWroManagerFactory {

    @Override
    protected WroModelFactory newModelFactory() {
        return new XmlModelFactory() {
            @Override
            protected InputStream getModelResourceAsStream() throws IOException {

                final String resourceLocation = "src/main/wro/" + getDefaultModelFilename();
                final InputStream stream = new FileInputStream(resourceLocation);
                return stream;
            }
        };
    }
}