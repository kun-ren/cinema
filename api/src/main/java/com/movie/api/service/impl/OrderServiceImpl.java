package com.movie.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.movie.api.constant.OrderStatus;
import com.movie.api.mapper.FilmMapper;
import com.movie.api.mapper.OrderMapper;
import com.movie.api.mapper.UserMapper;
import com.movie.api.model.entity.Arrangement;
import com.movie.api.model.entity.Cart;
import com.movie.api.model.entity.Film;
import com.movie.api.model.entity.Order;
import com.movie.api.model.vo.OrderVO;
import com.movie.api.service.ArrangementService;
import com.movie.api.service.OrderService;
import com.movie.api.utils.DataTimeUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@CacheConfig(cacheNames = "order")
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private ArrangementService arrangementService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private FilmMapper filmMapper;

    @Override
    public Order create(Cart cart) throws Exception {
        List<Integer> seats = arrangementService.getSeatsHaveSelected(cart.getAid());
        // Accept comma-separated seats and legacy non-numeric separators.
        String[] split = cart.getSeats().split("\\D+");
        for (String s : split) {
            if (seats.contains(Integer.parseInt(s))) throw new Exception("These seats have already been booked by another customer. Please select different seats");
        }
        Order order = new Order();
        //Generate an order ID
        order.setId(UUID.randomUUID().toString());
        //Set the user ID
        order.setUid(cart.getUid());
        //Set the customer phone number
        order.setPhone(cart.getPhone());
        //Set the screening ID
        order.setAid(cart.getAid());
        //Set seat and status details
        order.setStatus(cart.getStatus());
        order.setSeats(cart.getSeats());
        if (cart.getStatus() == 2) order.setPayAt(DataTimeUtil.getNowTimeString());
        order.setPrice(cart.getPrice());
        order.setCreateAt(DataTimeUtil.getNowTimeString());
        orderMapper.insert(order);

        //Increase popularity by the number of booked seats
        Film film = filmMapper.selectById(arrangementService.findById(cart.getAid()).getFid());
        film.setHot(film.getHot() + split.length);
        filmMapper.updateById(film);
        return order;
    }

    @Override
    public Order pay(String id) throws Exception {
        Order order = orderMapper.selectById(id);
        if (order == null) throw new Exception("Order ID does not exist");

        if (DataTimeUtil.parseTimeStamp(order.getCreateAt()) + OrderStatus.EXPIRATION_TIME
                < System.currentTimeMillis()) {
            order.setStatus(OrderStatus.PAYMENT_FAILED);
            orderMapper.updateById(order);
            throw new Exception("Order payment has timed out");
        }

        order.setStatus(OrderStatus.PAYMENT_SUCCESSFUL);
        order.setPayAt(DataTimeUtil.getNowTimeString());
        orderMapper.updateById(order);
        return order;
    }

    @Override
    public void update(Order order) {
        orderMapper.updateById(order);
    }

    @Override
    public List<OrderVO> findAll() {
        return findByWrapper(new QueryWrapper<>());
    }

    @Override
    public List<OrderVO> findByUser(String uid) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.in("uid", uid);
        return findByWrapper(wrapper);
    }

    private List<OrderVO> findByWrapper(QueryWrapper<Order> wrapper) {
        List<Order> orders = orderMapper.selectList(wrapper);
        List<OrderVO> result = new ArrayList<>();
        for (Order o : orders) {
            OrderVO orderVO = new OrderVO();
            orderVO.setOrder(o);
            orderVO.setUser(userMapper.selectById(o.getUid()));
            Arrangement arrangement = arrangementService.findById(o.getAid());
            orderVO.setArrangement(arrangement);
            orderVO.setFilm(filmMapper.selectById(arrangement.getFid()));
            result.add(orderVO);
        }
        return result;
    }

}
