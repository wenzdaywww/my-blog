package com.www.authorise.controller;

import com.www.common.pojo.ResponseDTO;
import com.www.common.pojo.TokenDTO;
import com.www.common.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;

/**
 * <p>@Description 重写oauth请求 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/19 23:36 </p>
 */
@RestController
@RequestMapping("oauth")
public class OauthController {
    /** redis的key前缀 **/
    private static String PREFIX = "oauth2_token";
    /** redis的key分隔符 **/
    private static String SEPARATOR = ":";
    /** oauth/token请求的username **/
    private static String USERNAME = "username";
    @Autowired
    private TokenEndpoint tokenEndpoint;

    /**
     * <p>@Description 重写oauth/token的get请求 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/19 23:44 </p>
     * @param principal
     * @param parameters
     * @return com.www.authorise.data.dto.ResponseDTO<java.util.Map>
     */
    @GetMapping("/token")
    public ResponseDTO<TokenDTO> tokenGet(HttpServletResponse response, Principal principal, @RequestParam Map<String, String> parameters) throws Exception {
        return tokenPost(response,principal,parameters);
    }
    /**
     * <p>@Description 重写oauth/token的post请求 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/19 23:44 </p>
     * @param principal
     * @param parameters
     * @return com.www.authorise.data.dto.ResponseDTO<java.util.Map>
     */
    @PostMapping("/token")
    public ResponseDTO<TokenDTO> tokenPost(HttpServletResponse response, Principal principal, @RequestParam Map<String, String> parameters) throws Exception  {
        ResponseDTO<TokenDTO> responseDTO = new ResponseDTO<>();
        ResponseEntity<OAuth2AccessToken> accessToken = tokenEndpoint.postAccessToken(principal, parameters);
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setAccessToken(accessToken.getBody().getValue());
        tokenDTO.setRefreshToken(accessToken.getBody().getRefreshToken() != null ?
                accessToken.getBody().getRefreshToken().getValue() : null);
        tokenDTO.setTokenType(accessToken.getBody().getTokenType());
        tokenDTO.setExpiresSeconds(accessToken.getBody().getExpiresIn());
        tokenDTO.setScope(accessToken.getBody().getScope());
        //将token保存到cookie中
        Cookie cookie = new Cookie("access_token",tokenDTO.getAccessToken());
        cookie.setMaxAge(tokenDTO.getExpiresSeconds());
        response.addCookie(cookie);
        responseDTO.setResponseCode(ResponseDTO.RespEnum.SUCCESS,tokenDTO);
        //将token保存到redis中
        String key = PREFIX + SEPARATOR + parameters.get(OAuth2Utils.CLIENT_ID) + SEPARATOR + parameters.get(OAuth2Utils.GRANT_TYPE);
        if(StringUtils.isNotBlank(parameters.get(OAuth2Utils.SCOPE))){
            key += SEPARATOR + parameters.get(OAuth2Utils.SCOPE);
        }
        if(StringUtils.isNotBlank(parameters.get(USERNAME))){
            key += SEPARATOR + parameters.get(USERNAME);
        }
        RedisUtils.set(key,tokenDTO.getAccessToken(),tokenDTO.getExpiresSeconds());
        return responseDTO;
    }
}
