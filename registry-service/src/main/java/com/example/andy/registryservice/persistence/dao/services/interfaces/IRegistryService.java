package com.example.andy.registryservice.persistence.dao.services.interfaces;

import com.example.andy.registryservice.persistence.model.Registry;

public interface IRegistryService {
    Registry saveDeviceIntoRegistry(Registry registry);
    Iterable<Registry> findAllDevicesInRegistry();
}
