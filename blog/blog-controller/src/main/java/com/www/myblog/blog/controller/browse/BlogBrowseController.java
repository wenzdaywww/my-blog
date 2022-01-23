package com.www.myblog.blog.controller.browse;

import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.myblog.blog.data.dto.AuthorDTO;
import com.www.myblog.blog.data.dto.BlogArticleDTO;
import com.www.myblog.blog.service.browse.IBlogBrowseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>@Description 匿名用户博客浏览controller </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/23 15:10 </p>
 */
@RestController
@RequestMapping("browse")
public class BlogBrowseController {
    @Autowired
    private IBlogBrowseService blogBrowseService;

    /**
     * <p>@Description 查询博主信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 15:14 </p>
     * @param userId 博主ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.myblog.blog.data.dto.AuthorDTO>
     */
    @GetMapping("author/{id}")
    public ResponseDTO<AuthorDTO> findAuthorInfo(@PathVariable("id") String userId){
        return blogBrowseService.findAuthorInfo(userId);
    }
    /**
     * <p>@Description 获取热门博客前10名单 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 19:24 </p>
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.myblog.blog.data.dto.BlogArticleDTO>
     */
    @GetMapping("hot-rank")
    public ResponseDTO<List<BlogArticleDTO>> findHotBlogRank(){
        return blogBrowseService.findHotBlogRank();
    }
}
