package com.example.andy.registryservice.persistence.dao.services.interfaces;

import com.example.andy.registryservice.persistence.model.Configuration;

public interface IConfigurationService {
    Configuration fetchConfigurationBySerialNumber(String serialNumber);
}
