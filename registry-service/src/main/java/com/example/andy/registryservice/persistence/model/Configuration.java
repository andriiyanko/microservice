package com.example.andy.registryservice.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Configuration {

    private Integer id;
    private String serialNumber;
    private String ipAddress;
    private String subnetMask;

}
