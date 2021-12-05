package com.www.myblog.admin.controller.common;

import com.www.myblog.common.pojo.ResponseDTO;
import com.www.myblog.common.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>@Description 文件上传Controller </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/5 22:34 </p>
 */
@RestController
@RequestMapping("common")
public class FileController {
    @Autowired
    private IFileService fileService;

    /**
     * <p>@Description 上传用户头像 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/4 15:03 </p>
     * @param photo 头像
     * @return com.www.myblog.common.pojo.ResponseDTO<java.lang.String>
     */
    @PostMapping(value = "/up")
    public ResponseDTO<String> uploadPhoto(MultipartFile photo){
        return fileService.uploadFile(photo);
    }
}
