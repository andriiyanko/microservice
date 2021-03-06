package com.example.andy.configurationservice.controller;

import com.example.andy.configurationservice.persistence.dao.services.interfaces.IConfigurationService;
import com.example.andy.configurationservice.persistence.model.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
            List<Configuration> configurations = configurationService.findAllConfigurations();
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
        Configuration configuration = configurationService.findConfigurationBySerialNumber(serialNumber);

        return new ResponseEntity<>(configuration,HttpStatus.OK);
    }

    @PostMapping("/configurations")
    public ResponseEntity<Configuration> createConfiguration( @Validated @RequestBody Configuration configurationRequest){
        log.info("Inside createConfiguration method of ConfigurationController");
        Configuration configuration = configurationService.saveConfiguration(new Configuration(configurationRequest.getSerialNumber(), configurationRequest.getIpAddress(), configurationRequest.getSubnetMask()));
        return new ResponseEntity<>(configuration, HttpStatus.CREATED);

    }

}
