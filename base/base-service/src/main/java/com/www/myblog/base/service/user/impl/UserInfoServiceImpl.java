package com.www.myblog.base.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.www.common.config.code.CodeDict;
import com.www.common.config.mvc.upload.IFileUpload;
import com.www.common.feign.blog.IBlogFeignService;
import com.www.common.pojo.constant.CharConstant;
import com.www.common.pojo.dto.feign.UserInfoDTO;
import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.common.pojo.enums.CodeTypeEnum;
import com.www.common.utils.DateUtils;
import com.www.myblog.base.data.dto.SysMenuDTO;
import com.www.myblog.base.data.dto.SysRoleDTO;
import com.www.myblog.base.data.dto.SysUserDTO;
import com.www.myblog.base.data.entity.SysRoleEntity;
import com.www.myblog.base.data.entity.SysUserEntity;
import com.www.myblog.base.data.entity.SysUserRoleEntity;
import com.www.myblog.base.data.mapper.SysMenuMapper;
import com.www.myblog.base.data.mapper.SysRoleMapper;
import com.www.myblog.base.data.mapper.SysUserMapper;
import com.www.myblog.base.data.mapper.SysUserRoleMapper;
import com.www.myblog.base.service.entity.ISysRoleService;
import com.www.myblog.base.service.entity.ISysUserService;
import com.www.myblog.base.service.user.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private IFileUpload fileService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private IBlogFeignService blogFeignService;
    @Value("${server.port}")
    private String serverPort;
    @Value("${eureka.instance.ip-address}")
    private String ip;

    /**
     * <p>@Description 查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 15:43 </p>
     * @param userId 用户id
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.common.pojo.dto.feign.UserInfoDTO>
     */
    @Override
    public ResponseDTO<UserInfoDTO> findUserInfo(String userId) {
        ResponseDTO<UserInfoDTO> response = new ResponseDTO<>();
        if(StringUtils.isBlank(userId)){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"查询失败，用户ID为空",null);
            return response;
        }
        SysUserEntity userEntity = sysUserService.findUserEntityById(userId);
        if(userEntity == null){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"查询失败，查无此用户",null);
            return response;
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setSuId(userEntity.getSuId()).setUserId(userEntity.getUserId()).setUserName(userEntity.getUserName())
                .setPhoneNum(userEntity.getPhoneNum()).setBirthday(userEntity.getBirthday()).setSex(userEntity.getSex())
                .setPhoto(userEntity.getPhoto()).setEmail(userEntity.getEmail()).setBrief(userEntity.getBrief())
                .setUpdateTime(userEntity.getUpdateTime()).setCreateTime(userEntity.getCreateTime());
        response.setResponse(ResponseDTO.RespEnum.SUCCESS,userInfoDTO);
        return response;
    }

    /**
     * <p>@Description 更新用户密码 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:58 </p>
     * @param user 用户信息
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @Override
    public ResponseDTO<String> updateUserPwd(SysUserDTO user) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if(user == null || StringUtils.isAnyBlank(user.getUserId(),user.getPassword(),user.getNewPassWord())){
            responseDTO.setResponse(ResponseDTO.RespEnum.FAIL,"更新用户密码失败，密码不能为空");
            return responseDTO;
        }
        SysUserEntity userEntity = sysUserService.findUserEntityById(user.getUserId());
        if(userEntity == null){
            responseDTO.setResponse(ResponseDTO.RespEnum.FAIL,"查询不到该用户");
            return responseDTO;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //有输入密码则校验密码
        if(!encoder.matches(user.getPassword(),userEntity.getPassword())){
            responseDTO.setResponse(ResponseDTO.RespEnum.FAIL,"密码不正确");
            return responseDTO;
        }
        //更新用户信息
        UpdateWrapper<SysUserEntity> userWrapper = new UpdateWrapper<>();
        userWrapper.lambda().eq(SysUserEntity::getUserId,user.getUserId());
        userWrapper.lambda().set(SysUserEntity::getPassword,encoder.encode(user.getNewPassWord()));
        userWrapper.lambda().set(SysUserEntity::getUpdateTime,DateUtils.getCurrentDateTime());
        int count = sysUserMapper.update(null,userWrapper);
        if(count == 0){
            responseDTO.setResponse(ResponseDTO.RespEnum.FAIL,"更新用户密码失败");
        }
        responseDTO.setResponse(ResponseDTO.RespEnum.SUCCESS,"更新用户密码成功");
        return responseDTO;
    }

    /**
     * <p>@Description 查询用户vue的router权限 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 00:22 </p>
     * @param userId 用户ID
     * @return com.www.myblog.common.pojo.ResponseDTO
     */
    @Override
    public ResponseDTO<List<SysMenuDTO>> findUserRouter(String userId) {
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
        return new ResponseDTO(ResponseDTO.RespEnum.SUCCESS,resultList);
    }
    /**
     * <p>@Description 查询用户菜单列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 00:22 </p>
     * @param userId 用户ID
     * @return com.www.myblog.common.pojo.ResponseDTO
     */
    @Override
    public ResponseDTO<List<SysMenuDTO>> findUserMenu(String userId) {
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
        return new ResponseDTO(ResponseDTO.RespEnum.SUCCESS,resultList);
    }
    /**
     * <p>@Description 更新用户头像 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 20:02 </p>
     * @param photo  头像文件
     * @param userId 用户ID
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @Override
    public ResponseDTO<String> uploadPhoto(MultipartFile photo, String userId) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        SysUserEntity userEntity = sysUserService.findUserEntityById(userId);
        if(userEntity == null){
            responseDTO.setResponse(ResponseDTO.RespEnum.FAIL,"查询不到该用户");
            return responseDTO;
        }
        String path = fileService.uploadFileBackPath(photo,userId);
        if(StringUtils.isBlank(path)){
            responseDTO.setResponse(ResponseDTO.RespEnum.FAIL,"更新用户头像失败");
            return responseDTO;
        }
        path += CharConstant.QUESTION_MARK + DateUtils.format(DateUtils.getCurrentDateTime(), DateUtils.DateFormatEnum.YYYYMMDDHHMMSSSSS);
        path = "http://" + ip + CharConstant.COLON + serverPort + path;
        UpdateWrapper<SysUserEntity> userWrapper = new UpdateWrapper<>();
        userWrapper.lambda().eq(SysUserEntity::getUserId,userEntity.getUserId());
        userWrapper.lambda().set(SysUserEntity::getPhoto,path);
        int count = sysUserMapper.update(null,userWrapper);
        if(count == 0){
            responseDTO.setResponse(ResponseDTO.RespEnum.FAIL,"更新用户头像失败");
        }
        responseDTO.setResponse(ResponseDTO.RespEnum.SUCCESS,path);
        return responseDTO;
    }
    /**
     * <p>@Description 更新用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:58 </p>
     * @param user 用户信息
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @Override
    public ResponseDTO<String> updateUserInfo(SysUserDTO user) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if(user == null || StringUtils.isAnyBlank(user.getUserId(),user.getUserName())
                || CodeDict.isIllegalValue(CodeTypeEnum.SEX,user.getSex())){
            responseDTO.setResponse(ResponseDTO.RespEnum.FAIL,"更新用户信息失败，用户信息有误");
            return responseDTO;
        }
        SysUserEntity userEntity = sysUserService.findUserEntityById(user.getUserId());
        if(userEntity == null){
            responseDTO.setResponse(ResponseDTO.RespEnum.FAIL,"查询不到该用户");
            return responseDTO;
        }
        //更新用户信息
        UpdateWrapper<SysUserEntity> userWrapper = new UpdateWrapper<>();
        userWrapper.lambda().eq(SysUserEntity::getUserId,user.getUserId());
        userWrapper.lambda().set(SysUserEntity::getUserName,user.getUserName());
        userWrapper.lambda().set(SysUserEntity::getPhoneNum,user.getPhoneNum());
        userWrapper.lambda().set(SysUserEntity::getBirthday,DateUtils.parse(user.getBirthday(), DateUtils.DateFormatEnum.YYYY_MM_DD));
        userWrapper.lambda().set(SysUserEntity::getSex,user.getSex());
        userWrapper.lambda().set(SysUserEntity::getEmail,user.getEmail());
        userWrapper.lambda().set(SysUserEntity::getBrief,user.getBrief());
        userWrapper.lambda().set(SysUserEntity::getUpdateTime,DateUtils.getCurrentDateTime());
        int count = sysUserMapper.update(null,userWrapper);
        if(count == 0){
            responseDTO.setResponse(ResponseDTO.RespEnum.FAIL,"更新用户信息失败");
        }
        responseDTO.setResponse(ResponseDTO.RespEnum.SUCCESS,"更新用户信息成功");
        return responseDTO;
    }

    /**
     * <p>@Description 查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:43 </p>
     * @param userId 用户ID
     * @return com.www.myblog.common.pojo.ResponseDTO<com.www.myblog.base.data.dto.SysUserDTO>
     */
    @Override
    public ResponseDTO<SysUserDTO> findUser(String userId) {
        ResponseDTO<SysUserDTO> responseDTO = new ResponseDTO<>();
        SysUserDTO userDTO = sysUserMapper.findUserInfoById(userId);
        if(userDTO == null){
            responseDTO.setCode(ResponseDTO.RespEnum.FAIL.getCode());
            responseDTO.setMsg("查询不到该用户信息");
            return responseDTO;
        }
        responseDTO.setResponse(ResponseDTO.RespEnum.SUCCESS,userDTO);
        return responseDTO;
    }

    /**
     * <p>@Description 创建用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/7 21:03 </p>
     * @param user
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @Override
    public ResponseDTO<String> createUser(SysUserDTO user) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if(user == null || StringUtils.isAnyBlank(user.getUserId(),user.getUserName(),user.getPassword())
                || (StringUtils.isNotBlank(user.getSex()) && CodeDict.isIllegalValue(CodeTypeEnum.SEX,user.getSex()))){
            responseDTO.setResponse(ResponseDTO.RespEnum.FAIL,"信息不完整，创建用户失败");
            return responseDTO;
        }
        SysRoleEntity roleEntity = sysRoleService.findRoleEntityByName("user");
        if(roleEntity == null){
            responseDTO.setCode(ResponseDTO.RespEnum.FAIL.getCode()).setMsg("用户角色错误，创建用户失败");
            return responseDTO;
        }
        //创建用户
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setUserId(user.getUserId());
        userEntity.setUserName(user.getUserName());
        userEntity.setPassword(user.getPassword());
        userEntity.setSex(user.getSex());
        userEntity.setPhoneNum(user.getPhoneNum());
        userEntity.setBirthday(DateUtils.parse(user.getBirthday(), DateUtils.DateFormatEnum.YYYY_MM_DD));
        userEntity.setEmail(user.getEmail());
        userEntity.setCreateTime(DateUtils.getCurrentDateTime());
        userEntity.setUpdateTime(DateUtils.getCurrentDateTime());
        sysUserMapper.insert(userEntity);
        //创建用户角色
        SysUserRoleEntity userRoleEntity = new SysUserRoleEntity();
        userRoleEntity.setSuId(userEntity.getSuId());
        userRoleEntity.setRoleId(roleEntity.getRoleId());
        userRoleEntity.setCreateTime(DateUtils.getCurrentDateTime());
        userRoleEntity.setUpdateTime(DateUtils.getCurrentDateTime());
        sysUserRoleMapper.insert(userRoleEntity);
        responseDTO.setResponse(ResponseDTO.RespEnum.SUCCESS,"创建用户成功");
        return responseDTO;
    }
    /**
     * <p>@Description 查询所有角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 12:53 </p>
     * @return com.www.myblog.common.pojo.ResponseDTO<java.util.List < com.www.myblog.base.data.dto.SysUserRoleDTO>>
     */
    @Override
    public ResponseDTO<List<SysRoleDTO>> findAllRole() {
        List<SysRoleDTO> list = sysRoleMapper.findAllRole();
        return new ResponseDTO<>(ResponseDTO.RespEnum.SUCCESS,list);
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
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @Override
    public ResponseDTO<String> updateState(String userId, String stateCd, String notExpired, String notLocked, String credentialsNotExpired) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if(CodeDict.isIllegalValue(CodeTypeEnum.USER_STATUS,stateCd)
                || CodeDict.isIllegalValue(CodeTypeEnum.YES_NO,notExpired)
                || CodeDict.isIllegalValue(CodeTypeEnum.YES_NO,notLocked)
                || CodeDict.isIllegalValue(CodeTypeEnum.YES_NO,credentialsNotExpired)){
            responseDTO.setResponse(ResponseDTO.RespEnum.FAIL,"更新用户信息失败");
            return responseDTO;
        }
        UpdateWrapper<SysUserEntity> userWrapper = new UpdateWrapper<>();
        userWrapper.lambda().eq(SysUserEntity::getUserId,userId);
        userWrapper.lambda().set(SysUserEntity::getStateCd,stateCd);
        userWrapper.lambda().set(SysUserEntity::getNotExpired,notExpired);
        userWrapper.lambda().set(SysUserEntity::getNotLocked,notLocked);
        userWrapper.lambda().set(SysUserEntity::getCredentialsNotExpired,credentialsNotExpired);
        userWrapper.lambda().set(SysUserEntity::getUpdateTime,DateUtils.getCurrentDateTime());
        int count = sysUserMapper.update(null,userWrapper);
        if(count != 0){
            responseDTO.setResponse(ResponseDTO.RespEnum.SUCCESS,"更新用户信息成功");
        }else {
            responseDTO.setResponse(ResponseDTO.RespEnum.FAIL,"更新用户信息失败");
        }
        return responseDTO;
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
     * @return com.www.myblog.common.pojo.ResponseDTO<java.util.List < com.www.myblog.base.data.dto.SysUserDTO>>
     */
    @Override
    public ResponseDTO<List<SysUserDTO>> findAllUser(String stateCd, String userId,String userName, int pageNum, int pageSize) {
        SysUserDTO userDTO = new SysUserDTO();
        userDTO.setUserId(userId);
        userDTO.setUserName(userName);
        userDTO.setStateCd(stateCd);
        Page<SysUserDTO> page = new Page<>(pageNum,pageSize);
        page = sysUserMapper.findAllUser(page,userDTO);
        List<SysUserDTO> userList =  page.getRecords();
        ResponseDTO<List<SysUserDTO>> responseDTO = new ResponseDTO<>(ResponseDTO.RespEnum.SUCCESS,userList);
        responseDTO.setPageNum(pageNum);
        responseDTO.setPageSize(pageSize);
        responseDTO.setTotalNum(page.getTotal());
        return responseDTO;
    }
}
