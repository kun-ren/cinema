package com.movie.api.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * Homepage posters
 */
@Data
@TableName("t_poster")
public class Poster implements Serializable {

    private String id;

    private String title;

    private String url;

    //Published or unpublished
    private boolean status;

    private String createAt;

}
