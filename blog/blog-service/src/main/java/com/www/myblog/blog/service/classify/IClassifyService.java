package com.www.myblog.blog.service.classify;

import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.myblog.blog.data.dto.ClassificationDTO;

import java.util.List;

/**
 * <p>@Description 博客分类service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/22 19:10 </p>
 */
public interface IClassifyService {
    /**
     * <p>@Description 查询所有博客分类 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 19:07 </p>
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.ClassificationDTO>>
     */
    ResponseDTO<List<ClassificationDTO>> findAllBlogClass();
}
