package com.codegym.project.users.merchantProfile.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class MerchantProfile implements IMerchantProfileService{
    @Override
    public Page<com.codegym.project.users.merchantProfile.MerchantProfile> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<com.codegym.project.users.merchantProfile.MerchantProfile> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public com.codegym.project.users.merchantProfile.MerchantProfile save(com.codegym.project.users.merchantProfile.MerchantProfile merchantProfile) {
        return null;
    }

    @Override
    public void delete(com.codegym.project.users.merchantProfile.MerchantProfile merchantProfile) {

    }
}
