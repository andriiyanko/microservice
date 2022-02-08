package com.example.andy.configurationservice.controller;

import com.example.andy.configurationservice.exceptions.ResourceNotFoundException;
import com.example.andy.configurationservice.exceptions.UniqueElementException;
import com.example.andy.configurationservice.persistence.dao.services.interfaces.IConfigurationService;
import com.example.andy.configurationservice.persistence.model.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class ConfigurationController {
    private IConfigurationService configurationService;

    @Autowired
    public void setConfigurationService(IConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @GetMapping("/configurations")
    public ResponseEntity<List<Configuration>> getAllConfigurations(){
        log.info("Inside getAllConfigurations method of ConfigurationController");
        try {
            List<Configuration> configurations = new ArrayList<>();
            configurationService.findAllConfigurations().forEach(configurations::add);
            if (configurations.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(configurations, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/configurations/{serialNumber}")
    public ResponseEntity<Configuration> getConfigurationBySerialNumber(@PathVariable("serialNumber") String serialNumber){
        log.info("Inside getConfigurationBySerialNumber method of ConfigurationController");
        Configuration configuration = configurationService.findConfigurationBySerialNumber(serialNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Configuration with serial number" + serialNumber));
        return new ResponseEntity<>(configuration,HttpStatus.OK);
    }

    @PostMapping("/configurations")
    public ResponseEntity<Configuration> createConfiguration( @Validated @RequestBody Configuration configurationRequest){
        log.info("Inside createConfiguration method of ConfigurationController");
        boolean uniqueSerialNumber = checkUniqueSerialNumber(configurationRequest, configurationService);

        if (!uniqueSerialNumber){
            throw new UniqueElementException("Serial number must be unique!");
        }

        try {
            Configuration configuration = configurationService.saveConfiguration(new Configuration(configurationRequest.getSerialNumber(), configurationRequest.getIpAddress(), configurationRequest.getSubnetMask()));
            return new ResponseEntity<>(configuration, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean checkUniqueSerialNumber(Configuration configurationRequest, IConfigurationService configurationService){
        StringBuilder serialNumberRequest = new StringBuilder(configurationRequest.getSerialNumber());
        serialNumberRequest.trimToSize();

        List<Configuration> configurations = new ArrayList<>();
        configurationService.findAllConfigurations().forEach(configurations::add);

        for (Configuration configuration : configurations) {
            if (serialNumberRequest.toString().equals(configuration.getSerialNumber()))
                return false;
        }
        return true;
    }

}
