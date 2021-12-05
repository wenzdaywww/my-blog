package com.www.myblog.admin.controller.test;

import com.www.myblog.common.pojo.ResponseDTO;
import org.jasypt.encryption.StringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author www
 * @version 1.0
 * @description 测试首页
 * @date 2021/7/9 00:07
 */
/**
 * <p>@Description 测试 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/14 16:24 </p>
 */
@RestController
@RequestMapping("test")
public class TestController {
    private static Logger logger = LoggerFactory.getLogger(TestController.class);
    @Value("${server.port}")
    private String port;
    @Autowired
    private StringEncryptor stringEncryptor;
    /**
     * <p>@Description 测试方法 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/14 16:17 </p>
     * @return com.www.myblog.common.pojo.ResponseDTO
     */
    @GetMapping("/get/{name}")
    public ResponseDTO test(@PathVariable("name") String name){
        logger.info("访问成功！port={},name={}",port,name);
        return new ResponseDTO<>("访问成功！port=" + port + ", name=" + stringEncryptor.encrypt(name));
    }
    /**
     * <p>@Description 用户登入 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/14 14:38 </p>
     * @param userId 用户ID
     * @param password 密码
     * @return com.www.myblog.common.pojo.ResponseDTO<com.www.myblog.admin.data.entity.SysUserEntity>
     */
    @GetMapping("/find/{id}/{pwd}")
    public ResponseDTO login(@PathVariable("id") String userId, @PathVariable("pwd")String password){
        logger.info("访问成功！port={},name={}",port,userId);
        return new ResponseDTO<>("访问成功！port=" + port + ", name=" + stringEncryptor.encrypt(userId));
    }
}
