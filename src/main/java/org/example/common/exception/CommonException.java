package org.example.common.exception;

import java.io.Serializable;

/**
 * @author 李辉
 * @since 2022/10/26
 */
public class CommonException extends RuntimeException implements Serializable {
    public CommonException(String message) {
        super(message);
    }
}