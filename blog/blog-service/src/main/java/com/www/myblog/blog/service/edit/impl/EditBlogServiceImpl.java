package com.www.myblog.blog.service.edit.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.www.common.config.code.CodeDict;
import com.www.common.config.code.enums.CodeKeyEnum;
import com.www.common.config.mvc.upload.IFileUpload;
import com.www.common.data.dto.response.ResponseDTO;
import com.www.common.data.enums.ResponseEnum;
import com.www.common.utils.DateUtils;
import com.www.myblog.blog.data.dto.BlogGroupDTO;
import com.www.myblog.blog.data.dto.TagInfoDTO;
import com.www.myblog.blog.data.entity.BlogArticleEntity;
import com.www.myblog.blog.data.entity.BlogGroupEntity;
import com.www.myblog.blog.data.entity.BlogTagEntity;
import com.www.myblog.blog.data.entity.GroupInfoEntity;
import com.www.myblog.blog.data.entity.TagInfoEntity;
import com.www.myblog.blog.data.enums.CodeTypeEnum;
import com.www.myblog.blog.data.mapper.BlogArticleMapper;
import com.www.myblog.blog.data.mapper.BlogTagMapper;
import com.www.myblog.blog.data.mapper.GroupInfoMapper;
import com.www.myblog.blog.data.mapper.TagInfoMapper;
import com.www.myblog.blog.service.edit.IEditBlogService;
import com.www.myblog.blog.service.entity.IBlogArticleService;
import com.www.myblog.blog.service.entity.IBlogGroupService;
import com.www.myblog.blog.service.entity.IBlogTagService;
import com.www.myblog.blog.service.entity.IGroupInfoService;
import com.www.myblog.blog.service.entity.ITagInfoService;
import com.www.myblog.common.dto.BlogArticleDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>@Description 博客编辑service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/22 18:29 </p>
 */
@Service
public class EditBlogServiceImpl implements IEditBlogService {
    @Autowired
    private GroupInfoMapper groupInfoMapper;
    @Autowired
    private IBlogTagService blogTagService;
    @Autowired
    private IBlogGroupService blogGroupService;
    @Autowired
    private IFileUpload fileService;
    @Autowired
    private IBlogArticleService blogArticleService;
    @Autowired
    private IGroupInfoService groupInfoService;
    @Autowired
    private ITagInfoService tagInfoService;
    @Autowired
    private BlogArticleMapper blogArticleMapper;
    @Autowired
    private BlogTagMapper blogTagMapper;
    @Autowired
    private TagInfoMapper tagInfoMapper;

