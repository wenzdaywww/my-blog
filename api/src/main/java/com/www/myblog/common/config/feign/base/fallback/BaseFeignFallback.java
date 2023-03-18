package com.www.myblog.common.config.feign.base.fallback;

import com.www.common.data.enums.ResponseEnum;
import com.www.common.data.response.Response;
import com.www.myblog.common.config.feign.base.IBaseFeignService;
import com.www.myblog.common.dto.UserInfoDTO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>@Description base应用的服务降级处理 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/21 19:47 </p>
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "com.www.myblog.feign",name = "base")
public class BaseFeignFallback implements FallbackFactory<IBaseFeignService> {
    /**
     * <p>@Description 服务降级处理 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/21 19:53 </p>
     * @param throwable
     * @return IBlogFeignService
     */
    @Override
    public IBaseFeignService create(Throwable throwable) {
        log.error("base服务降级,失败原因:",throwable);
        return new IBaseFeignService() {
            /**
             * <p>@Description 查询多个用户信息 </p>
             * <p>@Author www </p>
             * <p>@Date 2022/1/23 15:43 </p>
             * @param userList 用户id集合
             * @return Response<UserInfoDTO>
             */
            @Override
            public Response<List<UserInfoDTO>> findUserInfoList(List<String> userList) {
                log.error("base服务降级：查询多个用户信息");
                List<UserInfoDTO> list = new ArrayList<>();
                return new Response<>(ResponseEnum.SUCCESS,list);
            }

            /**
             * <p>@Description 校验用户是否存在 </p>
             * <p>@Author www </p>
             * <p>@Date 2022/1/23 15:43 </p>
             * @param userList 用户id集合
             * @return Response<Boolean>
             */
            @Override
            public Response<Boolean> validateUserExist(List<String> userList) {
                log.error("base服务降级：校验用户是否存在");
                return new Response<>(ResponseEnum.SUCCESS,false);
            }
            /**
             * <p>@Description 查询用户信息 </p>
             * <p>@Author www </p>
             * <p>@Date 2022/1/23 15:43 </p>
             * @param userId 用户id
             * @return Response<UserInfoDTO>
             */
            @Override
            public Response<UserInfoDTO> findUserInfo(String userId) {
                log.error("base服务降级：查询用户信息");
                UserInfoDTO userInfoDTO = new UserInfoDTO();
                userInfoDTO.setUserId(userId);
                return new Response<>(ResponseEnum.SUCCESS,userInfoDTO);
            }
        };
    }
}
