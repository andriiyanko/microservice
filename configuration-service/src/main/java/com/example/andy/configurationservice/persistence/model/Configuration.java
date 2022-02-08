package com.example.andy.configurationservice.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "configuration")
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Serial number is mandatory")
    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @NotBlank(message = "Ip address is mandatory. Ip address must have this format 192.168.0.0")
    @Size(max = 16, message = "Ip address size should not be more than 16 characters ")
    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @NotBlank(message = "Serial number is mandatory. Subnet mask must have this format 255.0.0.0")
    @Size(max = 16, message = "Subnet mask size should not be more than 16 characters ")
    @Column(name = "subnet_mask", nullable = false)
    private String subnetMask;

    public Configuration(String serialNumber, String ipAddress, String subnetMask) {
        this.serialNumber = serialNumber;
        this.ipAddress = ipAddress;
        this.subnetMask = subnetMask;
    }
}
