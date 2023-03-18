package com.www.myblog.blog.controller.edit;

import com.www.common.config.oauth2.constant.AuthorityContant;
import com.www.common.config.oauth2.token.JwtTokenConverter;
import com.www.common.data.response.Response;
import com.www.myblog.blog.data.dto.BlogGroupDTO;
import com.www.myblog.blog.data.dto.TagInfoDTO;
import com.www.myblog.blog.service.edit.IEditBlogService;
import com.www.myblog.common.dto.BlogArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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


    /**
     * <p>@Description 修改博客的分组及标签信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/2 14:54 </p>
     * @param blog 博客信息
     * @return com.www.common.data.dto.response.Response<com.www.myblog.common.dto.BlogArticleDTO>
     */
    @PostMapping("newtg")
    public Response<Boolean> updateBlogTagAndGroup(BlogArticleDTO blog){
        return editBlogService.updateBlogTagAndGroup(blog);
    }
    /**
     * <p>@Description 查询博客的分组及标签信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/2 14:54 </p>
     * @param blogId 博客id
     * @return com.www.common.data.dto.response.Response<com.www.myblog.common.dto.BlogArticleDTO>
     */
    @GetMapping("btg/{bid}")
    public Response<BlogArticleDTO> findBlogTagAndGroup(@PathVariable("bid") Long blogId){
        return editBlogService.findBlogTagAndGroup(blogId);
    }
    /**
     * <p>@Description 获取用户博客标签列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @return com.www.common.data.dto.response.Response<java.util.List < com.www.myblog.blog.data.dto.TagInfoDTO>>
     */
    @GetMapping("tags")
    public Response<List<TagInfoDTO>> findUserBlogTag(){
        return editBlogService.findUserBlogTag(jwtTokenConverter.getUserId());
    }
    /**
     * <p>@Description 获取博客列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param query 查询条件
     * @return com.www.common.data.dto.response.Response<java.util.List < com.www.myblog.common.dto.BlogArticleDTO>>
     */
    @PostMapping("blogs")
    public Response<List<BlogArticleDTO>> findBlogList(BlogArticleDTO query){
        if(query != null){
            query.setUserId(jwtTokenConverter.getUserId());
        }
        return editBlogService.findBlogList(query);
    }
    /**
     * <p>@Description 查询当前登录的用户的博客分组列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 18:28 </p>
     * @return com.www.common.data.dto.response.Response<java.util.List < com.www.myblog.blog.data.dto.BlogGroupDTO>>
     */
    @PostMapping("group")
    public Response<List<BlogGroupDTO>> findBlogGroup(){
        return editBlogService.findBlogGroup(jwtTokenConverter.getUserId());
    }
    /**
     * <p>@Description 查询当前登录的用户的博客分组列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 18:28 </p>
     * @return com.www.common.data.dto.response.Response<String>
     */
    @PostMapping("new-group")
    public Response<String> createBlogGroup(String name){
        return editBlogService.createBlogGroup(jwtTokenConverter.getUserId(),name);
    }
    /**
     * <p>@Description 当前登录的用户创建博客文章 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 18:28 </p>
     * @return com.www.common.data.dto.response.Response<String> 博客文章主键
     */
    @PostMapping("new")
    public Response<Long> createBlogArticle(BlogArticleDTO blog,MultipartFile img){
        if(blog != null){
            blog.setUserId(jwtTokenConverter.getUserId());
        }
        return editBlogService.createBlogArticle(blog,img);
    }
    /**
     * <p>@Description 查询所有博客分类 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 19:07 </p>
     * @return com.www.common.data.dto.response.Response<java.util.List < com.www.myblog.blog.data.dto.ClassificationDTO>>
     */
    @PostMapping("tag")
    public Response<List<TagInfoDTO>> findAllBlogClass(){
        return editBlogService.findAllBlogTag();
    }
}
