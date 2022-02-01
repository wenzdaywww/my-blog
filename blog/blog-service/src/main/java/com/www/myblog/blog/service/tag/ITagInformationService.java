package com.www.myblog.blog.service.tag;

import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.myblog.blog.data.dto.TagInfoDTO;

import java.util.List;

/**
 * <p>@Description 博客标签service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/22 19:10 </p>
 */
public interface ITagInformationService {
    /**
     * <p>@Description 查询所有博客标签 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 19:07 </p>
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.TagInfoDTO>>
     */
    ResponseDTO<List<TagInfoDTO>> findAllBlogTag();
}
