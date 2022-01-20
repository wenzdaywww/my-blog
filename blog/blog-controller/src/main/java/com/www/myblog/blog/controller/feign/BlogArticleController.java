package com.www.myblog.blog.controller.feign;

import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.myblog.blog.service.feign.IBlogArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>@Description 博客文章对外服务接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/20 21:13 </p>
 */
@RestController
@RequestMapping("/feign")
public class BlogArticleController {
    @Autowired
    private IBlogArticleService blogArticleService;

    /**
     * <p>@Description 查询用户的博客数量 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/20 21:16 </p>
     * @param userId 用户ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.lang.Integer>
     */
    @GetMapping("blogs")
    public ResponseDTO<Integer> findUserBlogNum(String userId){
        return blogArticleService.findUserBlogNum(userId);
    }
}
