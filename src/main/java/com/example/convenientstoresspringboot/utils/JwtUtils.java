package com.example.convenientstoresspringboot.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {
    private static final String SECRET_KEY = "secret";
    private static final long EXPIRATION_TIME = 12 * 60 * 60 * 1000;

    /**
     * 生成Token
     * @param claims
     * @return
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
    }

    public static Claims parseToken(String token) throws Exception {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            // 如果 token 已过期，抛出异常
            if (claims.getExpiration().before(new Date())) {
                throw new Exception("Token 已过期");
            }
            return claims;
        } catch (Exception e) {
            throw new Exception("解析Token失败: " + e.getMessage());
        }
    }
}
