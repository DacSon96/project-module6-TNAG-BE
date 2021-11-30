package com.codegym.project.orders.couponType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICouponTypeRepository extends JpaRepository<CouponType, Long> {
}
