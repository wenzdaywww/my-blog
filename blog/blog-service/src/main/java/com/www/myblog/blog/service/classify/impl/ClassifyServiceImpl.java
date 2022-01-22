package com.www.myblog.blog.service.classify.impl;

import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.myblog.blog.data.dto.ClassificationDTO;
import com.www.myblog.blog.data.mapper.ClassificationMapper;
import com.www.myblog.blog.service.classify.IClassifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>@Description  </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/22 19:10 </p>
 */
@Slf4j
@Service
public class ClassifyServiceImpl implements IClassifyService {
    @Autowired
    private ClassificationMapper classificationMapper;

    /**
     * <p>@Description 查询所有博客分类 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 19:07 </p>
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.ClassificationDTO>>
     */
    @Override
    public ResponseDTO<List<ClassificationDTO>> findAllBlogClass() {
        List<ClassificationDTO> list = classificationMapper.findAllBlogClass();
        return new ResponseDTO<>(ResponseDTO.RespEnum.SUCCESS,list);
    }
}
