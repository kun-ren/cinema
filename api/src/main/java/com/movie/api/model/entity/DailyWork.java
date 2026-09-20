package com.movie.api.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * Daily work updates
 */
@Data
@TableName("t_daily_work")
public class DailyWork implements Serializable {

    private String id;

    //Important, normal, or urgent
    private int type;

    //Work description
    private String content;

    //Uploaded at
    private String createAt;

}
