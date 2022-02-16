package com.example.andy.registryservice;

import com.example.andy.registryservice.persistence.dao.services.implementation.ConfigurationServiceImpl;
import com.example.andy.registryservice.persistence.dao.services.implementation.RegistryServiceImpl;
import com.example.andy.registryservice.persistence.dao.services.interfaces.ISerialNumber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("classpath:url.properties")
public class SpringTestConfig {
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ConfigurationServiceImpl getConfigurationService(){
        return new ConfigurationServiceImpl();
    }
}
