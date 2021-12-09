package com.codegym.project.users.shipperProfile;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ShipperRegisterForm {
    private MultipartFile driverLicense;

    private MultipartFile idCardFront;

    private MultipartFile idCardBack;

    private MultipartFile vehicleOwnershipCertificate;

    private String profession;
}
