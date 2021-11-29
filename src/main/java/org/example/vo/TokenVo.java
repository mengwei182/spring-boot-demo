package org.example.vo;

import lombok.Data;

@Data
public class TokenVo {
    private String token;
    private String tokenHead;
    private String requestTokenHead;

    public TokenVo(String token, String tokenHead) {
        this.token = token;
        this.tokenHead = tokenHead;
    }
}