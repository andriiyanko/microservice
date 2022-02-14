package com.example.andy.registryservice.persistence.dao.services.implementation;

import com.example.andy.registryservice.exceptions.RestTemplateErrorHandler;
import com.example.andy.registryservice.persistence.dao.repositories.RegistryRepository;
import com.example.andy.registryservice.persistence.dao.services.interfaces.IRegistryService;
import com.example.andy.registryservice.persistence.model.Configuration;
import com.example.andy.registryservice.persistence.model.Registry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RegistryServiceImpl implements IRegistryService {
    private RegistryRepository registryRepository;
    private RestTemplate restTemplate;

    private final String URL = "http://localhost:9001/api/configurations/";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setRegistryRepository(RegistryRepository registryRepository) {
        this.registryRepository = registryRepository;
    }

    public Configuration fetchConfigurationBySerialNumber(String serialNumber){
            restTemplate.setErrorHandler(new RestTemplateErrorHandler());
            return restTemplate.getForObject(URL + serialNumber, Configuration.class);
    }

    @Override
    public Registry saveRegistry(Registry registry) {
        Configuration configuration = fetchConfigurationBySerialNumber(registry.getSerialNumber());

        registry.setIpAddress(configuration.getIpAddress());
        registry.setSubnetMask(configuration.getSubnetMask());

        return registryRepository.save(registry);

    }
}
