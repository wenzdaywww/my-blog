package com.www.myblog.base.controller.user;

import com.www.common.config.code.dto.CodeDTO;
import com.www.common.config.oauth2.constant.AuthorityContant;
import com.www.common.config.oauth2.token.JwtTokenConverter;
import com.www.common.data.enums.ResponseEnum;
import com.www.common.data.response.Response;
import com.www.myblog.base.data.dto.SysMenuDTO;
import com.www.myblog.base.data.dto.SysUserDTO;
import com.www.myblog.base.service.redis.IRedisService;
import com.www.myblog.base.service.user.IUserInfoService;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>@Description 用户信息controller </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/30 20:54 </p>
 */
@RestController
@RequestMapping("user")
@PreAuthorize(AuthorityContant.ALL)
public class UserController {
    @Autowired
    private JwtTokenConverter jwtTokenConverter;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IRedisService redisService;

    /**
     * <p>@Description 查询多个数据字典数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 16:39 </p>
     * @param list 字典类型集合
     * @return Response<List<CodeDTO>>
     */
    @PostMapping("codes")
    public Response<Map<String,List<CodeDTO>>> findCodeDataList(@RequestParam List<String> list){
        Response<Map<String,List<CodeDTO>>>response = new Response<>();
        if(list == null || list.size() <= 0){
            response.setResponse(ResponseEnum.FAIL,"查询数据字典数据失败，信息不全",null);
            return response;
        }
        Map<String, Map<String, CodeDTO>> codeMap = redisService.getCodeData();
        Map<String,List<CodeDTO>> typeMap = new HashMap<>();
        for (String codeType : list){
            Map<String, CodeDTO> valueMap = codeMap.get(codeType);
            if(MapUtils.isNotEmpty(valueMap)){
                List<CodeDTO> collect = valueMap.values().stream().collect(Collectors.toList());
                typeMap.put(codeType,collect);
            }
        }
        response.setResponse(typeMap);
        return response;
    }
    /**
     * <p>@Description 查询单个数据字典数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 16:39 </p>
     * @param codeType 字典类型
     * @return Response<List<CodeDTO>>
     */
    @GetMapping("code/{type}")
    public Response<Map<String,List<CodeDTO>>> findCodeData(@PathVariable("type") String codeType){
        Response<Map<String,List<CodeDTO>>>response = new Response<>();
        if(StringUtils.isBlank(codeType)){
            response.setResponse(ResponseEnum.FAIL,"查询单个数据字典数据失败，信息不全",null);
            return response;
        }
        List<String> list = new ArrayList<>();
        list.add(codeType);
        return this.findCodeDataList(list);
    }
    /**
     * <p>@Description 更新当前登录的用户密码 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:58 </p>
     * @param user 用户信息
     * @return Response<String>
     */
    @PostMapping("pwd")
    public Response<String> updateUserPwd(SysUserDTO user){
        if(user != null){
            user.setUserId(jwtTokenConverter.getUserId());
        }
        return userInfoService.updateUserPwd(user);
    }
    /**
     * <p>@Description 查询当前登录的用户vue的router权限 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 00:22 </p>
     * @return Response<List<SysMenuDTO>>
     */
    @GetMapping("router")
    public Response<List<SysMenuDTO>> findUserRouter(){
        return userInfoService.findUserRouter(jwtTokenConverter.getUserId());
    }
    /**
     * <p>@Description 查询当前登录的用户菜单列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 00:22 </p>
     * @return Response<List<SysMenuDTO>>
     */
    @GetMapping("menu")
    public Response<List<SysMenuDTO>> findUserMenu(){
        return userInfoService.findUserMenu(jwtTokenConverter.getUserId());
    }
    /**
     * <p>@Description 更新当前登录的用户头像 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 20:02 </p>
     * @param photo 头像文件
     * @return Response<String>
     */
    @PostMapping("photo")
    public Response<String> uploadPhoto(MultipartFile photo){
        return userInfoService.uploadPhoto(photo,jwtTokenConverter.getUserId());
    }
    /**
     * <p>@Description 更新当前登录的用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:58 </p>
     * @param user 用户信息
     * @return Response<String>
     */
    @PostMapping("edit")
    public Response<String> updateUserInfo(SysUserDTO user){
        if(user != null){
            user.setUserId(jwtTokenConverter.getUserId());
        }
        return userInfoService.updateUserInfo(user);
    }
    /**
     * <p>@Description 查询当前登录的用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:43 </p>
     * @return Response<SysUserDTO>
     */
    @GetMapping("info")
    public Response<SysUserDTO> findUser(){
        return userInfoService.findUser(jwtTokenConverter.getUserId());
    }
}
