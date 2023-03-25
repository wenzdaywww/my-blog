package com.www.myblog.base.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.www.common.config.code.CodeDict;
import com.www.common.config.exception.BusinessException;
import com.www.common.config.mvc.upload.IFileService;
import com.www.common.data.constant.CharConstant;
import com.www.common.data.enums.DateFormatEnum;
import com.www.common.data.response.Result;
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
    private ISysUserRoleService sysUserRoleService;
    @Autowired
    private IFileService fileService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysRoleService sysRoleService;
    /** 端口 **/
    @Value("${server.port}")
    private String port;
    /** ip地址 **/
    @Value("${eureka.instance.ip-address}")
    private String ip;

    /**
     * <p>@Description 查询多个用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 15:43 </p>
     * @param userList 用户id集合
     * @return com.www.common.data.dto.response.Response<com.www.common.pojo.dto.feign.UserInfoDTO>
     */
    @Override
    public Result<List<UserInfoDTO>> findUserInfoList(List<String> userList) {
        if(CollectionUtils.isEmpty(userList)){
            throw new BusinessException("查询多个用户信息失败，用户ID集合为空");
        }
        //根据用户ID集合查询用户信息
        List<SysUserEntity> entityList = sysUserService.findUserEntityById(userList);
        if(CollectionUtils.isEmpty(entityList)){
            throw new BusinessException("查询多个用户信息失败，查询不到用户信息");
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
        return new Result<>(dtoList);
    }

    /**
     * <p>@Description 校验用户是否存在 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 15:43 </p>
     * @param userList 用户id集合
     * @return com.www.common.data.dto.response.Response<Boolean>
     */
    @Override
    public Result<Boolean> validateUserExist(List<String> userList) {
        if(CollectionUtils.isEmpty(userList)){
            throw new BusinessException("校验用户是否存在失败，查询用户ID为空");
        }
        //根据用户ID集合查询用户信息条数
        int count = sysUserService.findUserCountById(userList);
        return new Result<>(count>0);
    }

    /**
     * <p>@Description 查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 15:43 </p>
     * @param userId 用户id
     * @return com.www.common.data.dto.response.Response<com.www.common.pojo.dto.feign.UserInfoDTO>
     */
    @Override
    public Result<UserInfoDTO> findUserInfo(String userId) {
        if(StringUtils.isBlank(userId)){
            throw new BusinessException("查询失败，用户ID为空");
        }
        SysUserEntity userEntity = sysUserService.findUserEntityById(userId);
        if(userEntity == null){
            throw new BusinessException("查询失败，查无此用户");
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setSuId(userEntity.getSuId()).setUserId(userEntity.getUserId()).setUserName(userEntity.getUserName())
                .setPhoneNum(userEntity.getPhoneNum()).setBirthday(userEntity.getBirthday()).setSex(userEntity.getSex())
                .setPhoto(userEntity.getPhoto()).setEmail(userEntity.getEmail()).setBrief(userEntity.getBrief())
                .setUpdateTime(userEntity.getUpdateTime()).setCreateTime(userEntity.getCreateTime());
        return new Result<>(userInfoDTO);
    }

    /**
     * <p>@Description 更新用户密码 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:58 </p>
     * @param user 用户信息
     * @return Response<java.lang.String>
     */
    @Override
    public Result<String> updateUserPwd(SysUserDTO user) {
        if(user == null || StringUtils.isAnyBlank(user.getUserId(),user.getPassword(),user.getNewPassWord())){
            throw new BusinessException("更新用户密码失败，密码不能为空");
        }
        SysUserEntity userEntity = sysUserService.findUserEntityById(user.getUserId());
        if(userEntity == null){
            throw new BusinessException("查询不到该用户");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //有输入密码则校验密码
        if(!encoder.matches(user.getPassword(),userEntity.getPassword())){
            throw new BusinessException("密码不正确");
        }
        //更新用户信息
        UpdateWrapper<SysUserEntity> userWrapper = new UpdateWrapper<>();
        userWrapper.lambda().eq(SysUserEntity::getUserId,user.getUserId());
        userWrapper.lambda().set(SysUserEntity::getPassword,encoder.encode(user.getNewPassWord()));
        userWrapper.lambda().set(SysUserEntity::getUpdateTime,DateUtils.getCurrentDateTime());
        boolean isOk = sysUserService.updateEntity(userWrapper);
        if(isOk){
            return new Result<>("更新用户密码成功");
        }
        return new Result<>("更新用户密码失败");
    }

    /**
     * <p>@Description 查询用户vue的router权限 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 00:22 </p>
     * @param userId 用户ID
     * @return Response
     */
    @Override
    public Result<List<SysMenuDTO>> findUserRouter(String userId) {
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
        return new Result(resultList);
    }
    /**
     * <p>@Description 查询用户菜单列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/11 00:22 </p>
     * @param userId 用户ID
     * @return Response
     */
    @Override
    public Result<List<SysMenuDTO>> findUserMenu(String userId) {
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
        return new Result<>(resultList);
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
    public Result<String> uploadPhoto(MultipartFile photo, String userId) {
        SysUserEntity userEntity = sysUserService.findUserEntityById(userId);
        if(userEntity == null){
            throw new BusinessException("查询不到该用户");
        }
        String httpAddr = CharConstant.HTTP + ip + CharConstant.COLON + port;
        String path = fileService.uploadFileBackURL(photo,httpAddr,"photo",userId);
        if(StringUtils.isBlank(path)){
            throw new BusinessException("更新用户头像失败");
        }
        UpdateWrapper<SysUserEntity> userWrapper = new UpdateWrapper<>();
        userWrapper.lambda().eq(SysUserEntity::getUserId,userEntity.getUserId());
        userWrapper.lambda().set(SysUserEntity::getPhoto,path);
        boolean isOk = sysUserService.updateEntity(userWrapper);
        if(isOk){
            return new Result<>(path);
        }
        return new Result<>("更新用户头像失败");
    }
    /**
     * <p>@Description 更新用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:58 </p>
     * @param user 用户信息
     * @return Response<java.lang.String>
     */
    @Override
    public Result<String> updateUserInfo(SysUserDTO user) {
        if(user == null || StringUtils.isAnyBlank(user.getUserId(),user.getUserName())
                || (StringUtils.isNotBlank(user.getSex()) && CodeDict.isIllegalValue(CodeTypeEnum.SEX.getCodeType(),user.getSex()))){
            throw new BusinessException("更新用户信息失败，用户信息有误");
        }
        SysUserEntity userEntity = sysUserService.findUserEntityById(user.getUserId());
        if(userEntity == null){
            throw new BusinessException("查询不到该用户");
        }
        //更新用户信息
        UpdateWrapper<SysUserEntity> userWrapper = new UpdateWrapper<>();
        userWrapper.lambda().eq(SysUserEntity::getUserId,user.getUserId());
        userWrapper.lambda().set(SysUserEntity::getUserName,user.getUserName());
        userWrapper.lambda().set(SysUserEntity::getPhoneNum,user.getPhoneNum());
        userWrapper.lambda().set(SysUserEntity::getBirthday,DateUtils.parse(user.getBirthday(), DateFormatEnum.YYYYMMDD1));
        userWrapper.lambda().set(SysUserEntity::getSex,user.getSex());
        userWrapper.lambda().set(SysUserEntity::getEmail,user.getEmail());
        userWrapper.lambda().set(SysUserEntity::getBrief,user.getBrief());
        userWrapper.lambda().set(SysUserEntity::getUpdateTime,DateUtils.getCurrentDateTime());
        boolean isOk = sysUserService.updateEntity(userWrapper);
        if(isOk){
            return new Result<>("更新用户信息成功");
        }
        return new Result<>("更新用户信息失败");
    }

    /**
     * <p>@Description 查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:43 </p>
     * @param userId 用户ID
     * @return Response<com.www.myblog.base.data.dto.SysUserDTO>
     */
    @Override
    public Result<SysUserDTO> findUser(String userId) {
        SysUserDTO userDTO = sysUserMapper.findUserInfoById(userId);
        if(userDTO == null){
            throw new BusinessException("查询不到该用户信息");
        }
        return new Result<>(userDTO);
    }

    /**
     * <p>@Description 创建用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/7 21:03 </p>
     * @param user
     * @return Response<java.lang.String>
     */
    @Override
    public Result<String> createUser(SysUserDTO user) {
        if(user == null || StringUtils.isAnyBlank(user.getUserId(),user.getUserName(),user.getPassword())
                || (StringUtils.isNotBlank(user.getSex()) && CodeDict.isIllegalValue(CodeTypeEnum.SEX.getCodeType(),user.getSex()))){
            throw new BusinessException("信息不完整，创建用户失败");
        }
        SysRoleEntity roleEntity = sysRoleService.findRoleEntityByName("user");
        if(roleEntity == null){
            throw new BusinessException("用户角色错误，创建用户失败");
        }
        //创建用户
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setUserId(user.getUserId());
        userEntity.setUserName(user.getUserName());
        userEntity.setPassword(user.getPassword());
        userEntity.setSex(user.getSex());
        userEntity.setPhoneNum(user.getPhoneNum());
        userEntity.setBirthday(DateUtils.parse(user.getBirthday(), DateFormatEnum.YYYYMMDD1));
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
        return new Result<>("创建用户成功");
    }
    /**
     * <p>@Description 查询所有角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 12:53 </p>
     * @return Response<java.util.List < com.www.myblog.base.data.dto.SysUserRoleDTO>>
     */
    @Override
    public Result<List<SysRoleDTO>> findAllRole() {
        List<SysRoleDTO> list = sysRoleMapper.findAllRole();
        return new Result<>(list);
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
    public Result<String> updateState(String userId, String stateCd, String notExpired, String notLocked, String credentialsNotExpired) {
        Result<String> response = new Result<>();
        if(CodeDict.isIllegalValue(CodeTypeEnum.USER_STATUS.getCodeType(),stateCd)
                || CodeDict.isIllegalValue(CodeTypeEnum.YES_NO.getCodeType(),notExpired)
                || CodeDict.isIllegalValue(CodeTypeEnum.YES_NO.getCodeType(),notLocked)
                || CodeDict.isIllegalValue(CodeTypeEnum.YES_NO.getCodeType(),credentialsNotExpired)){
            throw new BusinessException("更新用户信息失败");
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
            return new Result<>("更新用户信息成功");
        }
        return new Result<>("更新用户信息失败");
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
    public Result<List<SysUserDTO>> findAllUser(String stateCd, String userId,String userName, int pageNum, long pageSize) {
        SysUserDTO userDTO = new SysUserDTO();
        userDTO.setUserId(userId);
        userDTO.setUserName(userName);
        userDTO.setStateCd(stateCd);
        pageNum = pageNum <= 0 ? 1 : pageNum;
        pageSize = pageSize <= 0 ? 5 : pageSize;
        Page<SysUserDTO> page = new Page<>(pageNum,pageSize);
        page = sysUserMapper.findAllUser(page,userDTO);
        List<SysUserDTO> userList =  page.getRecords();
        Result<List<SysUserDTO>> listResult = new Result<>(userList);
        listResult.setPageNum(pageNum);
        listResult.setPageSize(pageSize);
        listResult.setTotalNum(page.getTotal());
        return listResult;
    }
}
