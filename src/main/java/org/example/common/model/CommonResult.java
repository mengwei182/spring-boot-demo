package org.example.common.model;

import lombok.Data;
import org.example.common.global.I18nMessage;
import org.example.common.util.I18nUtils;

@Data
public class CommonResult {
    private int code;
    private String message;
    private Object data;

    private CommonResult() {
    }

    private CommonResult(int code, String message, Object data) {
        this.code = code;
        this.message = I18nUtils.getMessage(message);
        this.data = data;
    }

    public static CommonResult success() {
        return new CommonResult(ResultCode.SUCCESS, I18nMessage.SUCCESS, null);
    }

    public static CommonResult success(Object data) {
        return new CommonResult(ResultCode.SUCCESS, I18nMessage.SUCCESS, data);
    }

    public static CommonResult success(String message) {
        return new CommonResult(ResultCode.SUCCESS, message, null);
    }

    public static CommonResult success(String message, Object data) {
        return new CommonResult(ResultCode.SUCCESS, message, data);
    }

    public static CommonResult failed() {
        return new CommonResult(ResultCode.FAILED, I18nMessage.FAIL, null);
    }

    public static CommonResult failed(Object data) {
        return new CommonResult(ResultCode.FAILED, I18nMessage.FAIL, data);
    }

    public static CommonResult failed(String message) {
        return new CommonResult(ResultCode.FAILED, message, null);
    }

    public static CommonResult failed(String message, Object data) {
        return new CommonResult(ResultCode.FAILED, message, data);
    }

    public static CommonResult unauthorized(Object data) {
        return new CommonResult(ResultCode.UNAUTHORIZED, I18nMessage.NO_ACCESS, data);
    }

    public static CommonResult forbidden(Object data) {
        return new CommonResult(ResultCode.FORBIDDEN, I18nMessage.PERMISSION_VERIFICATION_FAILED, data);
    }
}