package org.example.error.exception;

import org.example.error.CommonServerResult;
import org.example.model.CommonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理器
 *
 * @author lihui
 * @since 2022/10/26
 */
@ControllerAdvice
public class CommonControllerAdvice {
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public CommonResult<?> commonException(RuntimeException exception, HttpServletResponse response) {
        return CommonResult.error(CommonServerResult.ERROR, exception.getMessage());
    }
}