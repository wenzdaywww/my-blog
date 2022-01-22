package com.www.myblog.blog.controller.classify;

import com.www.common.pojo.constant.AuthorityContant;
import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.myblog.blog.data.dto.ClassificationDTO;
import com.www.myblog.blog.service.classify.IClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>@Description 博客分类controller </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/22 19:02 </p>
 */
@RestController
@RequestMapping("class")
@PreAuthorize(AuthorityContant.ALL)
public class BlogClassController {
    @Autowired
    private IClassifyService classifyService;

    /**
     * <p>@Description 查询所有博客分类 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 19:07 </p>
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.ClassificationDTO>>
     */
    @PostMapping("all")
    public ResponseDTO<List<ClassificationDTO>> findAllBlogClass(){
        return classifyService.findAllBlogClass();
    }
}
