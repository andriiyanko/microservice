package com.example.andy.configurationservice.persistence.dao.services.interfaces;

import com.example.andy.configurationservice.persistence.model.Configuration;

import java.util.List;
import java.util.Optional;

public interface IConfigurationService {
    List<Configuration> findAllConfigurations();
    Configuration findConfigurationBySerialNumber(String serialNumber);
    Configuration saveConfiguration(Configuration configuration);
}
