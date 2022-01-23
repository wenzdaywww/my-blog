package com.www.common.feign.base.fallback;

import com.www.common.feign.base.IBaseFeignService;
import com.www.common.pojo.dto.feign.UserInfoDTO;
import com.www.common.pojo.dto.response.ResponseDTO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * <p>@Description base应用的服务降级处理 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2022/1/21 19:47 </p>
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "com.www.common.feign",name = {"base"})
public class BaseFeignFallback implements FallbackFactory<IBaseFeignService> {
    /**
     * <p>@Description 服务降级处理 </p>
     * <p>@Author www </p>
     * <p>@Date 2022/1/21 19:53 </p>
     * @param throwable
     * @return com.www.common.feign.IBlogFeignService
     */
    @Override
    public IBaseFeignService create(Throwable throwable) {
        log.error("base服务降级,失败原因:",throwable);
        return new IBaseFeignService() {
            /**
             * <p>@Description 查询用户信息 </p>
             * <p>@Author www </p>
             * <p>@Date 2022/1/23 15:43 </p>
             * @param userId 用户id
             * @return com.www.common.pojo.dto.response.ResponseDTO<com.www.common.pojo.dto.feign.UserInfoDTO>
             */
            @Override
            public ResponseDTO<UserInfoDTO> findUserInfo(String userId) {
                log.error("base服务降级：查询用户信息");
                UserInfoDTO userInfoDTO = new UserInfoDTO();
                userInfoDTO.setUserId(userId);
                return new ResponseDTO<>(ResponseDTO.RespEnum.SUCCESS,userInfoDTO);
            }
        };
    }
}
