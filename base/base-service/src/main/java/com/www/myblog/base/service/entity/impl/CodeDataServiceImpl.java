package com.www.myblog.base.service.entity.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.myblog.base.data.entity.CodeDataEntity;
import com.www.myblog.base.data.mapper.CodeDataMapper;
import com.www.myblog.base.service.entity.ICodeDataService;
import org.springframework.stereotype.Service;

/**
 * <p>@Description 数据字典信息表的基本操作方法实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/8 22:11 </p>
 */
@Service
public class CodeDataServiceImpl extends ServiceImpl<CodeDataMapper, CodeDataEntity> implements ICodeDataService {
}
