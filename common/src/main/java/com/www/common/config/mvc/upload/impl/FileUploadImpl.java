package com.www.common.config.mvc.upload.impl;

import com.www.common.config.mvc.upload.IFileUpload;
import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * <p>@Description 文件上传Service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/5 22:38 </p>
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "com.www.common.file",name = "enable")
public class FileUploadImpl implements IFileUpload {
    /** 图片类型  **/
    private String[] imgType = {"BMP","JPG","JPEG","PNG","GIF"};
    /** 图片访问路径 **/
    @Value("${com.www.common.file.imgUrlPath}")
    private String imgUrlPath;
    /** 图片保存的绝对路径 **/
    @Value("${com.www.common.file.imgSavePath}")
    private String imgSavePath;
    /** 图片外其他文件访问路径 **/
    @Value("${com.www.common.file.otherUrlPath}")
    private String otherUrlPath;
    /** 图片外其他文件保存的绝对路径 **/
    @Value("${com.www.common.file.otherSavePath}")
    private String otherSavePath;

    /**
     * <p>@Description 构造方法 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/1 18:20 </p>
     * @return
     */
    public FileUploadImpl(){
        log.info("配置文件上传");
    }
    /**
     * <p>@Description 上传文件 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 15:03 </p>
     * @param file   文件
     * @param fileName 保存的文件名，不含文件格式
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @Override
    public ResponseDTO<String> uploadFile(MultipartFile file, String fileName) {
        String path = this.uploadFileBackPath(file,fileName);
        if(path == null){
            return new ResponseDTO<>(ResponseDTO.RespEnum.FAIL,"上传文件失败");
        }
        return new ResponseDTO<>(ResponseDTO.RespEnum.SUCCESS,path);
    }

    /**
     * <p>@Description 上传文件 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 15:03 </p>
     * @param file 文件
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @Override
    public ResponseDTO<String> uploadFile(MultipartFile file) {
        return this.uploadFile(file,null);
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
        return uploadFileBackPath(file,null);
    }

    /**
     * <p>@Description 上传文件并返回地址 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 15:03 </p>
     * @param file 文件
     * @param fileName 保存的文件名，不含文件格式
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @Override
    public String uploadFileBackPath(MultipartFile file, String fileName){
        return this.saveFile(file,fileName);
    }
    /**
     * <p>@Description 保存上传的文件 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/10 22:28 </p>
     * @param file 文件
     * @param fileName 保存的文件名，不含文件格式
     * @return java.lang.String 返回文件的相对路径
     */
    private String saveFile(MultipartFile file, String fileName){
        if(file == null){
            return null;
        }
        //获取原始文件名称(包含格式)
        String origFileFullName = file.getOriginalFilename();
        //获取文件名称（不包含格式）
        String orgFileName = origFileFullName.substring(0, origFileFullName.lastIndexOf("."));
        //获取文件类型，以最后一个`.`为标识
        String fileType = origFileFullName.substring(origFileFullName.lastIndexOf(".") + 1);
        String urlPath = "";
        String savePath = "";
        //上传的文件为图片
        if(Arrays.asList(imgType).contains(StringUtils.upperCase(fileType))){
            urlPath = imgUrlPath;
            savePath = imgSavePath;
        }else {
            urlPath = otherUrlPath;
            savePath = otherSavePath + DateUtils.format(DateUtils.getCurrentDateTime(), DateUtils.DateFormatEnum.YYYYMMDD);
        }
        //判断文件夹是否存在，不存在则创建
        File filePath = new File(savePath);
        if (!filePath.exists() && !filePath.isDirectory()) {
            filePath.mkdirs();
        }
        if(StringUtils.isBlank(fileName)){
            //设置文件新名称: 当前时间+文件名称（不包含格式）
            String date = DateUtils.format(DateUtils.getCurrentDateTime(), DateUtils.DateFormatEnum.YYYYMMDDHHMMSSSSS);
            fileName = date + "-" + orgFileName + "." + fileType;
        }else {
            fileName += "." + fileType;
        }
        //在指定路径下创建一个文件
        File targetFile = new File(savePath, fileName);
        try {
            //将文件保存到服务器指定位置
            file.transferTo(targetFile);
            log.info("上传成功,文件的保存路径：{},原始文件名称：{},文件类型：{},新文件名称：{}", imgSavePath,origFileFullName,fileType,fileName);
            //将文件在服务器的存储路径返回
        } catch (IOException e) {
            log.info("上传失败,失败信息：{}",e.getMessage());
            return null;
        }
        return urlPath.replace("**",fileName);
    }
}