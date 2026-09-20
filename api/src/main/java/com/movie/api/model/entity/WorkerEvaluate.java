package com.movie.api.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Support reviews
 */
@Data
@TableName("t_worker_evaluate")
@NoArgsConstructor
public class WorkerEvaluate implements Serializable {

    private String id;

    //Support worker ID
    private String wid;

    //Reviewer user ID
    private String uid;

    //Review text
    private String content;

    //Satisfaction level
    private String type;

    private String createAt;

}
