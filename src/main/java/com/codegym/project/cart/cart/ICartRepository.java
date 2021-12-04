package com.codegym.project.cart.cart;

import com.codegym.project.users.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Long> {
    Cart findByMerchant_IdAndUser_Id(Long merchantId, Long userId);

    Cart findByMerchantAndUser(User merchant, User user);
}
