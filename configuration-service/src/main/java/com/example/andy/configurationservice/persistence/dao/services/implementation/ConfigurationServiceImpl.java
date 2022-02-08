package com.example.andy.configurationservice.persistence.dao.services.implementation;

import com.example.andy.configurationservice.persistence.dao.repositories.ConfigurationRepository;
import com.example.andy.configurationservice.persistence.dao.services.interfaces.IConfigurationService;
import com.example.andy.configurationservice.persistence.model.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ConfigurationServiceImpl implements IConfigurationService {

    private ConfigurationRepository configurationRepository;

    @Autowired
    public void setConfigurationRepository(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Override
    public Iterable<Configuration> findAllConfigurations() {
        log.info("Inside findAllConfigurations of ConfigurationServiceImpl");
        return configurationRepository.findAll();
    }

    @Override
    public Optional<Configuration> findConfigurationBySerialNumber(String serialNumber) {
        log.info("Inside findConfigurationBySerialNumber of ConfigurationServiceImpl");
        return configurationRepository.findConfigurationBySerialNumber(serialNumber);
    }
}
