package com.codegym.project.cart.cart;

import com.codegym.project.IGeneralService;
import com.codegym.project.users.users.User;

public interface ICartService extends IGeneralService<Cart> {
    Cart findByMerchant_IdAndUser_Id(Long merchantId, Long userId);

    Cart findByMerchantAndUser(User merchant, User user);

    Iterable<Cart> findAllByUser(User user);

}
