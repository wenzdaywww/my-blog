package com.www.myblog.blog.service.user;

import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.myblog.blog.data.dto.AuthorDTO;
import com.www.myblog.blog.data.dto.BlogArticleDTO;
import com.www.myblog.blog.data.dto.CollectGroupDTO;
import com.www.myblog.blog.data.dto.CommentDTO;

import java.util.List;

/**
 * <p>@Description 当前登录用户博客信息service接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/23 18:21 </p>
 */
public interface IUserBlogService {
    /**
     * <p>@Description 点赞或取消点赞 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 22:24 </p>
     * @param userId 用户id
     * @param blogId 博客id
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.lang.Boolean> true点赞，fasle取消点赞
     */
    ResponseDTO<Boolean> savePraiseInfo(String userId,Long blogId);
    /**
     * <p>@Description 修改博客收藏夹位置 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 10:45 </p>
     * @param userId 用户id
     * @param blogId 博客id
     * @param cgId 收藏夹id
     * @return com.www.common.pojo.dto.response.ResponseDTO<Boolean> true修改成功，false修改失败
     */
    ResponseDTO<Boolean> updateCollectId(String userId,Long blogId,Long cgId);
    /**
     * <p>@Description 查询用户的博客收藏列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 19:08 </p>
     * @param query 查询条件就
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.BlogArticleDTO>> 博客收藏列表
     */
    ResponseDTO<List<BlogArticleDTO>> findCollectList(CollectGroupDTO query);
    /**
     * <p>@Description 查询收藏夹列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 13:29 </p>
     * @param userId 用户id
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.lang.Boolean> true添加成功，false取消失败
     */
    ResponseDTO<List<CollectGroupDTO>> findCollectGroup(String userId);
    /**
     * <p>@Description 新增收藏夹 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/3 13:31 </p>
     * @param userId 用户id
     * @param collectName 收藏夹名称
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.lang.Boolean> true添加成功，false取消失败
     */
    ResponseDTO<Boolean> addCollectGroup(String userId,String collectName);
    /**
     * <p>@Description  获取粉丝列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/2 22:54 </p>
     * @param pageNum 页码
     * @param userId 用户id
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.AuthorDTO>> 粉丝用户列表
     */
    ResponseDTO<List<AuthorDTO>> findFansList(int pageNum,String userId);
    /**
     * <p>@Description  获取关注列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/2 22:54 </p>
     * @param pageNum 页码
     * @param userId 用户id
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.AuthorDTO>> 关注用户列表
     */
    ResponseDTO<List<AuthorDTO>> findFollowList(int pageNum,String userId);
    /**
     * <p>@Description 添加/取消博客收藏 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/1 10:45 </p>
     * @param userId 用户id
     * @param blogId 博客id
     * @param cgId 收藏夹id
     * @return com.www.common.pojo.dto.response.ResponseDTO<BlogArticleDTO> true添加收藏，false取消收藏
     */
    ResponseDTO<BlogArticleDTO> addCollect(String userId,Long blogId,Long cgId);
    /**
     * <p>@Description 新增评论 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/30 21:13 </p>
     * @param userId 用户ID
     * @param blogId 博客ID，不等于null，则是新增的评论
     * @param replyComId 回复的评论ID，不等于null，则是回复评论
     * @param text 评论内容
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.myblog.blog.data.dto.CommentDTO>
     */
    ResponseDTO<CommentDTO> addBlogComment(String userId,Long blogId,Long replyComId, String text);
    /**
     * <p>@Description 获取用户博客相关统计信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 18:23 </p>
     * @param userId 用户ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.myblog.blog.data.dto.AuthorDTO>
     */
    ResponseDTO<AuthorDTO> findUserCount(String userId);
    /**
     * <p>@Description 关注或取消关注博主 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/29 15:16 </p>
     * @param userId 当前登录的用户ID
     * @param authorId 博主ID
     * @param blogId 博客ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.lang.Boolean>
     */
    ResponseDTO<Boolean> followAuthor(String userId,String authorId,Long blogId);
}
