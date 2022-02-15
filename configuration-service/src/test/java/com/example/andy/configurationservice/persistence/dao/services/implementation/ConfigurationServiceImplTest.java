package com.example.andy.configurationservice.persistence.dao.services.implementation;

import com.example.andy.configurationservice.persistence.dao.repositories.ConfigurationRepository;
import com.example.andy.configurationservice.persistence.dao.services.interfaces.IConfigurationService;
import com.example.andy.configurationservice.persistence.model.Configuration;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConfigurationServiceImplTest {

    @TestConfiguration
    static class ConfigurationServiceImplTestContextConfiguration{
        @Bean
        public IConfigurationService configurationService(){
            return new ConfigurationServiceImpl();
        }
    }

    @Autowired
    private IConfigurationService configurationService;

    @MockBean
    private ConfigurationRepository configurationRepository;

    static List<Configuration> configurations = new ArrayList<>();

    static {
        configurations.add(new Configuration("01ABCDEF01", "192.168.0.101", "255.255.255.0"));
        configurations.add(new Configuration("01ABCDEF02", "192.168.120.250", "255.255.0.0"));
        configurations.add(new Configuration("01ABCDEF03", "176.16.0.50", "255.255.255.255"));
    }

    @Test
    public void whenValidConfigurations_thenConfigurationsShouldBeFound() {
        Mockito.when(configurationRepository.findAll()).thenReturn(configurations);
        System.out.println(configurationService.findAllConfigurations());
        Assertions.assertEquals(configurations, configurationService.findAllConfigurations());
    }

    @Test
    public void whenValidSerialNumber_thenConfigurationShouldBeFound() {
        String serialNumber = "01ABCDEF01";
        Optional<Configuration> configurationOptional = configurations.stream().filter(configuration -> configuration.getSerialNumber().equals(serialNumber))
                .findFirst();
        Mockito.when(configurationRepository.findConfigurationBySerialNumber(serialNumber)).thenReturn(configurationOptional);
        System.out.println(configurationService.findConfigurationBySerialNumber(serialNumber));
        Assertions.assertEquals(configurationOptional.get(), configurationService.findConfigurationBySerialNumber(serialNumber));
    }

    @Test
    public void whenValidConfiguration_thenSaveAndReturnedSavedConfiguration() {
        Configuration configuration = new Configuration("01ABCDEF04", "192.192.120.250", "255.255.128.0");
        Mockito.when(configurationRepository.save(configuration)).thenReturn(configuration);
        System.out.println(configurationService.saveConfiguration(configuration));
        Assertions.assertEquals(configuration, configurationService.saveConfiguration(configuration));
    }

    @Test
    public void whenNotUniqueSerialNumber_thenReturnFalse() {
        ConfigurationServiceImpl spy = Mockito.spy(new ConfigurationServiceImpl());
        Mockito.doReturn(configurations).when(spy).findAllConfigurations();
        Mockito.doReturn(false).when(spy).checkUniqueSerialNumber("01ABCDEF01");
        spy.findAllConfigurations().forEach(s -> System.out.println(s));
        Assertions.assertFalse(spy.checkUniqueSerialNumber("01ABCDEF01"));
    }

    @Test
    public void whenUniqueSerialNumber_thenReturnTrue() {
        ConfigurationServiceImpl spy = Mockito.spy(new ConfigurationServiceImpl());
        Mockito.doReturn(configurations).when(spy).findAllConfigurations();
        Mockito.doReturn(true).when(spy).checkUniqueSerialNumber("01ABCDEF05");
        spy.findAllConfigurations().forEach(s -> System.out.println(s));
        System.out.println(spy.checkUniqueSerialNumber("01ABCDEF05"));
        Assertions.assertTrue(spy.checkUniqueSerialNumber("01ABCDEF05"));
    }

}