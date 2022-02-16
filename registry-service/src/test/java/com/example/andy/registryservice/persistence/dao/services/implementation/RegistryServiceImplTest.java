package com.example.andy.registryservice.persistence.dao.services.implementation;

import com.example.andy.registryservice.exceptions.ResourceNotFoundException;
import com.example.andy.registryservice.persistence.dao.repositories.RegistryRepository;
import com.example.andy.registryservice.persistence.dao.services.interfaces.IRegistryService;
import com.example.andy.registryservice.persistence.model.Configuration;
import com.example.andy.registryservice.persistence.model.Registry;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegistryServiceImplTest {

    @TestConfiguration
    static class RegistryServiceImplTestContextConfiguration{
        @Bean
        public IRegistryService registryService(){
            return new RegistryServiceImpl();
        }

    }

    @Autowired
    private IRegistryService registryService;

    @MockBean
    private RegistryRepository registryRepository;

    @MockBean
    private ConfigurationServiceImpl configurationService;

    static List<Registry> devices = new ArrayList<>();

    static {
        devices.add(new Registry(1, "Apple", "Iphone 10", "01ABCDEF01", "01-23-45-67-89-AB", "172.16.0.50", "255.255.0.0"));
        devices.add(new Registry(2, "Apple", "Iphone 11", "01ABCDEF02", "01-23-45-67-22-AB", "172.16.123.50", "255.255.0.0"));
        devices.add(new Registry(3, "Samsung", "Galaxy S22", "01ABCDEF03", "11-33-45-67-22-AB", "172.16.123.168", "255.255.128.0"));
        devices.add(new Registry(4, "Samsung", "Galaxy S22 Ultra", "01ABCDEF04", "11-DC-45-67-22-AB", "172.168.123.168", "255.255.224.0"));
        devices.add(new Registry(5, "Xiaomi", "Mi Mix Ultra", "01ABCDEF05", "11-DC-45-AA-22-AB", "192.168.0.101", "255.255.224.0"));
    }

    @Test
    public void whenValidConfigurations_thenDevicesInRegistryShouldBeFound() {
        Mockito.when(registryRepository.findAll()).thenReturn(devices);
        System.out.println(registryService.findAllDevicesInRegistry());
        Assert.assertEquals(devices, registryService.findAllDevicesInRegistry());

    }

    @Test
    public void whenRegistryIsEmpty_thenDevicesEmptyListShouldBeFound() {
        Mockito.when(registryRepository.findAll()).thenReturn(Collections.emptyList());
        System.out.println(registryService.findAllDevicesInRegistry());
        Assert.assertEquals(Collections.emptyList(), registryService.findAllDevicesInRegistry());

    }

    @Test
    public void whenValidDeviceId_thenDeviceFromRegistryShouldBeFound() {
        Integer id = 1;
        Optional<Registry> deviceOptional = devices.stream().filter(device -> device.getId().equals(id)).findFirst();
        Mockito.when(registryRepository.findById(id)).thenReturn(deviceOptional);
        System.out.println(registryService.findDeviceById(id));
        Assert.assertEquals(deviceOptional.get(), registryService.findDeviceById(id));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenInValidDeviceId_thenResourceNotFoundExceptionShouldBeThrown() {
        Integer id = 11;
        Optional<Registry> deviceOptional = devices.stream().filter(device -> device.getId().equals(id)).findFirst();
        Mockito.when(registryRepository.findById(id)).thenReturn(deviceOptional);
        System.out.println(registryService.findDeviceById(id));
        Assert.assertEquals(deviceOptional.get(), registryService.findDeviceById(id));
    }

    @Test
    public void whenValidDeviceVendor_thenDeviceFromRegistryShouldBeFound() {
        String vendor = "Apple";
        List<Registry> devicesByVendor = devices.stream().filter(device -> device.getVendor().contains(vendor)).collect(Collectors.toList());
        Mockito.when(registryRepository.findRegistryByVendorContainingIgnoreCase(vendor)).thenReturn(devicesByVendor);
        System.out.println(registryService.findDeviceByVendor(vendor));
        Assert.assertEquals(devicesByVendor, registryService.findDeviceByVendor(vendor));

    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenInValidDeviceVendor_thenResourceNotFoundExceptionShouldBeThrow() {
        String vendor = "LG";
        List<Registry> devicesByVendor = devices.stream().filter(device -> device.getVendor().equals(vendor)).collect(Collectors.toList());
        Mockito.when(registryRepository.findRegistryByVendorContainingIgnoreCase(vendor)).thenReturn(devicesByVendor);
        System.out.println(registryService.findDeviceByVendor(vendor));
        Assert.assertEquals(devicesByVendor, registryService.findDeviceByVendor(vendor));

    }

    @Test
    public void whenValidDeviceModel_thenDeviceFromRegistryShouldBeFound() {
        String model = "Galaxy";
        List<Registry> devicesByModel = devices.stream().filter(device -> device.getModel().contains(model)).collect(Collectors.toList());
        Mockito.when(registryRepository.findRegistryByModelContainingIgnoreCase(model)).thenReturn(devicesByModel);
        System.out.println(registryService.findDeviceByModel(model));
        Assert.assertEquals(devicesByModel, registryService.findDeviceByModel(model));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenInValidDeviceModel_thenResourceNotFoundExceptionShouldBeThrow() {
        String model = "Poco";
        List<Registry> devicesByModel = devices.stream().filter(device -> device.getModel().contains(model)).collect(Collectors.toList());
        Mockito.when(registryRepository.findRegistryByModelContainingIgnoreCase(model)).thenReturn(devicesByModel);
        System.out.println(registryService.findDeviceByModel(model));
        Assert.assertEquals(devicesByModel, registryService.findDeviceByModel(model));
    }


    @Test
    public void whenValidDevice_thenSaveToTheRegistryAndReturn() {
        Registry device = new Registry(1, "Apple", "Iphone 10", "01ABCDEF01", "01-23-45-67-89-AB", "192.168.150.40", "255.255.255.0");
        Configuration configuration = new Configuration(1, "01ABCDEF01", "192.168.150.40", "255.255.255.0");
        Mockito.when(registryRepository.save(device)).thenReturn(device);
        Mockito.when(configurationService.fetchConfigurationBySerialNumber(device.getSerialNumber())).thenReturn(configuration);
        System.out.println(registryService.saveDeviceIntoRegistry(device));
        Assert.assertEquals(device, registryService.saveDeviceIntoRegistry(device));
    }

}