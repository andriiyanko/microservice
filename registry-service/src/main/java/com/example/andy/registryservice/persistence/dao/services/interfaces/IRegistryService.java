package com.example.andy.registryservice.persistence.dao.services.interfaces;

import com.example.andy.registryservice.persistence.model.Registry;

public interface IRegistryService {
    Registry saveRegistry(Registry registry);
}
