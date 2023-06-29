package org.example.model;

import lombok.Data;
import org.example.error.CommonServerResult;
import org.example.global.ResultCode;

/**
 * @author lihui
 * @since 2022/10/26
 */
@Data
public class CommonResult {
    private Integer code;
    private String message;
    private Object data;

    private CommonResult() {
    }

    private CommonResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static CommonResult success() {
        return new CommonResult(ResultCode.SUCCESS.getCode(), CommonServerResult.SUCCESS, null);
    }

    public static CommonResult success(Object data) {
        return new CommonResult(ResultCode.SUCCESS.getCode(), CommonServerResult.SUCCESS, data);
    }

    public static CommonResult success(String message) {
        return new CommonResult(ResultCode.SUCCESS.getCode(), message, null);
    }

    public static CommonResult success(String message, Object data) {
        return new CommonResult(ResultCode.SUCCESS.getCode(), message, data);
    }

    public static CommonResult error() {
        return new CommonResult(ResultCode.ERROR.getCode(), CommonServerResult.ERROR, null);
    }

    public static CommonResult error(Object data) {
        return new CommonResult(ResultCode.ERROR.getCode(), CommonServerResult.ERROR, data);
    }

    public static CommonResult error(String message) {
        return new CommonResult(ResultCode.ERROR.getCode(), message, null);
    }

    public static CommonResult error(String message, Object data) {
        return new CommonResult(ResultCode.ERROR.getCode(), message, data);
    }

    public static CommonResult unauthorized() {
        return new CommonResult(ResultCode.UNAUTHORIZED.getCode(), CommonServerResult.UNAUTHORIZED, null);
    }

    public static CommonResult forbidden() {
        return new CommonResult(ResultCode.FORBIDDEN.getCode(), CommonServerResult.FORBIDDEN, null);
    }
}