package com.digitalhealthvault.security.config;

import com.digitalhealthvault.security.crypto.NimbusSignerFactory;
import com.digitalhealthvault.security.crypto.NimbusVerifierFactory;
import com.digitalhealthvault.security.crypto.RsaSigningKeyProvider;
import com.digitalhealthvault.security.crypto.SigningKeyProvider;
import com.digitalhealthvault.security.properties.SecurityProperties;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class KeyConfiguration implements ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public SigningKeyProvider signingKeyProvider(
            SecurityProperties properties) {

        return new RsaSigningKeyProvider(
                properties,
                resourceLoader
        );
    }

    @Bean
    public NimbusSignerFactory nimbusSignerFactory() {
        return new NimbusSignerFactory();
    }

    @Bean
    public NimbusVerifierFactory nimbusVerifierFactory() {
        return new NimbusVerifierFactory();
    }

}