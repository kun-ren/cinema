package com.movie.api.annotation;

import java.lang.annotation.*;

/**
 * Skip the JSON response envelope applied by GlobalResponseHandler.
 * For example, image endpoints write binary data instead of JSON.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DisableBaseResponse {

}
