package com.example.andy.registryservice.persistence.dao.services.implementation;

import com.example.andy.registryservice.exceptions.UniqueElementException;
import com.example.andy.registryservice.persistence.dao.repositories.RegistryRepository;
import com.example.andy.registryservice.persistence.dao.services.interfaces.IConfigurationService;
import com.example.andy.registryservice.persistence.dao.services.interfaces.IRegistryService;
import com.example.andy.registryservice.persistence.dao.services.interfaces.ISerialNumber;
import com.example.andy.registryservice.persistence.model.Configuration;
import com.example.andy.registryservice.persistence.model.Registry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RegistryServiceImpl implements IRegistryService, ISerialNumber{
    private RegistryRepository registryRepository;
    private IConfigurationService configurationService;

    @Autowired
    public void setRegistryRepository(RegistryRepository registryRepository) {
        this.registryRepository = registryRepository;
    }

    @Autowired
    public void setConfigurationService(IConfigurationService configurationService) {
        this.configurationService = configurationService;
    }


    @Override
    public Registry saveDeviceIntoRegistry(Registry registry) {
        log.info("Inside saveRegistry method of RegistryServiceImpl");

        if (!checkUniqueSerialNumber(registry.getSerialNumber()))
            throw new UniqueElementException("Device with this serial number already exists!");

        Configuration configuration = configurationService.fetchConfigurationBySerialNumber(registry.getSerialNumber());

        registry.setIpAddress(configuration.getIpAddress());
        registry.setSubnetMask(configuration.getSubnetMask());

        return registryRepository.save(registry);

    }

    @Override
    public Iterable<Registry> findAllDevicesInRegistry() {
        log.info("Inside findAllDevicesInRegistry method of RegistryServiceImpl");
        return registryRepository.findAll();
    }

    @Override
    public boolean checkUniqueSerialNumber(String serialNumber) {
        log.info("Inside checkUniqueSerialNumber method of RegistryServiceImpl");
        StringBuilder serialNumberBuilder = new StringBuilder(serialNumber);

        List<Registry> devices = new ArrayList<>();
        registryRepository.findAll().forEach(devices::add);

        for (Registry device : devices) {
            if (serialNumberBuilder.toString().trim().equals(device.getSerialNumber()))
                return false;
        }
        return true;
    }
}
