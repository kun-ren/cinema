package com.movie.api.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Shopping cart
 */
@Data
@TableName("t_cart")
@NoArgsConstructor
public class Cart implements Serializable {

    private String id;

    //User ID
    private String uid;

    //Screening ID
    private String aid;

    //Seat number
    private String seats;

    private String phone;

    private Integer status;

    //Amount
    private double price;

}
