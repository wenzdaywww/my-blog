package com.www.myblog.base.service.entity.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.www.myblog.base.data.entity.SysMenuEntity;
import com.www.myblog.base.data.mapper.SysMenuMapper;
import com.www.myblog.base.service.entity.ISysMenuService;
import org.springframework.stereotype.Service;

/**
 * <p>@Description SYS_MENU表的基本操作方法实现类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/8 22:11 </p>
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements ISysMenuService {
}