package com.example.andy.configurationservice.persistence.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "configuration")
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "subnet_mask")
    private String subnetMask;

    public Configuration(String serialNumber, String ipAddress, String subnetMask) {
        this.serialNumber = serialNumber;
        this.ipAddress = ipAddress;
        this.subnetMask = subnetMask;
    }
}
