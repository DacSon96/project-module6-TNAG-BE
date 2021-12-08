package com.codegym.project.users.shipperProfile;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class ShipperProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String driverLicense;

    private String idCardFront;

    private String idCardBack;

    private String vehicleOwnershipCertificate;

    private String profession;

    public ShipperProfile() {
    }

    public ShipperProfile(String driverLicense, String idCardFront, String idCardBack, String vehicleOwnershipCertificate, String profession) {
        this.driverLicense = driverLicense;
        this.idCardFront = idCardFront;
        this.idCardBack = idCardBack;
        this.vehicleOwnershipCertificate = vehicleOwnershipCertificate;
        this.profession = profession;
    }
}
