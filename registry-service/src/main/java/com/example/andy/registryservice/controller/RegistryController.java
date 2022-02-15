package com.example.andy.registryservice.controller;

import com.example.andy.registryservice.exceptions.ResourceNotFoundException;
import com.example.andy.registryservice.persistence.dao.services.interfaces.IRegistryService;
import com.example.andy.registryservice.persistence.model.Registry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class RegistryController {
    private IRegistryService registryService;

    @Autowired
    public void setRegistryService(IRegistryService registryService) {
        this.registryService = registryService;
    }


    @GetMapping("/registries")
    public ResponseEntity<List<Registry>> getAllDevicesFromRegistry(){
        log.info("Inside getAllDevicesFromRegistry method of RegistryController");

        try {
            List<Registry> devices = new ArrayList<>();
            registryService.findAllDevicesInRegistry().forEach(devices::add);
            if (devices.isEmpty()){
                return new ResponseEntity<>(Collections.emptyList(),HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(devices, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/registries")
    public ResponseEntity<Registry> createDeviceIntoRegistry(@Validated @RequestBody Registry registry){
        log.info("Inside createDeviceIntoRegistry method of RegistryController");
        Registry saveRegistry = registryService.saveDeviceIntoRegistry(new Registry(registry.getVendor(), registry.getModel(),
                registry.getSerialNumber(), registry.getMacAddress()));

        return new ResponseEntity<>(saveRegistry, HttpStatus.CREATED);
    }

}
