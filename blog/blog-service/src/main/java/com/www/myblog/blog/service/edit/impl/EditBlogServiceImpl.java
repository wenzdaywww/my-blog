package com.www.myblog.blog.service.edit.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.www.common.config.code.CodeDict;
import com.www.common.config.mvc.upload.IFileUpload;
import com.www.common.pojo.constant.CharConstant;
import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.common.pojo.enums.CodeKeyEnum;
import com.www.common.pojo.enums.CodeTypeEnum;
import com.www.common.utils.DateUtils;
import com.www.myblog.blog.data.dto.BlogArticleDTO;
import com.www.myblog.blog.data.dto.BlogGroupDTO;
import com.www.myblog.blog.data.entity.*;
import com.www.myblog.blog.data.mapper.*;
import com.www.myblog.blog.service.edit.IEditBlogService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>@Description 博客编辑service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/22 18:29 </p>
 */
@Service
public class EditBlogServiceImpl implements IEditBlogService {
    @Autowired
    private GroupInfoMapper blogGroupMapper;
    @Autowired
    private BlogArticleMapper blogArticleMapper;
    @Autowired
    private BlogTagMapper blogClassMapper;
    @Autowired
    private TagInfoMapper classificationMapper;
    @Autowired
    private BlogGroupMapper userBlogGroupMapper;
    @Autowired
    private IFileUpload fileService;

    /**
     * <p>@Description 当前登录的用户创建博客文章 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 18:31 </p>
     * @param blog 博客信息
     * @param img 博客封面图片
     * @return com.www.common.pojo.dto.response.ResponseDTO<Long> 博客文章主键
     */
    @Override
    public ResponseDTO<Long> createBlogArticle(BlogArticleDTO blog, MultipartFile img) {
        ResponseDTO<Long> response = new ResponseDTO<>();
        if(blog == null || StringUtils.isAnyBlank(blog.getUserId(),blog.getTitle(),blog.getContent())){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"发布博客失败，信息不完整",null);
            return response;
        }
        if(blog.getGroupId() != null){
            GroupInfoEntity groupEntity = blogGroupMapper.selectById(blog.getGroupId());
            if(groupEntity == null){
                response.setResponse(ResponseDTO.RespEnum.FAIL,"发布博客失败，分组信息不存在",null);
                return response;
            }
        }
        if(CollectionUtils.isNotEmpty(blog.getTagIds())){
            QueryWrapper<TagInfoEntity> wrapper = new QueryWrapper<>();
            wrapper.lambda().in(TagInfoEntity::getTagId,blog.getTagIds());
            List<TagInfoEntity> classList = classificationMapper.selectList(wrapper);
            if(CollectionUtils.isEmpty(classList) || classList.size() != blog.getTagIds().size()){
                response.setResponse(ResponseDTO.RespEnum.FAIL,"发布博客失败，分类信息不存在",null);
                return response;
            }
        }
        //创建博客
        BlogArticleEntity blogEntity = new BlogArticleEntity();
        blogEntity.setUserId(blog.getUserId());
        blogEntity.setTitle(blog.getTitle());
        blogEntity.setContent(blog.getContent());
        blogEntity.setSummary(blog.getSummary());
        blogEntity.setPraise(0L);
        blogEntity.setComment(0L);
        blogEntity.setBrowse(0L);
        blogEntity.setStateCd(CodeDict.getValue(CodeTypeEnum.BLOG_STATUS.getCodeType(), CodeKeyEnum.K1.getKey()));
        blogEntity.setUpdateTime(DateUtils.getCurrentDateTime());
        blogEntity.setCreateTime(DateUtils.getCurrentDateTime());
        blogArticleMapper.insert(blogEntity);
        //保存封面图片
        if(img != null){
            String path = fileService.uploadFileBackURL(img,"blogCover","coverImg_" + blogEntity.getBlogId());
            if(StringUtils.isNotBlank(path)){
                //更新博客封面图片
                blogEntity.setCoverImg(path);
                blogArticleMapper.updateById(blogEntity);
            }
        }
        //创建博客分组
        if(blog.getGroupId() != null){
            BlogGroupEntity blogGroupEntity = new BlogGroupEntity();
            blogGroupEntity.setGroupId(blog.getGroupId());
            blogGroupEntity.setUserId(blog.getUserId());
            blogGroupEntity.setBlogId(blogEntity.getBlogId());
            blogGroupEntity.setUpdateTime(DateUtils.getCurrentDateTime());
            blogGroupEntity.setCreateTime(DateUtils.getCurrentDateTime());
            userBlogGroupMapper.insert(blogGroupEntity);
        }
        //创建博客分类
        if(CollectionUtils.isNotEmpty(blog.getTagIds())){
            for (Long tagId : blog.getTagIds()){
                BlogTagEntity blogClassEntity = new BlogTagEntity();
                blogClassEntity.setBlogId(blogEntity.getBlogId());
                blogClassEntity.setUserId(blog.getUserId());
                blogClassEntity.setTagId(tagId);
                blogClassEntity.setUpdateTime(DateUtils.getCurrentDateTime());
                blogClassEntity.setCreateTime(DateUtils.getCurrentDateTime());
                blogClassMapper.insert(blogClassEntity);
            }
        }
        response.setResponse(ResponseDTO.RespEnum.SUCCESS,"发布博客成功",blogEntity.getBlogId());
        return response;
    }

    /**
     * <p>@Description 当前登录用户新增分组信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 18:31 </p>
     * @param userId 用户ID
     * @param name 分组名称
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.BlogGroupDTO>>
     */
    @Override
    public ResponseDTO<String> createBlogGroup(String userId, String name) {
        ResponseDTO<String> response = new ResponseDTO<>();
        if(StringUtils.isAnyBlank(userId,name)){
            response.setCode(ResponseDTO.RespEnum.FAIL.getCode());
            response.setMsg("新增分组失败，用户ID或分组名称为空");
            return response;
        }
        GroupInfoEntity groupEntity = new GroupInfoEntity();
        groupEntity.setUserId(userId);
        groupEntity.setGroupName(name);
        groupEntity.setUpdateTime(DateUtils.getCurrentDateTime());
        groupEntity.setCreateTime(DateUtils.getCurrentDateTime());
        blogGroupMapper.insert(groupEntity);
        response.setResponse(ResponseDTO.RespEnum.SUCCESS,"新增分组成功");
        return response;
    }

    /**
     * <p>@Description 查询用户的博客分组列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 18:31 </p>
     * @param userId 用户ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.BlogGroupDTO>>
     */
    @Override
    public ResponseDTO<List<BlogGroupDTO>> findBlogGroup(String userId) {
        ResponseDTO<List<BlogGroupDTO>> response = new ResponseDTO<>();
        if(StringUtils.isBlank(userId)){
            return response;
        }
        List<BlogGroupDTO> list = blogGroupMapper.findBlogGroup(userId);
        response.setResponse(ResponseDTO.RespEnum.SUCCESS,list);
        return response;
    }
}
