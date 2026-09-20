package com.movie.api.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Order issue
 */
@Data
@TableName("t_order_exception")
@NoArgsConstructor
@AllArgsConstructor
public class OrderException implements Serializable {

    private String id;

    //Order ID
    private String oid;

    //Issue reason
    private String reason;

    //Reviewer or reporter
    private String reviewer;

    //true Resolved
    //false Pending
    private boolean status;

    private String result;

    private String createAt;

    private String endAt;

}
