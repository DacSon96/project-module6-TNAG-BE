package com.codegym.project.users.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShipperRequestService implements IShipperRequestService{
    @Autowired
    private IShipperRequestRepository shipperRequestRepository;

    @Override
    public Page<ShipperRegisterRequest> findAll(Pageable pageable) {
        return shipperRequestRepository.findAll(pageable);
    }

    @Override
    public Optional<ShipperRegisterRequest> findById(Long id) {
        return shipperRequestRepository.findById(id);
    }

    @Override
    public ShipperRegisterRequest save(ShipperRegisterRequest shipperRegisterRequest) {
        return shipperRequestRepository.save(shipperRegisterRequest);
    }

    @Override
    public void deleteById(Long id) {
        shipperRequestRepository.deleteById(id);
    }

    @Override
    public Iterable<ShipperRegisterRequest> getAllByStatus(boolean status) {
        return shipperRequestRepository.getAllByStatus(status);
    }

    @Override
    public ShipperRegisterRequest updateStatus(ShipperRegisterRequest shipperRegisterRequest, boolean status) {
        shipperRegisterRequest.setStatus(status);
        save(shipperRegisterRequest);
        return shipperRegisterRequest;
    }

    @Override
    public Iterable<ShipperRegisterRequest> findAll() {
        return shipperRequestRepository.findAll();
    }
}
