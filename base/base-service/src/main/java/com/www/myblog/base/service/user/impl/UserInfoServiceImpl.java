package com.www.myblog.base.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.www.common.config.code.CodeDict;
import com.www.common.config.mvc.upload.IFileUpload;
import com.www.common.data.enums.DateFormatEnum;
import com.www.common.data.enums.ResponseEnum;
import com.www.common.data.response.Response;
import com.www.common.utils.DateUtils;
import com.www.myblog.base.data.dto.SysMenuDTO;
import com.www.myblog.base.data.dto.SysRoleDTO;
import com.www.myblog.base.data.dto.SysUserDTO;
import com.www.myblog.base.data.entity.SysRoleEntity;
import com.www.myblog.base.data.entity.SysUserEntity;
import com.www.myblog.base.data.entity.SysUserRoleEntity;
import com.www.myblog.base.data.enums.CodeTypeEnum;
import com.www.myblog.base.data.mapper.SysMenuMapper;
import com.www.myblog.base.data.mapper.SysRoleMapper;
import com.www.myblog.base.data.mapper.SysUserMapper;
import com.www.myblog.base.service.entity.ISysRoleService;
import com.www.myblog.base.service.entity.ISysUserRoleService;
import com.www.myblog.base.service.entity.ISysUserService;
import com.www.myblog.base.service.user.IUserInfoService;
import com.www.myblog.common.dto.UserInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>@Description 用户信息service实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/14 15:32 </p>
 */
