package com.codegym.project.users.merchantProfile.repository;

import com.codegym.project.users.merchantProfile.MerchantProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMerchantProfileRepository extends JpaRepository<MerchantProfile,Long> {
}
