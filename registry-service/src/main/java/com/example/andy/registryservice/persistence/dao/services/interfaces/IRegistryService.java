package com.example.andy.registryservice.persistence.dao.services.interfaces;

import com.example.andy.registryservice.persistence.model.Registry;

import java.util.List;

public interface IRegistryService {
    Registry saveDeviceIntoRegistry(Registry registry);
    List<Registry> findAllDevicesInRegistry();
    Registry findDeviceById(Integer id);
    List<Registry> findDeviceByVendor(String vendor);
    List<Registry> findDeviceByModel(String model);
}
