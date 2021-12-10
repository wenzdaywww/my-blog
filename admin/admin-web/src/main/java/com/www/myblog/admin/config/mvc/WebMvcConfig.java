package com.www.myblog.admin.config.mvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>@Description MVC配置 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/10 20:48 </p>
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /** 图片访问路径 **/
    @Value("${file.imgUrlPath}")
    private String imgUrlPath;
    /** 图片保存的绝对路径 **/
    @Value("${file.imgSavePath}")
    private String imgSavePath;
    /** 图片外其他文件访问路径 **/
    @Value("${file.otherUrlPath}")
    private String otherUrlPath;
    /** 图片外其他文件保存的绝对路径 **/
    @Value("${file.otherSavePath}")
    private String otherSavePath;
    /**
     * <p>@Description 资源拦截配置 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/10 22:23 </p>
     * @param registry
     * @return void
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置图片访问的相对路径
        registry.addResourceHandler(imgUrlPath).addResourceLocations("file:"+imgSavePath);
        //配置图片外其他文件访问的相对路径
        registry.addResourceHandler(otherUrlPath).addResourceLocations("file:"+otherSavePath);
    }
}
