package com.www.myblog.blog.service.browse.impl;

import com.www.common.feign.base.IBaseFeignService;
import com.www.common.pojo.dto.feign.UserInfoDTO;
import com.www.common.pojo.dto.response.ResponseDTO;
import com.www.common.utils.DateUtils;
import com.www.myblog.blog.data.dto.AuthorDTO;
import com.www.myblog.blog.data.dto.BlogArticleDTO;
import com.www.myblog.blog.data.mapper.BlogArticleMapper;
import com.www.myblog.blog.service.browse.IBlogBrowseService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
