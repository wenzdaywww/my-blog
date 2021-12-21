package com.www.authorise.controller;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
/**
 * <p>@Description 自定义授权页面controller </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/21 20:42 </p>
 */
@Slf4j
@Controller
@SessionAttributes("authorizationRequest") //必须配置
public class ConfirmController {
    /**
     * <p>@Description 重写授权页面跳转 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/21 20:42 </p>
     * @param model
     * @param request
     * @return org.springframework.web.servlet.ModelAndView
     */
    @RequestMapping("/oauth/confirm_access")
    public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
        ModelAndView view = new ModelAndView();
        view.setViewName("confirm"); //自定义页面名字，resources\templates\authorize.html
        view.addObject("clientId", authorizationRequest.getClientId());
        view.addObject("scopes",authorizationRequest.getScope());
        return view;
    }
}

