package com.movie.api.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Messages
 */
@Data
@TableName("t_leaving_message")
@NoArgsConstructor
public class LeavingMessage implements Serializable {

    private String id;

    //User ID
    private String uid;

    private String reply;

    //Content
    private String content;

    private String createAt;

}
