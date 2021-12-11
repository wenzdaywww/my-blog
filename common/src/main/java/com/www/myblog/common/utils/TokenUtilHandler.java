package com.www.myblog.common.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>@Description token生成解析工具 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/16 20:43 </p>
 */
public class TokenUtilHandler {
    private static final Logger LOG = LoggerFactory.getLogger(TokenUtilHandler.class);
    /** token键值 */
    public static final String TOKEN = "token";
    /** token-type键值 */
    public static final String TOKEN_TYPE = "token-type";
    /** 超时键值 */
    public static final String EXPIRE_TIME = "expire-time";
    /** 用户名键值 */
    public static final String USERID = "userId";
    /** 请求头名称 */
    public static final String AUTHORIZATION = "Authorization";
    /** 过期时间 */
    private Long EXPIRATION_TIME ;
    /** 私钥 */
    private String SECRET ;
    /** token前缀 */
    private final String TOKEN_PREFIX = "Bearer";

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * <p>@Description 构造方法 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/16 20:49 </p>
     * @param expirationTime 过期时间
     * @param secret 密钥
     */
    public TokenUtilHandler(Long expirationTime,String secret){
        EXPIRATION_TIME = expirationTime;
        SECRET = secret;
    }
    /**
     * <p>@Description 生成token </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/16 20:52 </p>
     * @param claims
     * @return java.util.Map<java.lang.String, java.lang.String>
     */
    public Map<String, String> generateToken(Map<String, Object> claims) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.SECOND, EXPIRATION_TIME.intValue());
        Date d = c.getTime();
        String jwt = Jwts.builder()
                .setClaims(claims)
                .setExpiration(d)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        Map<String, String> map = new HashMap<String, String>();
        map.put(TOKEN,jwt);
        map.put(TOKEN_TYPE, TOKEN_PREFIX);
        map.put(EXPIRE_TIME,format.format(d));
        return map;
    }
    /**
     * <p>@Description 解析token </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/16 20:53 </p>
     * @param request
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    public Map<String, Object> validateTokenAndGetClaims(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        if (token == null) {
            return null;
        }
        Map<String, Object> body;
        try {
            body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX + " ",""))
                    .getBody();
        }catch (ExpiredJwtException e){
            LOG.error("-----> token过期");
            body = e.getClaims();
        }
        body.put(AUTHORIZATION,token.replace(TOKEN_PREFIX + " ",""));
        return body;
    }
}
