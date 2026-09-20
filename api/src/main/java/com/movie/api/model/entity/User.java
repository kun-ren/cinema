package com.movie.api.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * User
 */
@Data
@TableName("t_user")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private String id;

    private String username;

    private String password;

    //Nickname
    private String nickname;

    //Email
    private String email;

    //Birthday
    private String birthday;

    //Gender
    private String gender;

    //Biography
    private String info;

    //User avatar URL
    private String avatar;

    private String createAt;

    private String updateAt;

}
