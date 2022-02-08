package com.example.andy.configurationservice.persistence.dao.services.interfaces;

import com.example.andy.configurationservice.persistence.model.Configuration;

import java.util.Optional;

public interface IConfigurationService {
    Iterable<Configuration> findAllConfigurations();
    Optional<Configuration> findConfigurationBySerialNumber(String serialNumber);
}
