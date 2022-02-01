package com.www.myblog.blog.service.tag.impl;

import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.myblog.blog.data.dto.TagInfoDTO;
import com.www.myblog.blog.data.mapper.TagInfoMapper;
import com.www.myblog.blog.service.tag.ITagInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>@Description 标签信息Service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/22 19:10 </p>
 */
@Slf4j
@Service
public class TagInformationServiceImpl implements ITagInformationService {
    @Autowired
    private TagInfoMapper tagInfoMapper;

    /**
     * <p>@Description 查询所有博客标签 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 19:07 </p>
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.ClassificationDTO>>
     */
    @Override
    public ResponseDTO<List<TagInfoDTO>> findAllBlogTag() {
        List<TagInfoDTO> list = tagInfoMapper.findAllBlogTag();
        return new ResponseDTO<>(ResponseDTO.RespEnum.SUCCESS,list);
    }
}
