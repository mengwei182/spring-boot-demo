package org.example.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.example.common.model.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@PropertySource("classpath:common.properties")
public class JwtTokenService {
    private static final String CLAIM_KEY_USERID = "userid";
    private static final String CLAIM_KEY_USERNAME = "username";
    private static final String CLAIM_KEY_CREATE_TIME = "create_time";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.refreshExpiration}")
    private int refreshExpiration;

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).setExpiration(new Date(System.currentTimeMillis() + expiration * 1000)).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    private Claims getClaims(String token) {
        Claims claims = null;
        try {
            token = token.substring(tokenHead.length());
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.error("get claims error:{}", e.getMessage());
        }
        return claims;
    }

    public String getUserId(String token) {
        String userId;
        try {
            Claims claims = getClaims(token);
            userId = (String) claims.get(CLAIM_KEY_USERID);
        } catch (Exception e) {
            userId = null;
        }
        return userId;
    }

    public String getUsername(String token) {
        String username;
        try {
            Claims claims = getClaims(token);
            username = (String) claims.get(CLAIM_KEY_USERNAME);
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public boolean validateToken(String token, CustomUserDetails customUserDetails) {
        String username = getUsername(token);
        return username.equals(customUserDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDate(token);
        return expiredDate.before(new Date());
    }

    private Date getExpiredDate(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration();
    }

    public String generateToken(CustomUserDetails customUserDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERID, customUserDetails.getUser().getId());
        claims.put(CLAIM_KEY_USERNAME, customUserDetails.getUser().getUsername());
        claims.put(CLAIM_KEY_CREATE_TIME, new Date());
        return generateToken(claims);
    }

    public String refreshHeadToken(String oldToken) {
        if (!StringUtils.hasLength(oldToken)) {
            return null;
        }
        String token = oldToken.substring(tokenHead.length());
        if (!StringUtils.hasLength(token)) {
            return null;
        }
        Claims claims = getClaims(token);
        if (claims == null) {
            return null;
        }
        if (isTokenExpired(token)) {
            return null;
        }
        if (tokenRefreshJustBefore(token, refreshExpiration)) {
            return token;
        } else {
            claims.put(CLAIM_KEY_CREATE_TIME, new Date());
            return generateToken(claims);
        }
    }

    private boolean tokenRefreshJustBefore(String token, int time) {
        Claims claims = getClaims(token);
        Date created = claims.get(CLAIM_KEY_CREATE_TIME, Date.class);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(created);
        calendar.set(Calendar.SECOND, time);
        Date refreshDate = new Date();
        return refreshDate.after(created) && refreshDate.before(calendar.getTime());
    }
}