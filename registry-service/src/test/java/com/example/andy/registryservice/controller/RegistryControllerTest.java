package com.example.andy.registryservice.controller;

import com.example.andy.registryservice.persistence.dao.services.interfaces.IRegistryService;
import com.example.andy.registryservice.persistence.model.Registry;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RegistryController.class)
public class RegistryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRegistryService registryService;

    @Autowired
    private ObjectMapper mapper;

    static List<Registry> devices = new ArrayList<>();

    static {
        devices.add(new Registry(1, "Apple", "Iphone 10", "01ABCDEF01", "01-23-45-67-89-AB", "172.16.0.50", "255.255.0.0"));
        devices.add(new Registry(2, "Apple", "Iphone 11", "01ABCDEF02", "01-23-45-67-22-AB", "172.16.123.50", "255.255.0.0"));
        devices.add(new Registry(3, "Samsung", "Galaxy S22", "01ABCDEF03", "11-33-45-67-22-AB", "172.16.123.168", "255.255.128.0"));
        devices.add(new Registry(4, "Samsung", "Galaxy S22 Ultra", "01ABCDEF04", "11-DC-45-67-22-AB", "172.168.123.168", "255.255.224.0"));
        devices.add(new Registry(5, "Xiaomi", "Mi Mix Ultra", "01ABCDEF05", "11-DC-45-AA-22-AB", "192.168.0.101", "255.255.224.0"));
    }

    @Test
    public void givenValidDevices_thenReturnDevicesInRegistry() {
        Mockito.when(registryService.findAllDevicesInRegistry()).thenReturn(devices);
        System.out.println(registryService.findAllDevicesInRegistry());
        try {
            mockMvc.perform(get("/api/registries"))
                    .andExpect(status().isOk())
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenDevicesEmptyList_thenReturnNoContentStatusCode() {
        Mockito.when(registryService.findAllDevicesInRegistry()).thenReturn(Collections.emptyList());
        System.out.println(registryService.findAllDevicesInRegistry());
        try {
            mockMvc.perform(get("/api/registries"))
                    .andExpect(status().isNoContent())
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenInValidRequest_thenReturnInternalServerErrorStatusCode() {
        Mockito.when(registryService.findAllDevicesInRegistry()).thenReturn(null);
        System.out.println(registryService.findAllDevicesInRegistry());
        try {
            mockMvc.perform(get("/api/registries"))
                    .andExpect(status().is5xxServerError())
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenValidDeviceId_thenDeviceShouldBeReturned() {
        Integer id = 1;
        try {
            Registry deviceById = devices.stream().filter(device -> device.getId().equals(id)).findFirst().get();
            Mockito.when(registryService.findDeviceById(id)).thenReturn(deviceById);
            mockMvc.perform(get("/api/registries/{id}" , id))
                    .andExpect(status().isOk())
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenValidDeviceVendor_thenDevicesShouldBeReturned() {
        String vendor = "Samsung";
        try {
            List<Registry> devicesByVendor = devices.stream().filter(device -> device.getVendor().contains(vendor)).collect(Collectors.toList());
            Mockito.when(registryService.findDeviceByVendor(vendor)).thenReturn(devicesByVendor);
            mockMvc.perform(get("/api/registries/vendor/{vendor}", vendor))
                    .andExpect(status().isOk())
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenValidDeviceModel_thenDevicesShouldBeReturned() {
        String model = "Galaxy";
        try {
            List<Registry> devicesByModel = devices.stream().filter(device -> device.getModel().contains(model)).collect(Collectors.toList());
            Mockito.when(registryService.findDeviceByModel(model)).thenReturn(devicesByModel);
            mockMvc.perform(get("/api/registries/model/{model}", model))
                    .andExpect(status().isOk())
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenValidDeviceToSave_thenSaveDeviceToRegistryAndReturnInResponse() throws Exception {
        Registry device = new Registry("Apple", "Iphone 10", "01ABCDEF01", "01-23-45-67-89-AB");
        Mockito.when(registryService.saveDeviceIntoRegistry(device)).thenReturn(device);
        mockMvc.perform(post("/api/registries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(device))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

    }
}