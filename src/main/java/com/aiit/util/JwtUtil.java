package com.aiit.util;

import com.aiit.entity.UserInfo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtUtil {
    private static final long EXPIRE_TIME = 15 * 24 * 60 * 60 * 1000;
    private static final String TOKEN_SECRET = "AIITbeidou&A6N206.";  //密钥

    /**
     * 签名生成
     *
     * @param user
     * @return
     */
    public static String sign(UserInfo user) {
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create().withIssuer("auth0")
                    .withClaim("username", user.getUserName())
                    .withExpiresAt(expiresAt)
                    //使用HMAC256加密
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 签名验证
     *
     * @param token
     * @return
     */
    public static String verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            log.info(jwt.getIssuer() + "用户名：" + jwt.getClaim("username").asString() + "认证通过");
            log.info("过期时间：" + jwt.getExpiresAt());
            return jwt.getClaim("username").asString();
        } catch (Exception e) {
            return null;
        }
    }
}
