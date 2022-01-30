package com.www.myblog.blog.service.user;

import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.myblog.blog.data.dto.AuthorDTO;
import com.www.myblog.blog.data.dto.CommentDTO;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p>@Description 当前登录用户博客信息service接口 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/23 18:21 </p>
 */
public interface IUserBlogService {
    /**
     * <p>@Description 新增评论 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/30 21:13 </p>
     * @param userId 用户ID
     * @param blogId 博客ID
     * @param text 评论内容
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.myblog.blog.data.dto.CommentDTO>
     */
    ResponseDTO<CommentDTO> addBlogComment(String userId,Long blogId, String text);
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
