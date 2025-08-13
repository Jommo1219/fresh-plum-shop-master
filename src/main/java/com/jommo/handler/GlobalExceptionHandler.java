package com.jommo.handler;


import com.jommo.common.Result;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 不会开发的小虾米
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e, BindingResult result) {
        e.printStackTrace();
        if (result.hasFieldErrors()) {
            String errorMsg = result.getFieldError().getDefaultMessage();
            return Result.error(errorMsg);
        }
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");
    }
}
