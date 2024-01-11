package org.example.entity.base;

import lombok.Data;

import java.util.Date;

/**
 * @author lihui
 * @since 2022/10/29
 */
@Data
public class Token<T> {
    private Object id;
    // 签名时间
    private Date signTime;
    // token数据
    private T data;

    public Token() {
    }

    public Token(Object id, Date signTime, T data) {
        this.id = id;
        this.signTime = signTime;
        this.data = data;
    }
}