    /**
     * <p>@Description 修改博客的分组及标签信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/2 14:54 </p>
     * @param blog 博客信息
     * @return com.www.common.data.dto.response.ResponseDTO<com.www.myblog.common.dto.BlogArticleDTO>
     */
    @Override
    public ResponseDTO<Boolean> updateBlogTagAndGroup(BlogArticleDTO blog) {
        ResponseDTO<Boolean> response = new ResponseDTO<>();
        if(blog == null || blog.getBlogId() == null){
            response.setResponse(ResponseEnum.FAIL,"修改博客的分组及标签信息失败，信息不全",null);
            return response;
        }
        BlogArticleEntity articleEntity = blogArticleService.findById(blog.getBlogId());
        if(articleEntity == null){
            response.setResponse(ResponseEnum.FAIL,"查询博客的分组及标签信息失败，博客不存在",null);
            return response;
        }
        //修改分组信息
        BlogGroupEntity blogGroupEntity = blogGroupService.findEntityByBlogId(blog.getBlogId());
        if(blog.getGroupId() == null){//分组id为空，则取消分组
            if(blogGroupEntity != null){
                blogGroupService.deleteEntity(blogGroupEntity.getBgId());
            }
        }else {
            GroupInfoEntity groupInfoEntity = groupInfoService.findById(blog.getGroupId());
            if(groupInfoEntity == null){
                response.setResponse(ResponseEnum.FAIL,"查询博客的分组及标签信息失败，分组不存在",null);
                return response;
            }
            if(blogGroupEntity != null){
                //存在分组，判断是否是同一分组，是则不更新，否则删除旧分组，新建新分组
                if(!groupInfoEntity.getGroupId().equals(blogGroupEntity.getGroupId())){
                    blogGroupService.deleteEntity(blogGroupEntity.getBgId());
                    BlogGroupEntity newBlogGroupEntity = new BlogGroupEntity();
                    newBlogGroupEntity.setBlogId(articleEntity.getBlogId()).setUserId(articleEntity.getUserId())
                            .setGroupId(groupInfoEntity.getGroupId()).setCreateTime(DateUtils.getCurrentDateTime()).setUpdateTime(DateUtils.getCurrentDateTime());
                    blogGroupService.createEntity(newBlogGroupEntity);
                }
            }else {
                //不存在分组，则创建分组
                blogGroupEntity = new BlogGroupEntity();
                blogGroupEntity.setBlogId(articleEntity.getBlogId()).setUserId(articleEntity.getUserId())
                        .setGroupId(groupInfoEntity.getGroupId()).setCreateTime(DateUtils.getCurrentDateTime()).setUpdateTime(DateUtils.getCurrentDateTime());
                blogGroupService.createEntity(blogGroupEntity);
            }
        }
        //修改标签信息
        List<BlogTagEntity> blogTagList = blogTagService.findEntityByBlogId(blog.getBlogId());
        //标签有值，则修改标签
        if(CollectionUtils.isNotEmpty(blog.getTagIds())){
            List<TagInfoEntity> tagInfoList = tagInfoService.findByIds(blog.getTagIds());
            if(CollectionUtils.isEmpty(tagInfoList) || tagInfoList.size() != blog.getTagIds().size()){
                response.setResponse(ResponseEnum.FAIL,"查询博客的分组及标签信息失败，标签不存在",null);
                return response;
            }
            List<BlogTagEntity> addTagList = new ArrayList<>();//需要新增的博客标签
            List<Long> deeteTagList = new ArrayList<>();//需要删除的博客标签
            //博客标签的map集合
            Map<Long, BlogTagEntity> blogTagMap = CollectionUtils.isEmpty(blogTagList) ? new HashMap<>() :
                    blogTagList.stream().collect(Collectors.toMap(BlogTagEntity::getTagId, Function.identity(), (key1, key2) -> key2));
            //判断博客标签是否有新加的tagId，不存在则需要新增
            for (TagInfoEntity tagInfoEntity : tagInfoList){
                if(!blogTagMap.containsKey(tagInfoEntity.getTagId())){
                    BlogTagEntity addEntity= new BlogTagEntity();
                    addEntity.setBlogId(articleEntity.getBlogId()).setTagId(tagInfoEntity.getTagId()).setUserId(articleEntity.getUserId())
                            .setUpdateTime(DateUtils.getCurrentDateTime()).setCreateTime(DateUtils.getCurrentDateTime());
                    addTagList.add(addEntity);
                }
            }
            //判断博客标签是否有多余的tagId，多余则需要删除
            if(CollectionUtils.isNotEmpty(blogTagList)){
                //标签信息的map集合
                Map<Long, TagInfoEntity> tagInfoMap = CollectionUtils.isEmpty(tagInfoList) ? new HashMap<>() :
                        tagInfoList.stream().collect(Collectors.toMap(TagInfoEntity::getTagId, Function.identity(), (key1, key2) -> key2));
                for (BlogTagEntity blogTagEntity : blogTagList){
                    if(!tagInfoMap.containsKey(blogTagEntity.getTagId())){
                        deeteTagList.add(blogTagEntity.getBtId());
                    }
                }
            }
            //删除多余的博客标签
            blogTagService.deleteById(deeteTagList);
            //新增博客标签
            blogTagService.createEntityBatch(addTagList);
        }else {
            //删除所有标签
            if(CollectionUtils.isNotEmpty(blogTagList)){
                blogTagService.deleteByBlogId(blog.getBlogId());
            }
        }
        response.setResponse(ResponseEnum.SUCCESS,true);
        return response;
    }
    /**
     * <p>@Description 查询博客的分组及标签信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/2/2 14:54 </p>
     *
     * @param blogId 博客id
     * @return com.www.common.data.dto.response.ResponseDTO<com.www.myblog.common.dto.BlogArticleDTO>
     */
    @Override
    public ResponseDTO<BlogArticleDTO> findBlogTagAndGroup(Long blogId) {
        ResponseDTO<BlogArticleDTO> response = new ResponseDTO<>();
        if(blogId == null){
            response.setResponse(ResponseEnum.FAIL,"查询博客的分组及标签信息失败，信息不全",null);
            return response;
        }
        BlogArticleEntity articleEntity = blogArticleService.findById(blogId);
        if(articleEntity == null){
            response.setResponse(ResponseEnum.FAIL,"查询博客的分组及标签信息失败，博客不存在",null);
            return response;
        }
        BlogArticleDTO articleDTO = new BlogArticleDTO();
        articleDTO.setBlogId(blogId).setTitle(articleEntity.getTitle());
        //查询分组信息
        BlogGroupEntity groupEntity = blogGroupService.findEntityByBlogId(blogId);
        articleDTO.setGroupId(groupEntity != null ? groupEntity.getGroupId() : null);
        //查询标签信息
        List<BlogTagEntity> tagList = blogTagService.findEntityByBlogId(blogId);
        List<Long> tagIds = CollectionUtils.isEmpty(tagList) ? null :
                tagList.stream().map(BlogTagEntity::getTagId).collect(Collectors.toList());
        articleDTO.setTagIds(tagIds);
        response.setResponse(ResponseEnum.SUCCESS,articleDTO);
        return response;
    }
    /**
     * <p>@Description 查询所有博客标签 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 19:07 </p>
     * @return com.www.common.data.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.ClassificationDTO>>
     */
    @Override
    public ResponseDTO<List<TagInfoDTO>> findAllBlogTag() {
        List<TagInfoDTO> list = tagInfoMapper.findAllBlogTag();
        return new ResponseDTO<>(ResponseEnum.SUCCESS,list);
    }
    /**
     * <p>@Description 获取用户博客标签列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 19:07 </p>
     * @param userId 用户ID
     * @return com.www.common.data.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.TagInfoDTO>>
     */
    @Override
    public ResponseDTO<List<TagInfoDTO>> findUserBlogTag(String userId) {
        ResponseDTO<List<TagInfoDTO>> response = new ResponseDTO<>();
        if(StringUtils.isBlank(userId)){
            response.setResponse(ResponseEnum.FAIL,"获取用户博客标签列表失败。信息不全",null);
            return response;
        }
        List<TagInfoDTO> list = blogTagMapper.findAuthorBlogTag(userId);
        response.setResponse(ResponseEnum.SUCCESS,list);
        return response;
    }
    /**
     * <p>@Description 获取博客列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param query 查询条件
     * @return com.www.common.data.dto.response.ResponseDTO<java.util.List < com.www.myblog.common.dto.BlogArticleDTO>>
     */
    @Override
    public ResponseDTO<List<BlogArticleDTO>> findBlogList(BlogArticleDTO query) {
        ResponseDTO<List<BlogArticleDTO>> response = new ResponseDTO<>();
        if(query == null || StringUtils.isBlank(query.getUserId())){
            response.setResponse(ResponseEnum.FAIL,"获取博客列表失败，信息不全",null);
            return response;
        }
        int pageNum = query.getPageNum() <= 0 ? 1 : query.getPageNum();
        long pageSize = query.getPageSize() <= 0 ? 5 : query.getPageSize();
        Page<BlogArticleDTO> page = new Page<>(pageNum,pageSize);
        page = blogArticleMapper.findUserBlogList(page,query);
        List<BlogArticleDTO> blogList =  page.getRecords();
        response.setPageNum(query.getPageNum());
        response.setPageSize(query.getPageSize());
        response.setTotalNum(page.getTotal());
        response.setResponse(ResponseEnum.SUCCESS,blogList);
        return response;
    }
    /**
     * <p>@Description 当前登录的用户创建博客文章 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 18:31 </p>
     * @param blog 博客信息
     * @param img 博客封面图片
     * @return com.www.common.data.dto.response.ResponseDTO<Long> 博客文章主键
     */
    @Override
    public ResponseDTO<Long> createBlogArticle(BlogArticleDTO blog, MultipartFile img) {
        ResponseDTO<Long> response = new ResponseDTO<>();
        if(blog == null || StringUtils.isAnyBlank(blog.getUserId(),blog.getTitle(),blog.getContent())){
            response.setResponse(ResponseEnum.FAIL,"发布博客失败，信息不完整",null);
            return response;
        }
        if(blog.getGroupId() != null){
            GroupInfoEntity groupEntity = groupInfoService.findById(blog.getGroupId());
            if(groupEntity == null){
                response.setResponse(ResponseEnum.FAIL,"发布博客失败，分组信息不存在",null);
                return response;
            }
        }
        if(CollectionUtils.isNotEmpty(blog.getTagIds())){
            //根据标签id集合查询标签信息
            List<TagInfoEntity> tagList = tagInfoService.findByIds(blog.getTagIds());
            if(CollectionUtils.isEmpty(tagList) || tagList.size() != blog.getTagIds().size()){
                response.setResponse(ResponseEnum.FAIL,"发布博客失败，分类信息不存在",null);
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
        blogEntity.setCollect(0L);
        blogEntity.setStateCd(CodeDict.getValue(CodeTypeEnum.BLOG_STATUS.getCodeType(), CodeKeyEnum.K1.getKey()));
        blogEntity.setUpdateTime(DateUtils.getCurrentDateTime());
        blogEntity.setCreateTime(DateUtils.getCurrentDateTime());
        blogArticleService.createEntity(blogEntity);
        //保存封面图片
        if(img != null){
            String path = fileService.uploadFileBackURL(img,"blogCover","coverImg_" + blogEntity.getBlogId());
            if(StringUtils.isNotBlank(path)){
                //更新博客封面图片
                UpdateWrapper<BlogArticleEntity> wrapper = new UpdateWrapper<>();
                wrapper.lambda().eq(BlogArticleEntity::getBlogId,blogEntity.getBlogId());
                wrapper.lambda().set(BlogArticleEntity::getCoverImg,path);
                blogArticleService.updateEntity(wrapper);
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
            blogGroupService.createEntity(blogGroupEntity);
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
                blogTagService.createEntity(blogClassEntity);
            }
        }
        response.setResponse(ResponseEnum.SUCCESS,"发布博客成功",blogEntity.getBlogId());
        return response;
    }

    /**
     * <p>@Description 当前登录用户新增分组信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 18:31 </p>
     * @param userId 用户ID
     * @param name 分组名称
     * @return com.www.common.data.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.BlogGroupDTO>>
     */
    @Override
    public ResponseDTO<String> createBlogGroup(String userId, String name) {
        ResponseDTO<String> response = new ResponseDTO<>();
        if(StringUtils.isAnyBlank(userId,name)){
            response.setCode(ResponseEnum.FAIL.getCode());
            response.setMsg("新增分组失败，用户ID或分组名称为空");
            return response;
        }
        GroupInfoEntity groupEntity = new GroupInfoEntity();
        groupEntity.setUserId(userId);
        groupEntity.setGroupName(name);
        groupEntity.setUpdateTime(DateUtils.getCurrentDateTime());
        groupEntity.setCreateTime(DateUtils.getCurrentDateTime());
        groupInfoService.createEntity(groupEntity);
        response.setResponse(ResponseEnum.SUCCESS,"新增分组成功");
        return response;
    }

    /**
     * <p>@Description 查询用户的博客分组列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/22 18:31 </p>
     * @param userId 用户ID
     * @return com.www.common.data.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.BlogGroupDTO>>
     */
    @Override
    public ResponseDTO<List<BlogGroupDTO>> findBlogGroup(String userId) {
        ResponseDTO<List<BlogGroupDTO>> response = new ResponseDTO<>();
        if(StringUtils.isBlank(userId)){
            return response;
        }
        List<BlogGroupDTO> list = groupInfoMapper.findBlogGroup(userId);
        response.setResponse(ResponseEnum.SUCCESS,list);
        return response;
    }
}
