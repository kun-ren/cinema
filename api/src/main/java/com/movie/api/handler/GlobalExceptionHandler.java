package com.movie.api.handler;

import com.movie.api.annotation.DisableBaseResponse;
import com.movie.api.model.support.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Catch exceptions raised by controllers.
 * Handle controller exceptions with the methods below.
 * Return errors using the standard response envelope.
 */
@ResponseBody
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = Exception.class)
    @DisableBaseResponse
    public Object handleException(Exception e) {
        if (e.getClass().equals(AccessDeniedException.class)){
            return new ResponseResult<>(403, "You do not have permission to access this resource");
        }
        logger.error(e.getMessage());
        return new ResponseResult<>(400, e.getMessage());
    }

}
