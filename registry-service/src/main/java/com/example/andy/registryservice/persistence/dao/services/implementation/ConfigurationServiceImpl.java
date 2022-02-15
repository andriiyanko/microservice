package com.example.andy.registryservice.persistence.dao.services.implementation;

import com.example.andy.registryservice.exceptions.RestTemplateErrorHandler;
import com.example.andy.registryservice.persistence.dao.services.interfaces.IConfigurationService;
import com.example.andy.registryservice.persistence.model.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@PropertySource("classpath:url.properties")
public class ConfigurationServiceImpl implements IConfigurationService {
    private RestTemplate restTemplate;

    @Value("${url}")
    private String URL;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Configuration fetchConfigurationBySerialNumber(String serialNumber) {
        log.info("Inside fetchConfigurationBySerialNumber of ConfigurationServiceImpl");
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());
        return restTemplate.getForObject(URL + serialNumber, Configuration.class);
    }
}
