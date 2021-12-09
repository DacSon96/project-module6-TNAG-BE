package com.codegym.project.users.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShipperRequestRepository extends JpaRepository<ShipperRegisterRequest, Long> {
    Iterable<ShipperRegisterRequest> getAllByStatus(Boolean status);

}
