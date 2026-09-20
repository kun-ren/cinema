package com.movie.api.controller;

import com.movie.api.model.entity.OrderException;
import com.movie.api.service.OrderExceptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "Order issue API")
@RequestMapping("/api/oe")
public class OrderExceptionController {

    @Resource
    private OrderExceptionService orderExceptionService;

    @PostMapping("")
    @ApiOperation("Report order issue")
    public OrderException create(@RequestBody OrderException orderException) {
        return orderExceptionService.create(orderException);
    }

    @GetMapping("")
    @ApiOperation("List all order issues")
    public List<OrderException> findAll() {
        return orderExceptionService.findAll();
    }

    @PutMapping("")
    @ApiOperation("Resolve order issues")
    public void handle(@RequestBody OrderException orderException) {
        orderExceptionService.handleException(orderException);
    }

}
