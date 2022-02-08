package com.example.andy.configurationservice.persistence.dao.services.interfaces;

import com.example.andy.configurationservice.persistence.model.Configuration;

public interface IConfigurationService {
    Iterable<Configuration> findAllConfigurations();
}
