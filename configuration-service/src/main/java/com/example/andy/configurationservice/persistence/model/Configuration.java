package com.example.andy.configurationservice.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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

    @NotBlank(message = "Ip address is mandatory")
    @Pattern(regexp = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$", message = "Ip address must have this format 192.168.0.0")
    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @NotBlank(message = "Subnet mask is mandatory")
    @Pattern(regexp = "^(255)\\.(0|128|192|224|240|248|252|255)\\.(0|128|192|224|240|248|252|255)\\.(0|128|192|224|240|248|252|255)$",
            message = "Subnet mask must have this format 255.(0|128|192|224|240|248|252|255).(0|128|192|224|240|248|252|255).(0|128|192|224|240|248|252|255)")
    @Column(name = "subnet_mask", nullable = false)
    private String subnetMask;

    public Configuration(String serialNumber, String ipAddress, String subnetMask) {
        this.serialNumber = serialNumber;
        this.ipAddress = ipAddress;
        this.subnetMask = subnetMask;
    }
}
