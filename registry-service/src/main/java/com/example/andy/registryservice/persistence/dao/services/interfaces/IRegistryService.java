package com.example.andy.registryservice.persistence.dao.services.interfaces;

import com.example.andy.registryservice.persistence.model.Registry;

import java.util.List;
import java.util.Optional;

public interface IRegistryService {
    Registry saveDeviceIntoRegistry(Registry registry);
    List<Registry> findAllDevicesInRegistry();
}
