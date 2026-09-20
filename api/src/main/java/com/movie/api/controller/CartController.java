package com.movie.api.controller;

import com.movie.api.model.entity.Cart;
import com.movie.api.model.vo.CartVO;
import com.movie.api.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "Shopping cart API")
@RequestMapping("/api/cart")
public class CartController {

    @Resource
    private CartService cartService;

    @PostMapping
    @ApiOperation("Add to cart")
    public void save(@RequestBody Cart cart) throws Exception {
        cartService.save(cart);
    }

    @GetMapping("")
    @ApiOperation("List cart items by user ID")
    public List<CartVO> list(@RequestParam String uid) {
        return cartService.findAllByUserId(uid);
    }

    @DeleteMapping("")
    @ApiOperation("Delete cart item")
    public void delete(String id) {
        cartService.deleteById(id);
    }

}
