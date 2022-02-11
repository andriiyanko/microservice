package com.example.andy.registryservice.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "registry")
public class Registry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String vendor;
    private String model;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "subnet_mask")
    private String subnetMask;

    public Registry(String vendor, String model, String serialNumber, String macAddress) {
        this.vendor = vendor;
        this.model = model;
        this.serialNumber = serialNumber;
        this.macAddress = macAddress;
    }

    public Registry(String vendor, String model, String serialNumber, String macAddress, String ipAddress, String subnetMask) {
        this.vendor = vendor;
        this.model = model;
        this.serialNumber = serialNumber;
        this.macAddress = macAddress;
        this.ipAddress = ipAddress;
        this.subnetMask = subnetMask;
    }

}
