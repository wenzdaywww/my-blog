package com.www.myblog.admin.test;

import com.www.myblog.admin.AdminApplication;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p>@Description 测试类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/11/18 21:24 </p>
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = AdminApplication.class)
public class ApplicationTest {
//    @Autowired
    StringEncryptor encryptor;
    //加密
//    @Test
    public void getPass(){
        String name = encryptor.encrypt("www362412");
        System.out.println("加密结果："+name);//解密
    }
//    @Test
    public void passDecrypt(){
        String username = encryptor.decrypt("7uBC9fVLpL05ipEPzgsDT6Qcjuq9HvDYc0VUIgP4hY=");
        System.out.println("解密结果："+username);
    }

    @Test
    public void test(){
        //加密工具
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        //加密配置
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
        //自己在用的时候更改此密码
        config.setPassword("wenzday");
        //应用配置
        encryptor.setConfig(config);
        String plaintext="www362412";
        //加密
        String ciphertext=encryptor.encrypt(plaintext);
        System.out.println(plaintext + " : " + ciphertext);
    }
}
