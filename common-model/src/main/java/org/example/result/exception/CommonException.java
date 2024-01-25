package org.example.result.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.example.result.CommonServerResult;

import java.io.Serializable;

/**
 * @author lihui
 * @since 2022/10/26
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class CommonException extends RuntimeException implements Serializable {
    private final String code;

    public CommonException(String message) {
        super(message);
        this.code = CommonServerResult.COMMON_EXCEPTION_CODE;
    }

    public CommonException(String code, String message) {
        super(message);
        this.code = code;
    }
}