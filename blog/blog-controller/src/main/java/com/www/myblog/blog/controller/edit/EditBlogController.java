package com.www.myblog.blog.controller.edit;

import com.www.common.config.oauth2.token.JwtTokenConverter;
import com.www.common.pojo.constant.AuthorityContant;
import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.myblog.blog.data.dto.BlogArticleDTO;
import com.www.myblog.blog.data.dto.BlogGroupDTO;
import com.www.myblog.blog.data.dto.TagInfoDTO;
import com.www.myblog.blog.service.tag.ITagInfoService;
import com.www.myblog.blog.service.edit.IEditBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>@Description 博客编辑controller </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/22 17:48 </p>
 */
@RestController
@RequestMapping("edit")
@PreAuthorize(AuthorityContant.USER)
public class EditBlogController {
    @Autowired
    private JwtTokenConverter jwtTokenConverter;
    @Autowired
    private IEditBlogService editBlogService;
    @Autowired
    private ITagInfoService tagInfoService;

    /**
     * <p>@Description 查询当前登录的用户的博客分组列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 18:28 </p>
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.BlogGroupDTO>>
     */
    @PostMapping("group")
    public ResponseDTO<List<BlogGroupDTO>> findBlogGroup(){
        return editBlogService.findBlogGroup(jwtTokenConverter.getUserId());
    }
    /**
     * <p>@Description 查询当前登录的用户的博客分组列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 18:28 </p>
     * @return com.www.common.pojo.dto.response.ResponseDTO<String>
     */
    @PostMapping("new-group")
    public ResponseDTO<String> createBlogGroup(String name){
        return editBlogService.createBlogGroup(jwtTokenConverter.getUserId(),name);
    }
    /**
     * <p>@Description 当前登录的用户创建博客文章 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 18:28 </p>
     * @return com.www.common.pojo.dto.response.ResponseDTO<String> 博客文章主键
     */
    @PostMapping("new")
    public ResponseDTO<Long> createBlogArticle(BlogArticleDTO blogText){
        if(blogText != null){
            blogText.setUserId(jwtTokenConverter.getUserId());
        }
        return editBlogService.createBlogArticle(blogText);
    }
    /**
     * <p>@Description 查询所有博客分类 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 19:07 </p>
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.ClassificationDTO>>
     */
    @PostMapping("tag")
    public ResponseDTO<List<TagInfoDTO>> findAllBlogClass(){
        return tagInfoService.findAllBlogTag();
    }
}
