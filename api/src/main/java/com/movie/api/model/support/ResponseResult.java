package com.movie.api.model.support;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@ApiModel(value = "API response")
@AllArgsConstructor
public class ResponseResult<T> {

    private Integer code;

    private boolean success;

    private String msg;

    private T data;

    public ResponseResult() {
        this.code = 200;
        this.success = true;
        this.msg = null;
        this.data = null;
    }

    //Return data without a message
    public ResponseResult(T data) {
        this.code = 200;
        this.success = true;
        this.msg = null;
        this.data = data;
    }

    //Return data with a message
    public ResponseResult(String msg, T data) {
        this.code = 200;
        this.success = true;
        this.msg = msg;
        this.data = data;
    }

    //Default error response
    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.success = false;
        this.msg = msg;
        this.data = null;
    }

}
