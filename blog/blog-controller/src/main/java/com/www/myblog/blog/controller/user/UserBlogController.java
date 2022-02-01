package com.www.myblog.blog.controller.user;

import com.www.common.config.oauth2.token.JwtTokenConverter;
import com.www.common.pojo.constant.AuthorityContant;
import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.myblog.blog.data.dto.AuthorDTO;
import com.www.myblog.blog.data.dto.CommentDTO;
import com.www.myblog.blog.data.dto.TagInfoDTO;
import com.www.myblog.blog.service.user.IUserBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>@Description 当前登录用户博客信息controller </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/23 18:14 </p>
 */
@RestController
@RequestMapping("user")
@PreAuthorize(AuthorityContant.ALL)
public class UserBlogController {
    @Autowired
    private JwtTokenConverter jwtTokenConverter;
    @Autowired
    private IUserBlogService userBlogService;

    /**
     * <p>@Description 博客添加收藏 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 10:44 </p>
     * @param bid
     * @return com.www.common.pojo.dto.response.ResponseDTO<Boolean> true添加收藏，false取消收藏
     */
    @PostMapping("collect/{bid}")
    public ResponseDTO<Boolean> addCollect(@PathVariable("bid") Long bid){
        return userBlogService.addCollect(jwtTokenConverter.getUserId(),bid);
    }
    /**
     * <p>@Description 新增评论 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/30 21:12 </p>
     * @param bid 博客ID，不等于null，则是新增的评论
     * @param rid 回复的评论ID，不等于null，则是回复评论
     * @param text 评论内容
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.myblog.blog.data.dto.CommentDTO>
     */
    @PostMapping("comment")
    public ResponseDTO<CommentDTO> addBlogComment(Long bid,Long rid,String text){
        return userBlogService.addBlogComment(jwtTokenConverter.getUserId(),bid,rid,text);
    }
    /**
     * <p>@Description 获取用户博客相关统计信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 18:18 </p>
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.myblog.blog.data.dto.AuthorDTO>
     */
    @GetMapping("count")
    public ResponseDTO<AuthorDTO> findUserCount(){
        return userBlogService.findUserCount(jwtTokenConverter.getUserId());
    }
    /**
     * <p>@Description 关注或取消关注博主 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/29 15:15 </p>
     * @param id 博主ID
     * @param bid 博客ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.lang.Boolean>
     */
    @GetMapping("follow")
    public ResponseDTO<Boolean> followAuthor(String id,Long bid){
        return userBlogService.followAuthor(jwtTokenConverter.getUserId(),id,bid);
    };
}
