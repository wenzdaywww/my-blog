package com.www.myblog.blog.controller.browse;

import com.www.common.config.oauth2.token.JwtTokenConverter;
import com.www.common.data.dto.response.ResponseDTO;
import com.www.myblog.blog.data.dto.AuthorDTO;
import com.www.myblog.blog.data.dto.BlogGroupDTO;
import com.www.myblog.blog.data.dto.CommentDTO;
import com.www.myblog.blog.data.dto.TagInfoDTO;
import com.www.myblog.blog.service.browse.IBlogBrowseService;
import com.www.myblog.common.dto.BlogArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private JwtTokenConverter jwtTokenConverter;
    @Autowired
    private IBlogBrowseService blogBrowseService;

    /**
     * <p>@Description 获取推荐博客列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param pageNum 页码
     * @return com.www.common.data.dto.response.ResponseDTO<java.util.List < com.www.myblog.common.dto.BlogArticleDTO>>
     */
    @PostMapping("tip-list")
    public ResponseDTO<List<BlogArticleDTO>> findTipBlogList(int pageNum){
        return blogBrowseService.findTipBlogList(pageNum);
    }
    /**
     * <p>@Description 查询评论列表，包括父评论和子评论 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/30 22:34 </p>
     * @param pageNum 页码
     * @param bid 博客id,不等于null，则是父评论
     * @param pid 父评论id，不等于null，则是子评论
     * @return com.www.common.data.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.CommentDTO>>
     */
    @GetMapping("cmt-list")
    public ResponseDTO<List<CommentDTO>> findCommentList(int pageNum,Long bid,Long pid){
        return blogBrowseService.findCommentList(pageNum,bid,pid);
    }
    /**
     * <p>@Description 根据博客ID查询博客信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/25 21:21 </p>
     * @param bid 博客ID
     * @param ip 访问IP
     * @return com.www.common.data.dto.response.ResponseDTO<com.www.myblog.common.dto.BlogArticleDTO>
     */
    @GetMapping("article")
    public ResponseDTO<BlogArticleDTO> findAriticle(Long bid,String ip){
        return blogBrowseService.findAriticle(jwtTokenConverter.getUserId(),bid,ip);
    }
    /**
     * <p>@Description 查询博主信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 15:14 </p>
     * @param id 博主ID
     * @param bid 博客ID
     * @return com.www.common.data.dto.response.ResponseDTO<com.www.myblog.blog.data.dto.AuthorDTO>
     */
    @GetMapping("author")
    public ResponseDTO<AuthorDTO> findAuthorInfo(String id,Long bid){
        return blogBrowseService.findAuthorInfo(jwtTokenConverter.getUserId(),id,bid);
    }
    /**
     * <p>@Description 获取热门博客前10名单 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 19:24 </p>
     * @return com.www.common.data.dto.response.ResponseDTO<com.www.myblog.common.dto.BlogArticleDTO>
     */
    @GetMapping("hot-rank")
    public ResponseDTO<List<BlogArticleDTO>> findHotBlogRank(){
        return blogBrowseService.findHotBlogRank();
    }
    /**
     * <p>@Description 获取博主博客列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param query 查询条件
     * @return com.www.common.data.dto.response.ResponseDTO<java.util.List < com.www.myblog.common.dto.BlogArticleDTO>>
     */
    @PostMapping("list")
    public ResponseDTO<List<BlogArticleDTO>> findAuthorBlogList(BlogArticleDTO query){
        return blogBrowseService.findAuthorBlogList(query);
    }
    /**
     * <p>@Description 获取博主博客分组列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param id 博主ID
     * @param bid 博客ID
     * @return com.www.common.data.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.BlogGroupDTO>>
     */
    @GetMapping("group")
    public ResponseDTO<List<BlogGroupDTO>> findAuthorBlogGroup(String id,Long bid){
        return blogBrowseService.findAuthorBlogGroup(id,bid);
    }
    /**
     * <p>@Description 获取博主博客标签列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param id 博主ID
     * @param bid 博客ID
     * @return com.www.common.data.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.TagInfoDTO>>
     */
    @GetMapping("tag")
    public ResponseDTO<List<TagInfoDTO>> findAuthorBlogTag(String id, Long bid){
        return blogBrowseService.findAuthorBlogTag(id,bid);
    }
}
