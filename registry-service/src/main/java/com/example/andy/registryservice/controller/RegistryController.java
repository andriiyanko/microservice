package com.example.andy.registryservice.controller;

import com.example.andy.registryservice.exceptions.ResourceNotFoundException;
import com.example.andy.registryservice.persistence.dao.services.interfaces.IRegistryService;
import com.example.andy.registryservice.persistence.model.Registry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RegistryController {
    private IRegistryService registryService;

    @Autowired
    public void setRegistryService(IRegistryService registryService) {
        this.registryService = registryService;
    }


    @PostMapping("/registries")
    public ResponseEntity<Registry> createRegistry(@RequestBody Registry registry){
        Registry saveRegistry = registryService.saveRegistry(new Registry(registry.getVendor(), registry.getModel(), registry.getSerialNumber(), registry.getMacAddress()));
        return new ResponseEntity<>(saveRegistry, HttpStatus.CREATED);
    }

}
