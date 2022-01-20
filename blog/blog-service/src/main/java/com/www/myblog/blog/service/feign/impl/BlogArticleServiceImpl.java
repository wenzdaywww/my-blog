package com.www.myblog.blog.service.feign.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.www.common.config.code.CodeDict;
import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.common.pojo.enums.CodeKeyEnum;
import com.www.common.pojo.enums.CodeTypeEnum;
import com.www.myblog.blog.data.entity.BlogArticleEntity;
import com.www.myblog.blog.data.mapper.BlogArticleMapper;
import com.www.myblog.blog.service.feign.IBlogArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>@Description 对外博客文章服务 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/20 21:18 </p>
 */
@Service
public class BlogArticleServiceImpl implements IBlogArticleService {
    @Autowired
    private BlogArticleMapper blogArticleMapper;

    /**
     * <p>@Description 查询用户的博客数量 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/20 21:16 </p>
     * @param userId 用户ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.lang.Integer>
     */
    @Override
    public ResponseDTO<Integer> findUserBlogNum(String userId) {
        ResponseDTO<Integer> response = new ResponseDTO<>();
        if(StringUtils.isBlank(userId)){
            response.setResponseCode(ResponseDTO.RespEnum.SUCCESS,0);
            return response;
        }
        QueryWrapper<BlogArticleEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BlogArticleEntity::getUserId,userId);
        wrapper.lambda().eq(BlogArticleEntity::getStateCd, CodeDict.getValue(CodeTypeEnum.BLOG_STATUS, CodeKeyEnum.K1));
        int count = blogArticleMapper.selectCount(wrapper);
        response.setResponseCode(ResponseDTO.RespEnum.SUCCESS,count);
        return response;
    }
}
