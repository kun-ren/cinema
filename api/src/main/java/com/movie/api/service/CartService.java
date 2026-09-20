package com.movie.api.service;

import com.movie.api.model.entity.Cart;
import com.movie.api.model.vo.CartVO;

import java.util.List;

public interface CartService {

    void save(Cart cart) throws Exception;

    void deleteById(String id);

    void deleteAllByUserId(String uid);

    List<CartVO> findAllByUserId(String uid);

    //Delete selected cart items
    void deleteCarts(List<Cart> carts);

    //Check out selected cart items
    void settleCarts(List<Cart> carts) throws Exception;

}
