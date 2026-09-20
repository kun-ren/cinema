package com.movie.api.controller;

import com.movie.api.model.entity.Cart;
import com.movie.api.model.entity.Order;
import com.movie.api.model.vo.OrderVO;
import com.movie.api.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "Order API")
@RequestMapping("/api/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("")
    @ApiOperation(value = "Create order")
    public void save(@RequestBody Cart cart) throws Exception {
        orderService.create(cart);
    }

    @GetMapping("")
    @ApiOperation(value = "List all orders")
    public List<OrderVO> findAll() {
        return orderService.findAll();
    }

    @PutMapping("")
    @ApiOperation(value = "Update order")
    public void update(@RequestBody Order order) {
        orderService.update(order);
    }

    @GetMapping("/user/{id}")
    @ApiOperation(value = "List user orders")
    public List<OrderVO> findByUser(@PathVariable String id) {
        return orderService.findByUser(id);
    }

    @GetMapping("/pay")
    @ApiOperation(value = "Pay for order")
    public Order save(String id) throws Exception {
        return orderService.pay(id);
    }

}
