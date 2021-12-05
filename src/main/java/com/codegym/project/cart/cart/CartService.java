package com.codegym.project.cart.cart;

import com.codegym.project.users.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService implements ICartService{
    @Autowired
    private ICartRepository cartRepository;

    @Override
    public Page<Cart> findAll(Pageable pageable) {
        return cartRepository.findAll(pageable);
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void deleteById(Long id) {
        cartRepository.deleteById(id);
    }

    @Override
    public Cart findByMerchant_IdAndUser_Id(Long merchantId, Long userId) {
        return cartRepository.findByMerchant_IdAndUser_Id(merchantId, userId);
    }

    @Override
    public Cart findByMerchantAndUser(User merchant, User user) {
        return cartRepository.findByMerchantAndUser(merchant, user);
    }
}
