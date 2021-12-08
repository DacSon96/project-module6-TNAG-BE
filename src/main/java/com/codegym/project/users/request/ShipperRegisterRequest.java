package com.codegym.project.users.request;

import com.codegym.project.users.shipperProfile.ShipperProfile;
import com.codegym.project.users.users.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ShipperRegisterRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private ShipperProfile shipperProfile;

    private Boolean status;

    public ShipperRegisterRequest() {
    }

    public ShipperRegisterRequest(User user, ShipperProfile shipperProfile, Boolean status) {
        this.user = user;
        this.shipperProfile = shipperProfile;
        this.status = status;
    }
}
