package com.www.myblog.blog.controller.user;

import com.www.common.config.oauth2.token.JwtTokenConverter;
import com.www.common.pojo.constant.AuthorityContant;
import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.myblog.blog.data.dto.AuthorDTO;
import com.www.myblog.blog.data.dto.BlogArticleDTO;
import com.www.myblog.blog.data.dto.CollectGroupDTO;
import com.www.myblog.blog.data.dto.CommentDTO;
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
     * <p>@Description 修改博客收藏夹位置 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 10:44 </p>
     * @param bid 博客id
     * @param cgid 收藏夹id
     * @return com.www.common.pojo.dto.response.ResponseDTO<Boolean> true添加收藏，false取消收藏
     */
    @PostMapping("newclt")
    public ResponseDTO<Boolean> updateCollectId(Long bid,Long cgid){
        return userBlogService.updateCollectId(jwtTokenConverter.getUserId(),bid,cgid);
    }
    /**
     * <p>@Description 获取博客列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param query 查询条件
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.BlogArticleDTO>>
     */
    @GetMapping("collects")
    public ResponseDTO<List<BlogArticleDTO>> findCollectList(CollectGroupDTO query){
        if(query != null){
            query.setUserId(jwtTokenConverter.getUserId());
        }
        return userBlogService.findCollectList(query);
    }
    /**
     * <p>@Description 查询收藏夹列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 13:29 </p>
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.lang.Boolean> true添加成功，false取消失败
     */
    @GetMapping("cltgp")
    public ResponseDTO<List<CollectGroupDTO>> findCollectGroup(){
        return userBlogService.findCollectGroup(jwtTokenConverter.getUserId());
    }
    /**
     * <p>@Description 新增收藏夹 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 13:29 </p>
     * @param name 收藏夹名称
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.lang.Boolean> true添加成功，false取消失败
     */
    @PostMapping("newgp")
    public ResponseDTO<Boolean> addCollectGroup(String name){
        return userBlogService.addCollectGroup(jwtTokenConverter.getUserId(),name);
    }
    /**
     * <p>@Description 获取粉丝列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/2 22:53 </p>
     * @param pageNum 页码
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.AuthorDTO>>
     */
    @GetMapping("fans/{num}")
    public ResponseDTO<List<AuthorDTO>> findFansList(@PathVariable("num") int pageNum){
        return userBlogService.findFansList(pageNum,jwtTokenConverter.getUserId());
    }
    /**
     * <p>@Description 获取关注列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/2 22:53 </p>
     * @param pageNum 页码
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.AuthorDTO>>
     */
    @GetMapping("follows/{num}")
    public ResponseDTO<List<AuthorDTO>> findFollowList(@PathVariable("num") int pageNum){
        return userBlogService.findFollowList(pageNum,jwtTokenConverter.getUserId());
    }
    /**
     * <p>@Description 博客添加收藏 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 10:44 </p>
     * @param bid 博客id
     * @param cgid 收藏夹id
     * @return com.www.common.pojo.dto.response.ResponseDTO<BlogArticleDTO>
     */
    @PostMapping("collect")
    public ResponseDTO<BlogArticleDTO> addCollect(Long bid,Long cgid){
        return userBlogService.addCollect(jwtTokenConverter.getUserId(),bid,cgid);
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
    @PostMapping("follow")
    public ResponseDTO<Boolean> followAuthor(String id,Long bid){
        return userBlogService.followAuthor(jwtTokenConverter.getUserId(),id,bid);
    };
}
