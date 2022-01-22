package com.www.uaa.controller;

import com.www.common.config.oauth2.token.JwtTokenConverter;
import com.www.common.config.oauth2.util.RedisTokenHandler;
import com.www.common.pojo.constant.CharConstant;
import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.common.pojo.dto.token.TokenDTO;
import com.www.common.pojo.dto.token.TokenInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@Slf4j
@RestController
public class OauthController {
    /** 保存到cookie的access_token的key **/
    public static final String COOKIES_ACCESS_TOKEN = "access_token";
    /** 保存到cookie的refresh_token的key **/
    public static final String COOKIES_REFRESH_TOKEN = "refresh_token";
    @Autowired
    private TokenEndpoint tokenEndpoint;
    @Autowired
    private JwtTokenConverter jwtTokenConverter;

    /**
     * <p>@Description 重写oauth/token的get请求 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/19 23:44 </p>
     * @param principal
     * @param parameters
     * @return com.www.authorise.data.dto.ResponseDTO<java.util.Map>
     */
    @GetMapping("oauth/token")
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
    @PostMapping("oauth/token")
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
        //解析token信息
        TokenInfoDTO tokenInfoDTO = jwtTokenConverter.decodeToken(tokenDTO.getAccessToken());
        tokenDTO.setUserId(tokenInfoDTO != null ? tokenInfoDTO.getUser_name() : null);
        //将token保存到cookie中
        Cookie tokenCookie = new Cookie(COOKIES_ACCESS_TOKEN,tokenDTO.getAccessToken());
        tokenCookie.setMaxAge(tokenDTO.getExpiresSeconds());
        tokenCookie.setPath(CharConstant.LEFT_SLASH);
        response.addCookie(tokenCookie);
        // 有刷新令牌则也保存到cookie中
        if(tokenDTO.getRefreshToken() != null){
            Cookie refreshCookie = new Cookie(COOKIES_REFRESH_TOKEN,tokenDTO.getRefreshToken());
            refreshCookie.setPath(CharConstant.LEFT_SLASH);
            response.addCookie(refreshCookie);
        }
        responseDTO.setResponse(ResponseDTO.RespEnum.SUCCESS,tokenDTO);
        //保存用户登录的token到redis中
        RedisTokenHandler.setUserIdToken(tokenInfoDTO,tokenDTO.getAccessToken(),tokenDTO.getExpiresSeconds());
        return responseDTO;
    }
}
