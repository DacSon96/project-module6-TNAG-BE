package com.codegym.project.users.merchantProfile.repository;

import com.codegym.project.users.merchantProfile.MerchantProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMerchantProfileRepository extends JpaRepository<MerchantProfile,Long> {
    Page<MerchantProfile> findAll(Pageable pageable);
}
