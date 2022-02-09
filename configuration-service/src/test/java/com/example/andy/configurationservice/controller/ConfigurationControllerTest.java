package com.example.andy.configurationservice.controller;

import com.example.andy.configurationservice.persistence.dao.services.interfaces.IConfigurationService;
import com.example.andy.configurationservice.persistence.dao.services.interfaces.ISerialNumber;
import com.example.andy.configurationservice.persistence.model.Configuration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ConfigurationController.class)
public class ConfigurationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IConfigurationService configurationService;

    @MockBean
    private ISerialNumber iSerialNumber;

    @Autowired
    private ObjectMapper mapper;

    static List<Configuration> configurations = new ArrayList<>();

    static {
        configurations.add(new Configuration(1,"01ABCDEF01", "192.168.0.101", "255.255.255.0"));
        configurations.add(new Configuration(2,"01ABCDEF02", "192.168.120.250", "255.255.0.0"));
        configurations.add(new Configuration(3,"01ABCDEF03", "176.16.0.50", "255.255.255.255"));
    }

    @Test
    public void givenValidConfigurations_thenReturnConfigurationList() {
        try {
            Mockito.when(configurationService.findAllConfigurations()).thenReturn(configurations);
            System.out.println(configurationService.findAllConfigurations());
            mockMvc.perform(get("/api/configurations"))
                    .andExpect(status().isOk())
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenInValidConfigurations_thenReturnInternalServerErrorStatusCode() {
        try {
            Mockito.when(configurationService.findAllConfigurations()).thenReturn(null);
            System.out.println(configurationService.findAllConfigurations());
            mockMvc.perform(get("/api/configurations"))
                    .andExpect(status().is5xxServerError())
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenEmptyConfigurations_thenReturnNoContentStatusCode() {
        try {
            List<Configuration> configurationList = new ArrayList<>();
            Mockito.when(configurationService.findAllConfigurations()).thenReturn(configurationList);
            System.out.println(configurationService.findAllConfigurations());
            mockMvc.perform(get("/api/configurations"))
                    .andExpect(status().isNoContent())
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenValidConfigurationSerialNumber_thenReturnConfiguration() {
        String serialNumber = "01ABCDEF01";
        try {
            Optional<Configuration> configurationOptional = configurations.stream()
                    .filter(configuration -> configuration.getSerialNumber().equals(serialNumber))
                    .findFirst();
            Mockito.when(configurationService.findConfigurationBySerialNumber(serialNumber)).thenReturn(configurationOptional);
            mockMvc.perform(get("/api/configurations/{serialNumber}", serialNumber))
                    .andExpect(status().isOk())
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenValidConfiguration_thenSaveAndReturnCreatedStatusCode() {
        try {
            Configuration configuration = new Configuration(3,"01ABCDEF07", "192.168.0.101", "255.255.255.0");
            Mockito.when(configurationService.saveConfiguration(configuration)).thenReturn(configuration);
            System.out.println(configurationService.saveConfiguration(configuration));
            Mockito.when(iSerialNumber.checkUniqueSerialNumber("01ABCDEF07")).thenReturn(true);

            mockMvc.perform(post("/api/configurations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(configurationService.saveConfiguration(configuration)))
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andDo(print());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenConfigurationWithNotUniqueSerialNumber_thenReturnForbiddenStatusCode() {
        try {
            Configuration configuration = new Configuration(1,"01ABCDEF07", "192.168.0.101", "255.255.255.0");
            Mockito.when(configurationService.saveConfiguration(configuration)).thenReturn(configuration);
            System.out.println(configurationService.saveConfiguration(configuration));
            Mockito.when(iSerialNumber.checkUniqueSerialNumber("01ABCDEF07")).thenReturn(false);

            mockMvc.perform(post("/api/configurations")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(configurationService.saveConfiguration(configuration)))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden())
                    .andDo(print());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenInValidConfigurationToSave_thenReturnInternalServerErrorStatusCode() {
        try {
            Mockito.when(configurationService.saveConfiguration(Mockito.any())).thenReturn(null);

            mockMvc.perform(post("/api/configurations")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(configurationService.saveConfiguration(Mockito.any())))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError())
                    .andDo(print());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}