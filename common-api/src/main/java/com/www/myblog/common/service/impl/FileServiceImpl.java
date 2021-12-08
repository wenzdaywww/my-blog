package com.www.myblog.common.service.impl;

import com.www.myblog.common.pojo.ResponseDTO;
import com.www.myblog.common.pojo.ResponseEnum;
import com.www.myblog.common.service.IFileService;
import com.www.myblog.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * <p>@Description 文件上传Service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/5 22:38 </p>
 */
@Service
public class FileServiceImpl implements IFileService {
    private static Logger LOG = LoggerFactory.getLogger(FileServiceImpl.class);
    @Value("${spring.servlet.multipart.location}")
    private String path;
    /**
     * <p>@Description 上传文件 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 15:03 </p>
     * @param file 文件
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @Override
    public ResponseDTO<String> uploadFile(MultipartFile file) {
        String path = this.uploadFileBackPath(file);
        return new ResponseDTO<>(ResponseEnum.SUCCESS,path);
    }

    /**
     * <p>@Description 上传文件并返回地址 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 15:03 </p>
     * @param file 文件
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @Override
    public String uploadFileBackPath(MultipartFile file) {
        File filePath = new File(path);
        LOG.info("文件的保存路径：{}", path);
        if (!filePath.exists() && !filePath.isDirectory()) {
            LOG.info("目录不存在，创建目录:{}",filePath);
            filePath.mkdir();
        }
        //获取原始文件名称(包含格式)
        String originalFileName = file.getOriginalFilename();
        LOG.info("原始文件名称：{}", originalFileName);
        //获取文件类型，以最后一个`.`为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        LOG.info("文件类型：{}", type);
        //获取文件名称（不包含格式）
        String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        //设置文件新名称: 当前时间+文件名称（不包含格式）
        String date = DateUtils.format(DateUtils.getCurrentDateTime(), DateUtils.DateFormatEnum.YYYYMMDDHHMMSSSSS);
        String fileName = date + "_" + name + "." + type;
        LOG.info("新文件名称：{}" , fileName);
        //在指定路径下创建一个文件
        File targetFile = new File(path, fileName);
        //将文件保存到服务器指定位置
        try {
            file.transferTo(targetFile);
            LOG.info("上传成功");
            //将文件在服务器的存储路径返回
        } catch (IOException e) {
            LOG.info("上传失败,失败信息：{}",e.getMessage());
        }
        return targetFile.getAbsolutePath();
    }
}
