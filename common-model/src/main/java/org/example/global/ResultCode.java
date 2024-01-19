package org.example.global;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(200, "SUCCESS"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    FORBIDDEN(403, "FORBIDDEN"),
    ERROR(500, "ERROR");
    private final Integer code;
    private final String name;

    ResultCode(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}