package com.movie.api.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Cinema support worker
 */
@Data
@TableName("t_worker")
@NoArgsConstructor
@AllArgsConstructor
public class Worker implements Serializable {

    private String id;

    private String username;

    private String password;

    private String nickname;

    private String gender;

    private String phone;

    private String avatar;

    //Employed
    private boolean entry;

    //Department
    private String department;

    private String createAt;

    private String updateAt;

}