@Service
@Slf4j
public class UserInfoServiceImpl implements IUserInfoService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private ISysUserRoleService sysUserRoleService;
    @Autowired
    private IFileUpload fileService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysRoleService sysRoleService;

    /**
     * <p>@Description 查询多个用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 15:43 </p>
     * @param userList 用户id集合
     * @return com.www.common.data.dto.response.Response<com.www.common.pojo.dto.feign.UserInfoDTO>
     */
    @Override
    public Response<List<UserInfoDTO>> findUserInfoList(List<String> userList) {
        Response<List<UserInfoDTO>> response = new Response<>();
        if(CollectionUtils.isEmpty(userList)){
            response.setResponse(ResponseEnum.FAIL,"查询多个用户信息失败，用户ID集合为空",null);
            return response;
        }
        //根据用户ID集合查询用户信息
        List<SysUserEntity> entityList = sysUserService.findUserEntityById(userList);
        if(CollectionUtils.isEmpty(entityList)){
            response.setResponse(ResponseEnum.SUCCESS,"查询多个用户信息失败，查询不到用户信息",null);
            return response;
        }
        List<UserInfoDTO> dtoList = new ArrayList<>();
        for (SysUserEntity entity : entityList){
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setSuId(entity.getSuId()).setUserId(entity.getUserId()).setUserName(entity.getUserName())
                    .setPhoneNum(entity.getPhoneNum()).setBirthday(entity.getBirthday()).setSex(entity.getSex())
                    .setPhoto(entity.getPhoto()).setEmail(entity.getEmail()).setBrief(entity.getBrief())
                    .setUpdateTime(entity.getUpdateTime()).setCreateTime(entity.getCreateTime());
            dtoList.add(userInfoDTO);
        }
        response.setResponse(ResponseEnum.SUCCESS,dtoList);
        return response;
    }

    /**
     * <p>@Description 校验用户是否存在 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 15:43 </p>
     * @param userList 用户id集合
     * @return com.www.common.data.dto.response.Response<Boolean>
     */
    @Override
    public Response<Boolean> validateUserExist(List<String> userList) {
        Response<Boolean> response = new Response<>();
        if(CollectionUtils.isEmpty(userList)){
            response.setResponse(ResponseEnum.SUCCESS,"校验用户是否存在失败，查询用户ID为空",false);
            return response;
        }
        //根据用户ID集合查询用户信息条数
        int count = sysUserService.findUserCountById(userList);
        response.setResponse(ResponseEnum.SUCCESS,count>0);
        return response;
    }

    /**
     * <p>@Description 查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 15:43 </p>
     * @param userId 用户id
     * @return com.www.common.data.dto.response.Response<com.www.common.pojo.dto.feign.UserInfoDTO>
     */
    @Override
    public Response<UserInfoDTO> findUserInfo(String userId) {
        Response<UserInfoDTO> response = new Response<>();
        if(StringUtils.isBlank(userId)){
            response.setResponse(ResponseEnum.FAIL,"查询失败，用户ID为空",null);
            return response;
        }
        SysUserEntity userEntity = sysUserService.findUserEntityById(userId);
        if(userEntity == null){
            response.setResponse(ResponseEnum.FAIL,"查询失败，查无此用户",null);
            return response;
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setSuId(userEntity.getSuId()).setUserId(userEntity.getUserId()).setUserName(userEntity.getUserName())
                .setPhoneNum(userEntity.getPhoneNum()).setBirthday(userEntity.getBirthday()).setSex(userEntity.getSex())
                .setPhoto(userEntity.getPhoto()).setEmail(userEntity.getEmail()).setBrief(userEntity.getBrief())
                .setUpdateTime(userEntity.getUpdateTime()).setCreateTime(userEntity.getCreateTime());
        response.setResponse(ResponseEnum.SUCCESS,userInfoDTO);
        return response;
    }

    /**
     * <p>@Description 更新用户密码 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:58 </p>
     * @param user 用户信息
     * @return Response<java.lang.String>
     */
    @Override
    public Response<String> updateUserPwd(SysUserDTO user) {
        Response<String> response = new Response<>();
        if(user == null || StringUtils.isAnyBlank(user.getUserId(),user.getPassword(),user.getNewPassWord())){
            response.setResponse(ResponseEnum.FAIL,"更新用户密码失败，密码不能为空");
            return response;
        }
        SysUserEntity userEntity = sysUserService.findUserEntityById(user.getUserId());
        if(userEntity == null){
            response.setResponse(ResponseEnum.FAIL,"查询不到该用户");
            return response;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //有输入密码则校验密码
        if(!encoder.matches(user.getPassword(),userEntity.getPassword())){
            response.setResponse(ResponseEnum.FAIL,"密码不正确");
            return response;
        }
        //更新用户信息
        UpdateWrapper<SysUserEntity> userWrapper = new UpdateWrapper<>();
        userWrapper.lambda().eq(SysUserEntity::getUserId,user.getUserId());
        userWrapper.lambda().set(SysUserEntity::getPassword,encoder.encode(user.getNewPassWord()));
        userWrapper.lambda().set(SysUserEntity::getUpdateTime,DateUtils.getCurrentDateTime());
        boolean isOk = sysUserService.updateEntity(userWrapper);
        if(!isOk){
            response.setResponse(ResponseEnum.FAIL,"更新用户密码失败");
        }
        response.setResponse(ResponseEnum.SUCCESS,"更新用户密码成功");
        return response;
    }

    /**
     * <p>@Description 查询用户vue的router权限 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 00:22 </p>
     * @param userId 用户ID
     * @return Response
     */
    @Override
    public Response<List<SysMenuDTO>> findUserRouter(String userId) {
        List<SysMenuDTO> resultList = new ArrayList<>();
        List<SysMenuDTO> menuList = sysMenuMapper.findUserRouter(userId,"admin-router");
        if(CollectionUtils.isNotEmpty(menuList)){
            for (SysMenuDTO treeNode : menuList) {
                if (treeNode.getParentId() == null) {
                    resultList.add(treeNode);
                }
                for (SysMenuDTO it : menuList) {
                    if (it.getParentId() == treeNode.getMenuId()) {
                        if (treeNode.getChildren() == null) {
                            treeNode.setChildren(new ArrayList<SysMenuDTO>());
                        }
                        treeNode.getChildren().add(it);
                    }
                }
            }
        }
        return new Response(ResponseEnum.SUCCESS,resultList);
    }
    /**
     * <p>@Description 查询用户菜单列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 00:22 </p>
     * @param userId 用户ID
     * @return Response
     */
    @Override
    public Response<List<SysMenuDTO>> findUserMenu(String userId) {
        List<SysMenuDTO> resultList = new ArrayList<>();
        List<SysMenuDTO> menuList = sysMenuMapper.findUserMenu(userId,"admin-menu");
        if(CollectionUtils.isNotEmpty(menuList)){
            for (SysMenuDTO treeNode : menuList) {
                if (treeNode.getParentId() == null) {
                    resultList.add(treeNode);
                }
                for (SysMenuDTO it : menuList) {
                    if (it.getParentId() == treeNode.getMenuId()) {
                        if (treeNode.getChildren() == null) {
                            treeNode.setChildren(new ArrayList<SysMenuDTO>());
                        }
                        treeNode.getChildren().add(it);
                    }
                }
            }
        }
        return new Response(ResponseEnum.SUCCESS,resultList);
    }
    /**
     * <p>@Description 更新用户头像 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 20:02 </p>
     * @param photo  头像文件
     * @param userId 用户ID
     * @return Response<java.lang.String>
     */
    @Override
    public Response<String> uploadPhoto(MultipartFile photo, String userId) {
        Response<String> response = new Response<>();
        SysUserEntity userEntity = sysUserService.findUserEntityById(userId);
        if(userEntity == null){
            response.setResponse(ResponseEnum.FAIL,"查询不到该用户");
            return response;
        }
        String path = fileService.uploadFileBackURL(photo,"photo",userId);
        if(StringUtils.isBlank(path)){
            response.setResponse(ResponseEnum.FAIL,"更新用户头像失败");
            return response;
        }
        UpdateWrapper<SysUserEntity> userWrapper = new UpdateWrapper<>();
        userWrapper.lambda().eq(SysUserEntity::getUserId,userEntity.getUserId());
        userWrapper.lambda().set(SysUserEntity::getPhoto,path);
        boolean isOk = sysUserService.updateEntity(userWrapper);
        if(!isOk){
            response.setResponse(ResponseEnum.FAIL,"更新用户头像失败");
        }
        response.setResponse(ResponseEnum.SUCCESS,path);
        return response;
    }
    /**
     * <p>@Description 更新用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:58 </p>
     * @param user 用户信息
     * @return Response<java.lang.String>
     */
    @Override
    public Response<String> updateUserInfo(SysUserDTO user) {
        Response<String> response = new Response<>();
        if(user == null || StringUtils.isAnyBlank(user.getUserId(),user.getUserName())
                || (StringUtils.isNotBlank(user.getSex()) && CodeDict.isIllegalValue(CodeTypeEnum.SEX.getCodeType(),user.getSex()))){
            response.setResponse(ResponseEnum.FAIL,"更新用户信息失败，用户信息有误");
            return response;
        }
        SysUserEntity userEntity = sysUserService.findUserEntityById(user.getUserId());
        if(userEntity == null){
            response.setResponse(ResponseEnum.FAIL,"查询不到该用户");
            return response;
        }
        //更新用户信息
        UpdateWrapper<SysUserEntity> userWrapper = new UpdateWrapper<>();
        userWrapper.lambda().eq(SysUserEntity::getUserId,user.getUserId());
        userWrapper.lambda().set(SysUserEntity::getUserName,user.getUserName());
        userWrapper.lambda().set(SysUserEntity::getPhoneNum,user.getPhoneNum());
        userWrapper.lambda().set(SysUserEntity::getBirthday,DateUtils.parse(user.getBirthday(), DateFormatEnum.YYYY_MM_DD));
        userWrapper.lambda().set(SysUserEntity::getSex,user.getSex());
        userWrapper.lambda().set(SysUserEntity::getEmail,user.getEmail());
        userWrapper.lambda().set(SysUserEntity::getBrief,user.getBrief());
        userWrapper.lambda().set(SysUserEntity::getUpdateTime,DateUtils.getCurrentDateTime());
        boolean isOk = sysUserService.updateEntity(userWrapper);
        if(!isOk){
            response.setResponse(ResponseEnum.FAIL,"更新用户信息失败");
        }
        response.setResponse(ResponseEnum.SUCCESS,"更新用户信息成功");
        return response;
    }

    /**
     * <p>@Description 查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:43 </p>
     * @param userId 用户ID
     * @return Response<com.www.myblog.base.data.dto.SysUserDTO>
     */
    @Override
    public Response<SysUserDTO> findUser(String userId) {
        Response<SysUserDTO> response = new Response<>();
        SysUserDTO userDTO = sysUserMapper.findUserInfoById(userId);
        if(userDTO == null){
            response.setCode(ResponseEnum.FAIL.getCode());
            response.setMsg("查询不到该用户信息");
            return response;
        }
        response.setResponse(ResponseEnum.SUCCESS,userDTO);
        return response;
    }

    /**
     * <p>@Description 创建用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/7 21:03 </p>
     * @param user
     * @return Response<java.lang.String>
     */
    @Override
    public Response<String> createUser(SysUserDTO user) {
        Response<String> response = new Response<>();
        if(user == null || StringUtils.isAnyBlank(user.getUserId(),user.getUserName(),user.getPassword())
                || (StringUtils.isNotBlank(user.getSex()) && CodeDict.isIllegalValue(CodeTypeEnum.SEX.getCodeType(),user.getSex()))){
            response.setResponse(ResponseEnum.FAIL,"信息不完整，创建用户失败");
            return response;
        }
        SysRoleEntity roleEntity = sysRoleService.findRoleEntityByName("user");
        if(roleEntity == null){
            response.setCode(ResponseEnum.FAIL.getCode()).setMsg("用户角色错误，创建用户失败");
            return response;
        }
        //创建用户
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setUserId(user.getUserId());
        userEntity.setUserName(user.getUserName());
        userEntity.setPassword(user.getPassword());
        userEntity.setSex(user.getSex());
        userEntity.setPhoneNum(user.getPhoneNum());
        userEntity.setBirthday(DateUtils.parse(user.getBirthday(), DateFormatEnum.YYYY_MM_DD));
        userEntity.setEmail(user.getEmail());
        userEntity.setCreateTime(DateUtils.getCurrentDateTime());
        userEntity.setUpdateTime(DateUtils.getCurrentDateTime());
        sysUserService.createEntity(userEntity);
        //创建用户角色
        SysUserRoleEntity userRoleEntity = new SysUserRoleEntity();
        userRoleEntity.setSuId(userEntity.getSuId());
        userRoleEntity.setRoleId(roleEntity.getRoleId());
        userRoleEntity.setCreateTime(DateUtils.getCurrentDateTime());
        userRoleEntity.setUpdateTime(DateUtils.getCurrentDateTime());
        sysUserRoleService.createEntity(userRoleEntity);
        response.setResponse(ResponseEnum.SUCCESS,"创建用户成功");
        return response;
    }
    /**
     * <p>@Description 查询所有角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 12:53 </p>
     * @return Response<java.util.List < com.www.myblog.base.data.dto.SysUserRoleDTO>>
     */
    @Override
    public Response<List<SysRoleDTO>> findAllRole() {
        List<SysRoleDTO> list = sysRoleMapper.findAllRole();
        return new Response<>(ResponseEnum.SUCCESS,list);
    }

    /**
     * <p>@Description 更新用户状态 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/2 20:58 </p>
     * @param userId      用户id
     * @param stateCd     用户状态
     * @param notExpired  是否过期
     * @param notLocked   账号是否锁定
     * @param credentialsNotExpired 密码是否过期
     * @return Response<java.lang.String>
     */
    @Override
    public Response<String> updateState(String userId, String stateCd, String notExpired, String notLocked, String credentialsNotExpired) {
        Response<String> response = new Response<>();
        if(CodeDict.isIllegalValue(CodeTypeEnum.USER_STATUS.getCodeType(),stateCd)
                || CodeDict.isIllegalValue(CodeTypeEnum.YES_NO.getCodeType(),notExpired)
                || CodeDict.isIllegalValue(CodeTypeEnum.YES_NO.getCodeType(),notLocked)
                || CodeDict.isIllegalValue(CodeTypeEnum.YES_NO.getCodeType(),credentialsNotExpired)){
            response.setResponse(ResponseEnum.FAIL,"更新用户信息失败");
            return response;
        }
        UpdateWrapper<SysUserEntity> userWrapper = new UpdateWrapper<>();
        userWrapper.lambda().eq(SysUserEntity::getUserId,userId);
        userWrapper.lambda().set(SysUserEntity::getStateCd,stateCd);
        userWrapper.lambda().set(SysUserEntity::getNotExpired,notExpired);
        userWrapper.lambda().set(SysUserEntity::getNotLocked,notLocked);
        userWrapper.lambda().set(SysUserEntity::getCredentialsNotExpired,credentialsNotExpired);
        userWrapper.lambda().set(SysUserEntity::getUpdateTime,DateUtils.getCurrentDateTime());
        boolean isOk = sysUserService.updateEntity(userWrapper);
        if(isOk){
            response.setResponse(ResponseEnum.SUCCESS,"更新用户信息成功");
        }else {
            response.setResponse(ResponseEnum.FAIL,"更新用户信息失败");
        }
        return response;
    }

    /**
     * <p>@Description 查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/30 21:10 </p>
     * @param stateCd 用户状态
     * @param userId 用户id
     * @param userName 用户名称
     * @param pageNum  当前页数
     * @param pageSize 页面条数
     * @return Response<java.util.List < com.www.myblog.base.data.dto.SysUserDTO>>
     */
    @Override
    public Response<List<SysUserDTO>> findAllUser(String stateCd, String userId,String userName, int pageNum, long pageSize) {
        SysUserDTO userDTO = new SysUserDTO();
        userDTO.setUserId(userId);
        userDTO.setUserName(userName);
        userDTO.setStateCd(stateCd);
        pageNum = pageNum <= 0 ? 1 : pageNum;
        pageSize = pageSize <= 0 ? 5 : pageSize;
        Page<SysUserDTO> page = new Page<>(pageNum,pageSize);
        page = sysUserMapper.findAllUser(page,userDTO);
        List<SysUserDTO> userList =  page.getRecords();
        Response<List<SysUserDTO>> response = new Response<>(ResponseEnum.SUCCESS,userList);
        response.setPageNum(pageNum);
        response.setPageSize(pageSize);
        response.setTotalNum(page.getTotal());
        return response;
    }
}
