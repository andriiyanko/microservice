package com.example.andy.registryservice.persistence.model;

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
@Table(name = "registry")
public class Registry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Vendor is mandatory")
    private String vendor;

    @NotBlank(message = "Model is mandatory")
    private String model;

    @NotBlank(message = "Serial number is mandatory")
    @Column(name = "serial_number")
    private String serialNumber;

    @NotBlank(message = "Mac address is mandatory")
    @Pattern(regexp = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})|([0-9a-fA-F]{4}\\.[0-9a-fA-F]{4}\\.[0-9a-fA-F]{4})$",
            message = "Mac address must be 12-43-54-77-99-AC or 12:43:54:77:99:DC or 0123.4567.89AB")
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
