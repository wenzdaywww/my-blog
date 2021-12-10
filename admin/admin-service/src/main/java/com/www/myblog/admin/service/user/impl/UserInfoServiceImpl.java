package com.www.myblog.admin.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.regexp.internal.RE;
import com.www.myblog.admin.data.dto.SysMenuDTO;
import com.www.myblog.admin.data.dto.SysRoleDTO;
import com.www.myblog.admin.data.dto.SysUserDTO;
import com.www.myblog.admin.data.entity.SysRoleEntity;
import com.www.myblog.admin.data.entity.SysUserEntity;
import com.www.myblog.admin.data.entity.SysUserRoleEntity;
import com.www.myblog.admin.data.enums.CommonEnum;
import com.www.myblog.admin.data.mapper.SysMenuMapper;
import com.www.myblog.admin.data.mapper.SysRoleMapper;
import com.www.myblog.admin.data.mapper.SysUserMapper;
import com.www.myblog.admin.data.mapper.SysUserRoleMapper;
import com.www.myblog.admin.service.entity.ISysRoleService;
import com.www.myblog.admin.service.entity.ISysUserService;
import com.www.myblog.admin.service.user.IUserInfoService;
import com.www.myblog.common.pojo.ResponseDTO;
import com.www.myblog.common.pojo.ResponseEnum;
import com.www.myblog.common.service.IFileService;
import com.www.myblog.common.utils.DateUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
public class UserInfoServiceImpl implements IUserInfoService {
    private static Logger LOG = LoggerFactory.getLogger(UserInfoServiceImpl.class);
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private IFileService fileService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysRoleService sysRoleService;

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
        List<SysMenuDTO> menuList = sysMenuMapper.findUserMenu(userId);
        if(CollectionUtils.isNotEmpty(menuList)){
            resultList = menuList;
        }
        return new ResponseDTO(ResponseEnum.SUCCESS,resultList);
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
            responseDTO.setResponseCode(ResponseEnum.FAIL,"查询不到该用户");
            return responseDTO;
        }
        String path = fileService.uploadFileBackPath(photo,userId);
        UpdateWrapper<SysUserEntity> userWrapper = new UpdateWrapper<>();
        userWrapper.lambda().eq(SysUserEntity::getUserId,userEntity.getUserId());
        userWrapper.lambda().set(SysUserEntity::getPhoto,path);
        int count = sysUserMapper.update(null,userWrapper);
        if(count == 0){
            responseDTO.setResponseCode(ResponseEnum.FAIL,"更新用户头像失败");
        }
        responseDTO.setResponseCode(ResponseEnum.SUCCESS,path);
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
        if(user == null || StringUtils.isAnyBlank(user.getUserId(),user.getUserName(),user.getPassWord())
            || (StringUtils.isNotBlank(user.getSex()) && !StringUtils.containsAny(user.getSex(),CommonEnum.SEX_1.getCode(),CommonEnum.SEX_0.getCode()))){
            responseDTO.setResponseCode(ResponseEnum.FAIL,"更新用户信息失败，用户信息有误");
            return responseDTO;
        }
        SysUserEntity userEntity = sysUserService.findUserEntityById(user.getUserId());
        if(userEntity == null){
            responseDTO.setResponseCode(ResponseEnum.FAIL,"查询不到该用户");
            return responseDTO;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //有输入密码则校验密码
        if(StringUtils.isNotBlank(user.getPassWord()) && !encoder.matches(user.getPassWord(),userEntity.getPassWord())){
            responseDTO.setResponseCode(ResponseEnum.FAIL,"密码不正确");
            return responseDTO;
        }
        //更新用户信息
        UpdateWrapper<SysUserEntity> userWrapper = new UpdateWrapper<>();
        userWrapper.lambda().eq(SysUserEntity::getUserId,user.getUserId());
        if (StringUtils.isNotBlank(user.getNewPassWord())){
            userWrapper.lambda().set(SysUserEntity::getPassWord,encoder.encode(user.getNewPassWord()));
        }
        userWrapper.lambda().set(SysUserEntity::getUserName,user.getUserName());
        userWrapper.lambda().set(SysUserEntity::getPhoneNum,user.getPhoneNum());
        userWrapper.lambda().set(SysUserEntity::getBirthday,DateUtils.parse(user.getBirthday(), DateUtils.DateFormatEnum.YYYY_MM_DD));
        userWrapper.lambda().set(SysUserEntity::getSex,user.getSex());
        userWrapper.lambda().set(SysUserEntity::getEMail,user.getEMail());
        userWrapper.lambda().set(SysUserEntity::getBrief,user.getBrief());
        userWrapper.lambda().set(SysUserEntity::getSysUpdateTime,DateUtils.getCurrentDateTime());
        int count = sysUserMapper.update(null,userWrapper);
        if(count == 0){
            responseDTO.setResponseCode(ResponseEnum.FAIL,"更新用户信息失败");
        }
        responseDTO.setResponseCode(ResponseEnum.SUCCESS,"更新用户信息成功");
        return responseDTO;
    }

    /**
     * <p>@Description 查询用户信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/8 19:43 </p>
     * @param userId 用户ID
     * @return com.www.myblog.common.pojo.ResponseDTO<com.www.myblog.admin.data.dto.SysUserDTO>
     */
    @Override
    public ResponseDTO<SysUserDTO> findUser(String userId) {
        ResponseDTO<SysUserDTO> responseDTO = new ResponseDTO<>();
        SysUserEntity userEntity = sysUserService.findUserEntityById(userId);
        if(userEntity != null){
            SysUserDTO userDTO = new SysUserDTO();
            BeanUtils.copyProperties(userEntity,userDTO);
            userDTO.setBirthday(DateUtils.format(userEntity.getBirthday(), DateUtils.DateFormatEnum.YYYY_MM_DD));
            userDTO.setPassWord(null);
            responseDTO.setResponseCode(ResponseEnum.SUCCESS,userDTO);
        }else {
            responseDTO.setResponseCode(ResponseEnum.FAIL,null);
        }
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
        if(user == null || StringUtils.isAnyBlank(user.getUserId(),user.getUserName(),user.getPassWord(),user.getRoleName())
            || (StringUtils.isNotBlank(user.getSex()) && !StringUtils.containsAny(user.getSex(),CommonEnum.SEX_1.getCode(),CommonEnum.SEX_0.getCode()))){
            responseDTO.setResponseCode(ResponseEnum.FAIL,"信息不完整，创建用户失败");
            return responseDTO;
        }
        SysRoleEntity roleEntity = sysRoleService.findRoleEntityByName(user.getRoleName());
        if(roleEntity == null){
            responseDTO.setResponseCode(ResponseEnum.FAIL);
            responseDTO.setMsg("用户角色错误，创建用户失败");
            return responseDTO;
        }
        //创建用户
        user.setPassWord(new BCryptPasswordEncoder().encode(user.getPassWord()));
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setUserId(user.getUserId());
        userEntity.setUserName(user.getUserName());
        userEntity.setPassWord(user.getPassWord());
        userEntity.setSex(user.getSex());
        userEntity.setPhoneNum(user.getPhoneNum());
        userEntity.setBirthday(DateUtils.parse(user.getBirthday(), DateUtils.DateFormatEnum.YYYY_MM_DD));
        userEntity.setEMail(user.getEMail());
        userEntity.setSysCreateTime(DateUtils.getCurrentDateTime());
        userEntity.setSysUpdateTime(DateUtils.getCurrentDateTime());
        sysUserMapper.insert(userEntity);
        //创建用户角色
        SysUserRoleEntity userRoleEntity = new SysUserRoleEntity();
        userRoleEntity.setUserId(userEntity.getUserId());
        userRoleEntity.setRoleId(roleEntity.getRoleId());
        userRoleEntity.setSysCreateTime(DateUtils.getCurrentDateTime());
        userRoleEntity.setSysUpdateTime(DateUtils.getCurrentDateTime());
        sysUserRoleMapper.insert(userRoleEntity);
        responseDTO.setResponseCode(ResponseEnum.SUCCESS,"创建用户成功");
        return responseDTO;
    }
    /**
     * <p>@Description 查询所有角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 12:53 </p>
     * @return com.www.myblog.common.pojo.ResponseDTO<java.util.List < com.www.myblog.admin.data.dto.SysUserRoleDTO>>
     */
    @Override
    public ResponseDTO<List<SysRoleDTO>> findAllRole() {
        List<SysRoleDTO> list = sysRoleMapper.findAllRole();
        return new ResponseDTO<>(ResponseEnum.SUCCESS,list);
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
        if(!StringUtils.containsAny(stateCd, CommonEnum.STATE_CD_1.getCode(), CommonEnum.STATE_CD_2.getCode(), CommonEnum.STATE_CD_3.getCode())
                || !StringUtils.containsAny(notExpired, CommonEnum.YES_1.getCode(), CommonEnum.NO_0.getCode())
                || !StringUtils.containsAny(notLocked, CommonEnum.YES_1.getCode(), CommonEnum.NO_0.getCode())
                || !StringUtils.containsAny(credentialsNotExpired, CommonEnum.YES_1.getCode(), CommonEnum.NO_0.getCode())){
            responseDTO.setResponseCode(ResponseEnum.FAIL,"更新用户信息失败");
            return responseDTO;
        }
        UpdateWrapper<SysUserEntity> userWrapper = new UpdateWrapper<>();
        userWrapper.lambda().eq(SysUserEntity::getUserId,userId);
        userWrapper.lambda().set(SysUserEntity::getStateCd,stateCd);
        userWrapper.lambda().set(SysUserEntity::getNotExpired,notExpired);
        userWrapper.lambda().set(SysUserEntity::getNotLocked,notLocked);
        userWrapper.lambda().set(SysUserEntity::getCredentialsNotExpired,credentialsNotExpired);
        userWrapper.lambda().set(SysUserEntity::getSysUpdateTime,DateUtils.getCurrentDateTime());
        int count = sysUserMapper.update(null,userWrapper);
        if(count != 0){
            responseDTO.setResponseCode(ResponseEnum.SUCCESS,"更新用户信息成功");
        }else {
            responseDTO.setResponseCode(ResponseEnum.FAIL,"更新用户信息失败");
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
     * @return com.www.myblog.common.pojo.ResponseDTO<java.util.List < com.www.myblog.admin.data.dto.SysUserDTO>>
     */
    @Override
    public ResponseDTO<List<SysUserEntity>> findAllUser(String stateCd, String userId,String userName, int pageNum, int pageSize) {
        QueryWrapper<SysUserEntity> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(stateCd)){
            wrapper.lambda().eq(SysUserEntity::getStateCd,stateCd);
        }
        if(StringUtils.isNotBlank(userId)){
            wrapper.lambda().eq(SysUserEntity::getUserId,userId);
        }
        if(StringUtils.isNotBlank(userName)){
            wrapper.lambda().eq(SysUserEntity::getUserName,userName);
        }
        Page<SysUserEntity> page = new Page<>(pageNum,pageSize);
        page = sysUserMapper.selectPage(page,wrapper);
        List<SysUserEntity> userList =  page.getRecords();
        ResponseDTO<List<SysUserEntity>> responseDTO = new ResponseDTO<>(ResponseEnum.SUCCESS,userList);
        responseDTO.setPageNum(pageNum);
        responseDTO.setPageSize(pageSize);
        responseDTO.setTotalNum(page.getTotal());
        return responseDTO;
    }

    /**
     * <p>@Description 查询用户拥有的角色信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/24 21:45 </p>
     * @param userId 用户ID
     * @return java.util.List<com.www.myblog.admin.data.entity.SysRoleEntity> 角色信息
     */
    @Override
    public List<SysRoleEntity> findUserRole(String userId) {
        if(StringUtils.isBlank(userId)){
            return null;
        }
        return sysUserMapper.findUserRole(userId);
    }
}
