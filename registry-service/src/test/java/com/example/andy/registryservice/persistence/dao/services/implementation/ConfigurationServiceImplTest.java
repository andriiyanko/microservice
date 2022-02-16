package com.example.andy.registryservice.persistence.dao.services.implementation;

import com.example.andy.registryservice.SpringTestConfig;
import com.example.andy.registryservice.persistence.model.Configuration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringTestConfig.class)
public class ConfigurationServiceImplTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ConfigurationServiceImpl configurationService;

    //mock server
    //configure the server to return a particular object when a specific request is dispatched through RestTemplate instance.
    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }


    @Test
    public void givenMockingIsDoneByMockRestServiceServer_whenGetIsCalled_thenReturnsMockedConfigurationObject(){
        Configuration configuration = new Configuration(1, "01ABCDEF01", "192.168.150.40", "255.255.255.0");
        try {
            mockServer.expect(ExpectedCount.once(),
                    requestTo(new URI("http://localhost:9001/api/configurations/" + configuration.getSerialNumber())))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(mapper.writeValueAsString(configuration)));

            Configuration configurationBySerialNumber = configurationService.fetchConfigurationBySerialNumber(configuration.getSerialNumber());
            mockServer.verify();
            Assert.assertEquals(configuration, configurationBySerialNumber);
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}