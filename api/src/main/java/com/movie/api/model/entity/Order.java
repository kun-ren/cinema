package com.movie.api.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Order
 */
@Data
@TableName("t_order")
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

    private String id;

    //User ID
    private String uid;

    //Ticket collection phone
    private String phone;

    //Screening ID
    private String aid;

    //Seats
    private String seats;

    //Amount
    private double price;

    //Status
    private Integer status;

    //Created at
    private String createAt;

    //Updated at
    private String payAt;

}
