package com.movie.api.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * Activity registration
 */
@Data
@TableName("t_registration")
public class Registration implements Serializable {

    private String id;

    //Activity ID
    private String aid;

    //Registered user ID
    private String uid;

    private String createAt;

}
