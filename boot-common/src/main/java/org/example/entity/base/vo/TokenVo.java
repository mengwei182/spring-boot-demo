package org.example.entity.base.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author lihui
 * @since 2022/10/29
 */
@Data
public class TokenVo<T> {
    private Object id;
    // 签名时间
    private Date signTime;
    // token数据
    private T data;

    public TokenVo() {
    }

    public TokenVo(Object id, Date signTime, T data) {
        this.id = id;
        this.signTime = signTime;
        this.data = data;
    }
}