package com.example.andy.registryservice.controller;

import com.example.andy.registryservice.persistence.dao.services.interfaces.IRegistryService;
import com.example.andy.registryservice.persistence.model.Registry;
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
            List<Registry> devices = registryService.findAllDevicesInRegistry();
            if (devices.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(devices, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/registries/{id}")
    public ResponseEntity<Registry> getDeviceFromRegistryById(@PathVariable Integer id){
        log.info("Inside getDeviceFromRegistryById method of RegistryController");
        Registry device = registryService.findDeviceById(id);
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @GetMapping("/registries/vendor/{vendor}")
    public ResponseEntity<List<Registry>> getDeviceByVendor(@PathVariable("vendor") String vendor){
        List<Registry> devices = registryService.findDeviceByVendor(vendor);
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @GetMapping("/registries/model/{model}")
    public ResponseEntity<List<Registry>> getDeviceByModel(@PathVariable("model") String model){
        List<Registry> devices = registryService.findDeviceByModel(model);
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @PostMapping("/registries")
    public ResponseEntity<Registry> createDeviceIntoRegistry(@Validated @RequestBody Registry registry){
        log.info("Inside createDeviceIntoRegistry method of RegistryController");
        Registry saveRegistry = registryService.saveDeviceIntoRegistry(new Registry(registry.getVendor(), registry.getModel(),
                registry.getSerialNumber(), registry.getMacAddress()));

        return new ResponseEntity<>(saveRegistry, HttpStatus.CREATED);
    }

}
