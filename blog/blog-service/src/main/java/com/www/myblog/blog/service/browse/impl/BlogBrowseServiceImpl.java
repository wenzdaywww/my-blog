package com.www.myblog.blog.service.browse.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.www.common.feign.base.IBaseFeignService;
import com.www.common.pojo.dto.feign.UserInfoDTO;
import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.common.utils.DateUtils;
import com.www.myblog.blog.data.dto.AuthorDTO;
import com.www.myblog.blog.data.dto.BlogArticleDTO;
import com.www.myblog.blog.data.dto.BlogGroupDTO;
import com.www.myblog.blog.data.dto.ClassificationDTO;
import com.www.myblog.blog.data.mapper.BlogArticleMapper;
import com.www.myblog.blog.data.mapper.BlogClassMapper;
import com.www.myblog.blog.data.mapper.BlogGroupMapper;
import com.www.myblog.blog.data.mapper.UserBlogGroupMapper;
import com.www.myblog.blog.service.browse.IBlogBrowseService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * <p>@Description 匿名用户博客浏览Service </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/23 15:19 </p>
 */
@Service
public class BlogBrowseServiceImpl implements IBlogBrowseService {
    @Autowired
    private IBaseFeignService baseFeignService;
    @Autowired
    private BlogArticleMapper blogArticleMapper;
    @Autowired
    private UserBlogGroupMapper userBlogGroupMapper;
    @Autowired
    private BlogClassMapper blogClassMapper;


    /**
     * <p>@Description 获取博主博客分类列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param userId 博主ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.ClassificationDTO>>
     */
    @Override
    public ResponseDTO<List<ClassificationDTO>> findAuthorBlogClass(String userId) {
        ResponseDTO<List<ClassificationDTO>> response = new ResponseDTO<>();
        if(StringUtils.isBlank(userId)){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"获取博主博客分类列表，博主ID为空",null);
            return response;
        }
        List<ClassificationDTO> list = blogClassMapper.findAuthorBlogClass(userId);
        response.setResponse(ResponseDTO.RespEnum.SUCCESS,list);
        return response;
    }
    /**
     * <p>@Description 获取博主博客分组列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param userId 博主ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.BlogGroupDTO>>
     */
    @Override
    public ResponseDTO<List<BlogGroupDTO>> findAuthorBlogGroup(String userId) {
        ResponseDTO<List<BlogGroupDTO>> response = new ResponseDTO<>();
        if(StringUtils.isBlank(userId)){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"获取博主博客分组列表失败，博主ID为空",null);
            return response;
        }
        List<BlogGroupDTO> list = userBlogGroupMapper.findAuthorBlogGroup(userId);
        response.setResponse(ResponseDTO.RespEnum.SUCCESS,list);
        return response;
    }
    /**
     * <p>@Description 获取博主博客列表 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 21:37 </p>
     * @param queryDTO 查询条件
     * @return com.www.common.pojo.dto.response.ResponseDTO<java.util.List < com.www.myblog.blog.data.dto.BlogArticleDTO>>
     */
    @Override
    public ResponseDTO<List<BlogArticleDTO>> findAuthorBlogList(BlogArticleDTO queryDTO) {
        ResponseDTO<List<BlogArticleDTO>> response = new ResponseDTO<>();
        if(queryDTO == null || StringUtils.isBlank(queryDTO.getUserId())){
            response.setResponse(ResponseDTO.RespEnum.FAIL,"获取博主博客列表失败，博主ID为空",null);
            return response;
        }
        Page<BlogArticleDTO> page = new Page<>(queryDTO.getPageNum(),queryDTO.getPageSize());
        //TODO 2022/1/23 23:07 博客内容返回待处理，mapper暂注释
        page = blogArticleMapper.findAuthorBlogList(page,queryDTO);
        List<BlogArticleDTO> blogList =  page.getRecords();
        response.setPageNum(queryDTO.getPageNum());
        response.setPageSize(queryDTO.getPageSize());
        response.setTotalNum(page.getTotal());
        response.setResponse(ResponseDTO.RespEnum.SUCCESS,blogList);
        return response;
    }

    /**
     * <p>@Description 获取热门博客前10名单 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 19:24 </p>
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.myblog.blog.data.dto.BlogArticleDTO>
     */
    @Override
    public ResponseDTO<List<BlogArticleDTO>> findHotBlogRank() {
        List<BlogArticleDTO> list = blogArticleMapper.findHotBlogRank();
        if(CollectionUtils.isNotEmpty(list)){
            for (int i = 0; i < list.size(); i++){
                BlogArticleDTO dto = list.get(i);
                dto.setBlogTheme((i+1) + "、" + dto.getBlogTheme());
            }
        }
        return new ResponseDTO<>(ResponseDTO.RespEnum.SUCCESS,list);
    }

    /**
     * <p>@Description 查询博主信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/23 15:14 </p>
     * @param userId 博主ID
     * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.myblog.blog.data.dto.AuthorDTO>
     */
    @Override
    public ResponseDTO<AuthorDTO> findAuthorInfo(String userId) {
        ResponseDTO<AuthorDTO> responseDTO = new ResponseDTO<>();
        if(StringUtils.isBlank(userId)){
            responseDTO.setResponse(ResponseDTO.RespEnum.FAIL,"查询失败，博主ID为空",null);
            return responseDTO;
        }
        UserInfoDTO userInfoDTO = ResponseDTO.getBackData(baseFeignService.findUserInfo(userId));
        if(userInfoDTO == null){
            responseDTO.setResponse(ResponseDTO.RespEnum.FAIL,"查询失败，博主信息不存在",null);
            return responseDTO;
        }
        AuthorDTO authorDTO = blogArticleMapper.findAuthorCount(userId);
        authorDTO = authorDTO != null ? authorDTO : new AuthorDTO().setUserId(userInfoDTO.getUserId());
        authorDTO.setUserName(userInfoDTO.getUserName()).setPhoto(userInfoDTO.getPhoto());
        //计算码龄
        int month = DateUtils.getMonths(userInfoDTO.getCreateTime(),DateUtils.getCurrentDateTime());
        if(month != -1){
            authorDTO.setAge(month/12 + "年" + month%12 + "个月");
        }else {
            authorDTO.setAge("1个月");
        }
        responseDTO.setResponse(ResponseDTO.RespEnum.SUCCESS,authorDTO);
        return responseDTO;
    }
}
