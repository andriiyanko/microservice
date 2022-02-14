package com.example.andy.registryservice.persistence.dao.services.implementation;

import com.example.andy.registryservice.persistence.dao.repositories.RegistryRepository;
import com.example.andy.registryservice.persistence.dao.services.interfaces.IConfigurationService;
import com.example.andy.registryservice.persistence.dao.services.interfaces.IRegistryService;
import com.example.andy.registryservice.persistence.model.Configuration;
import com.example.andy.registryservice.persistence.model.Registry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RegistryServiceImpl implements IRegistryService {
    private RegistryRepository registryRepository;
    private IConfigurationService configurationService;

    @Autowired
    public void setRegistryRepository(RegistryRepository registryRepository) {
        this.registryRepository = registryRepository;
    }

    @Autowired
    public void setConfigurationService(IConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @Override
    public Registry saveDeviceIntoRegistry(Registry registry) {
        log.info("Inside saveRegistry method of RegistryServiceImpl");
        Configuration configuration = configurationService.fetchConfigurationBySerialNumber(registry.getSerialNumber());

        registry.setIpAddress(configuration.getIpAddress());
        registry.setSubnetMask(configuration.getSubnetMask());

        return registryRepository.save(registry);

    }

    @Override
    public Iterable<Registry> findAllDevicesInRegistry() {
        log.info("Inside findAllDevicesFromRegistry method of RegistryServiceImpl");
        return registryRepository.findAll();
    }
}
