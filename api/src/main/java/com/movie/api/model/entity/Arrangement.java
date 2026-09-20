package com.movie.api.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Film screening, schedule, and seat capacity
 */
@Data
@TableName("t_arrangement")
@NoArgsConstructor
@AllArgsConstructor
public class Arrangement implements Serializable {

    private String id;

    //Film ID
    private String fid;

    //Film title
    private String name;

    //Number of available seats
    private Integer seatNumber;

    //Price
    private double price;

    //Screening format 2D 3D
    private String type;

    //Screening start time 2021-01-01
    private String date;

    //Screening start time 19:30:00
    private String startTime;

    //Box office
    private Integer boxOffice;

    //End time
    private String endTime;

    //Created by
    private String founder;

    //Created at
    private String createAt;

}
