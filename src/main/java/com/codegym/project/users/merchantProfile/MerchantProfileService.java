package com.codegym.project.users.merchantProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MerchantProfileService implements IMerchantProfileService{

    @Autowired
    private IMerchantProfileRepository merchantProfileRepository;


    @Override
    public Page<MerchantProfile> findAll(Pageable pageable) {
        return merchantProfileRepository.findAll(pageable);
    }

    @Override
    public Optional<MerchantProfile> findById(Long id) {
        return merchantProfileRepository.findById(id);
    }

    @Override
    public MerchantProfile save(MerchantProfile merchantProfile) {
        return merchantProfileRepository.save(merchantProfile);
    }

    @Override
    public void deleteById(Long id) {
        merchantProfileRepository.deleteById(id);
    }

}
