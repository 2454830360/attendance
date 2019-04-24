package com.system.attendance.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.util.Date;

/**
 * JSON Web Tokens
 * 生成token
 */
public class JWTUtil {

    private static final Logger LOG = LoggerFactory.getLogger(JWTUtil.class);


    final static String base64EncodedSecretKey = "hello6341532";//私钥

    final static long TOKEN_EXP = 1000 * 60 * 60 * 24;//一天

    public static String getToken(){
        return Jwts.builder()
//                .setSubject("test1001")
//                .claim("roles","user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+TOKEN_EXP))//过期时间
                .signWith(SignatureAlgorithm.HS256,base64EncodedSecretKey)
                .compact();
    }

    //检查token
    public static Claims checkToken(String token) throws ServletException {
        try {
            final Claims claims =
                    Jwts.parser()
                            .setSigningKey(base64EncodedSecretKey)
                            .parseClaimsJws(token)
                            .getBody();
            return claims;
        }catch (ExpiredJwtException e1){
            LOG.info("登录信息过期，请重新登录");
        }catch (Exception e){
            LOG.info("该用户未登录，请重新登录");
        }
        return null;
    }



    //解token
    public static void parseToken(String token){

    }

}
