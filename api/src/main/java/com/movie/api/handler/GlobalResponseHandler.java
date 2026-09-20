package com.movie.api.handler;

import com.movie.api.annotation.DisableBaseResponse;
import com.movie.api.model.support.ResponseResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Intercept controller return values
 * Wrap the result in ResponseResult<T>
 */
@ControllerAdvice(basePackages = "com.movie.api")
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class c) {
        //Skip wrapping methods annotated with DisableBaseResponse
        return !methodParameter.hasMethodAnnotation(DisableBaseResponse.class);
    }

    @Override
    public ResponseResult<Object> beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o == null) {
            return new ResponseResult<>();
        }
        return new ResponseResult<>(o);
    }

}
