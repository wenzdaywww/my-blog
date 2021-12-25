package com.www.authorise.controller;
import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

/**
 * <p>@Description 自定义授权页面controller </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/21 20:42 </p>
 */
@Slf4j
@Controller
@SessionAttributes({ConfirmController.AUTHORIZATION_REQUEST_ATTR_NAME,ConfirmController.ORIGINAL_AUTHORIZATION_REQUEST_ATTR_NAME}) //必须配置
public class ConfirmController{
    /** 授权页面form提交的用户请求的所有scope的name **/
    static final String USER_SCOPE = "user_scope";
    /** 授权页面同意/拒绝的form提交的name **/
    static final String SELECT = "select";
    //重写请求需要的session参数
    static final String AUTHORIZATION_REQUEST_ATTR_NAME = "authorizationRequest";
    //重写请求需要的session参数
    static final String ORIGINAL_AUTHORIZATION_REQUEST_ATTR_NAME = "org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint.ORIGINAL_AUTHORIZATION_REQUEST";
    @Autowired
    private AuthorizationEndpoint authorizationEndpoint;
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
    /**
     * <p>@Description 重写/oauth/authorize请求 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/25 21:04 </p>
     * @param model 视图
     * @param parameters 请求参数
     * @param sessionStatus
     * @param principal
     * @return org.springframework.web.servlet.ModelAndView
     */
    @RequestMapping(value = "/oauth/authorize")
    public ModelAndView authorize(Map<String, Object> model, @RequestParam Map<String, String> parameters,SessionStatus sessionStatus, Principal principal){
        return authorizationEndpoint.authorize(model,parameters,sessionStatus,principal);
    }
    /**
     * <p>@Description 重写/oauth/authorize请求，授权页面确认时候跳转到该请求 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/25 21:04 </p>
     * @param approvalParameters
     * @param model
     * @param sessionStatus
     * @param principal
     * @return org.springframework.web.servlet.View
     */
    @RequestMapping(value = "/oauth/authorize", method = RequestMethod.POST, params = OAuth2Utils.USER_OAUTH_APPROVAL)
    public View approveOrDeny(@RequestParam Map<String, String> approvalParameters, Map<String, ?> model,SessionStatus sessionStatus, Principal principal){
        String scopes = approvalParameters.get(USER_SCOPE);
        //对多个scope统一由一对同意/拒绝控制，则需要对每个scope设置true或false
        if(StringUtils.isNotBlank(scopes)){
            scopes = scopes.replaceAll("\\[","").replaceAll("\\]","");
            String[] scopeArr = scopes.split(",");
            for (int i = 0;i < scopeArr.length;i++){
                approvalParameters.put(OAuth2Utils.SCOPE_PREFIX + scopeArr[i],approvalParameters.get(SELECT));
            }
        }
        return authorizationEndpoint.approveOrDeny(approvalParameters,model,sessionStatus,principal);
    }
}

