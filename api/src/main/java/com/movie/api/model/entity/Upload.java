package com.movie.api.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Uploaded images
 */
@Data
@TableName("t_upload")
@NoArgsConstructor
@AllArgsConstructor
public class Upload implements Serializable {

    private String id;

    private byte[] bytes;

    //File MD5 hash
    private String md5;

}
