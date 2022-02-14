package com.example.andy.configurationservice.persistence.dao.services.implementation;

import com.example.andy.configurationservice.exceptions.UniqueElementException;
import com.example.andy.configurationservice.persistence.dao.repositories.ConfigurationRepository;
import com.example.andy.configurationservice.persistence.dao.services.interfaces.IConfigurationService;
import com.example.andy.configurationservice.persistence.dao.services.interfaces.ISerialNumber;
import com.example.andy.configurationservice.persistence.model.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ConfigurationServiceImpl implements IConfigurationService, ISerialNumber {

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

    @Override
    public Configuration saveConfiguration(Configuration configuration) {
        log.info("Inside saveConfiguration of ConfigurationServiceImpl");
        if (!checkUniqueSerialNumber(configuration.getSerialNumber()))
            throw new UniqueElementException("Serial number must be unique");
        return configurationRepository.save(configuration);
    }

    @Override
    public boolean checkUniqueSerialNumber(String serialNumber) {
        log.info("Inside checkUniqueSerialNumber method of ConfigurationServiceImpl");
        StringBuilder serialNumberBuilder = new StringBuilder(serialNumber);

        List<Configuration> configurations = new ArrayList<>();
        configurationRepository.findAll().forEach(configurations::add);

        for (Configuration configuration : configurations) {
            if (serialNumberBuilder.toString().trim().equals(configuration.getSerialNumber()))
                return false;
        }
        return true;
    }
}
