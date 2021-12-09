package com.codegym.project.users.request;

import com.codegym.project.IGeneralService;

public interface IShipperRequestService extends IGeneralService<ShipperRegisterRequest> {
    Iterable<ShipperRegisterRequest> getAllByStatus(boolean status);

    ShipperRegisterRequest updateStatus(ShipperRegisterRequest shipperRegisterRequest, boolean status);

    Iterable<ShipperRegisterRequest> findAll();
}
