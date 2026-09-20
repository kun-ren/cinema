package com.movie.api.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Film
 */
@Data
@TableName("t_film")
@NoArgsConstructor
@AllArgsConstructor
public class Film implements Serializable {

    private String id;

    private String name;

    //Release date
    private String releaseTime;

    //Type
    private String type;

    //Status
    private boolean status;

    //Region, such as China, USA, or South Korea
    private String region;

    //Synopsis
    private String introduction;

    //Popularity
    private Integer hot;

    //Cover image
    private String cover;

    //Film duration in minutes
    private Integer duration;

}
