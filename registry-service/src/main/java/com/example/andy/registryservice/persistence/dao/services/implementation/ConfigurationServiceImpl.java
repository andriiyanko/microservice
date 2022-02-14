package com.example.andy.registryservice.persistence.dao.services.implementation;

import com.example.andy.registryservice.exceptions.RestTemplateErrorHandler;
import com.example.andy.registryservice.persistence.dao.services.interfaces.IConfigurationService;
import com.example.andy.registryservice.persistence.model.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConfigurationServiceImpl implements IConfigurationService {
    private RestTemplate restTemplate;
    private final String URL = "http://localhost:9001/api/configurations/";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Configuration fetchConfigurationBySerialNumber(String serialNumber) {
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());
        return restTemplate.getForObject(URL + serialNumber, Configuration.class);
    }
}
