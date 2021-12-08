package com.codegym.project.users.shipperProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShipperProfileService implements IShipperProfileService{
    @Autowired
    private IShipperProfileRepository shipperProfileRepository;

    @Override
    public Page<ShipperProfile> findAll(Pageable pageable) {
        return shipperProfileRepository.findAll(pageable);
    }

    @Override
    public Optional<ShipperProfile> findById(Long id) {
        return shipperProfileRepository.findById(id);
    }

    @Override
    public ShipperProfile save(ShipperProfile shipperProfile) {
        return shipperProfileRepository.save(shipperProfile);
    }

    @Override
    public void deleteById(Long id) {
        shipperProfileRepository.deleteById(id);
    }
}
