package com.www.common.config.mvc.upload;

import com.www.common.pojo.dto.response.ResponseDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>@Description 文件上传Service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/5 22:37 </p>
 */
public interface IFileUpload {
    /**
     * <p>@Description 上传文件 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 15:03 </p>
     * @param file 文件
     * @param fileName 保存的文件名，不含文件格式
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    ResponseDTO<String> uploadFile(MultipartFile file,String fileName);
    /**
     * <p>@Description 上传文件 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 15:03 </p>
     * @param file 文件
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    ResponseDTO<String> uploadFile(MultipartFile file);
    /**
     * <p>@Description 上传文件并返回地址 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 15:03 </p>
     * @param file 文件
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    String uploadFileBackPath(MultipartFile file);
    /**
     * <p>@Description 上传文件并返回地址 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 15:03 </p>
     * @param file 文件
     * @param fileName 保存的文件名，不含文件格式
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    String uploadFileBackPath(MultipartFile file,String fileName);
}
