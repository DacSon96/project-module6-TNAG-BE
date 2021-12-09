package com.codegym.project.users.shipperProfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShipperProfileRepository extends JpaRepository<ShipperProfile, Long> {
}
