package com.movie.api.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Film reviews
 */
@Data
@TableName("t_film_evaluate")
@NoArgsConstructor
public class FilmEvaluate implements Serializable {

    private String id;

    //Film ID
    private String fid;

    //User ID
    private String uid;

    //Stars
    private Integer star;

    //Review
    private String comment;

    private String createAt;

}
