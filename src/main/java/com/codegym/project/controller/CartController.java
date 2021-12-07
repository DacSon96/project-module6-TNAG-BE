package com.codegym.project.controller;

import com.codegym.project.cart.cart.Cart;
import com.codegym.project.cart.cart.ICartService;
import com.codegym.project.cart.cartDetail.CartDetail;
import com.codegym.project.cart.cartDetail.ICartDetailService;
import com.codegym.project.dish.Dish;
import com.codegym.project.dish.IDishService;
import com.codegym.project.role.IRoleService;
import com.codegym.project.role.Role;
import com.codegym.project.role.RoleConst;
import com.codegym.project.users.users.IUserService;
import com.codegym.project.users.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private ICartService cartService;

    @Autowired
    private ICartDetailService iCartDetailService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IDishService dishService;

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/{merchantId}")
    public ResponseEntity<Cart> getCartByMerchant(Authentication authentication,
                                                  @PathVariable Long merchantId) {
        User user = userService.getUserFromAuthentication(authentication);
        Role merchantRole = roleService.findByName(RoleConst.MERCHANT);
        User merchant = userService.findByRolesContainingAndId(merchantRole, merchantId);
        if (merchant == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Cart cart = cartService.findByMerchantAndUser(merchant, user);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
    }

    @Secured("ROLE_USER")
    @PostMapping("/{dishId}/{direction}")
    public ResponseEntity<CartDetail> addToCart(Authentication authentication,
                                                @PathVariable("dishId") Long id,
                                                @PathVariable("direction") String direction) {
        Optional<Dish> optionalDish = dishService.findById(id);
        if (!optionalDish.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Dish dish = optionalDish.get();
        User currentUser = userService.getUserFromAuthentication(authentication);
        User merchant = dish.getMerchant();
        Cart cart = cartService.findByMerchantAndUser(merchant, currentUser);
        if (cart == null) {
            Set<CartDetail> cartDetailSet = new HashSet<>();
            cart = new Cart(merchant, currentUser, cartDetailSet);
        }
        Set<CartDetail> cartDetails = cart.getCartDetails();
        boolean isDishInCart = false;
        CartDetail cartDetail = new CartDetail();
        for (CartDetail c : cartDetails) {
            if (c.getDish().equals(dish)) {
                cartDetail = c;
                isDishInCart = true;
                break;
            }
        }
        switch (direction) {
            case "+":
                if (!isDishInCart) {
                    cartDetail = new CartDetail(dish, 1, dish.getPrice());
                    cartDetail = iCartDetailService.save(cartDetail);
                    cartDetails.add(cartDetail);
                    cart.setCartDetails(cartDetails);
                    cartService.save(cart);
                } else {
                    cartDetail.setQuantity(cartDetail.getQuantity() + 1);
                    iCartDetailService.save(cartDetail);
                }
                return new ResponseEntity<>(cartDetail, HttpStatus.OK);
            case "-":
                if (!isDishInCart) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                } else {
                    if (cartDetail.getQuantity() == 1) {
                        iCartDetailService.deleteById(cartDetail.getId());
                        cart.getCartDetails().remove(cartDetail);
                        cartService.save(cart);
                        return new ResponseEntity<>(HttpStatus.OK);
                    } else {
                        cartDetail.setQuantity(cartDetail.getQuantity() - 1);
                        iCartDetailService.save(cartDetail);
                        return new ResponseEntity<>(cartDetail, HttpStatus.OK);
                    }
                }
            default:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
