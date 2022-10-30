package org.example.configuration;

import lombok.extern.slf4j.Slf4j;
import org.example.common.model.CommonResult;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author 李辉
 * @since 2022/10/26
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult methodArgumentNotValidHandler(MethodArgumentNotValidException exception) {
        log.error("methodArgumentNotValidHandler:", exception);
        BindingResult bindingResult = exception.getBindingResult();
        if (bindingResult != null) {
            List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
            String msg = fieldErrors.stream().findFirst().map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage())).orElse(null);
            return CommonResult.error(msg);
        }
        return CommonResult.error();
    }

    @ExceptionHandler(RuntimeException.class)
    public CommonResult handleRuntimeException(RuntimeException e) {
        log.error("handleRuntimeException:", e);
        return CommonResult.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public CommonResult handleException(Exception e) {
        log.error("handleException:", e);
        return CommonResult.error(e.getMessage());
    }
}