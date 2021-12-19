package com.www.authorise.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;

/**
 * <p>
 * @Description oauth2认证服务配置
 * oauth2默认的URL连接有：
 * 1、授权链接：/oauth/authorize
 * 2、生成令牌链接：/oauth/token
 * 3、用户确认授权提交链接：/oauth/confirm_access
 * 4、授权服务错误信息链接：/oauth/error
 * 5、用于资源服务访问的令牌进行解析的链接：/oauth/check_token
 * 6、使用jwt令牌需要用到提供公共密钥的链接：/oauth/token_key
 * 链接包含的url参数key有：
 * client_id : 客户端ID
 * client_secret : 客户端密钥
 * grant_type : 认证范围：authorization_code、password、client_credentials、implicit
 * username : 用户名
 * password : 用户密码
 * </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/12/18 12:10 </p>
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
    private static Logger LOG = LoggerFactory.getLogger(AuthorizationConfig.class);
    @Resource
    private DataSource dataSource;
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;
    @Autowired
    private AuthenticationManager authenticationManager; //密码模式必须配置
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * <p>@Description 配置客户端
     * 认证范围：
     * client_credentials 授权申请地址：/oauth/token?client_id=客户端ID &client_secret= 客户端密钥 &grant_type=client_credentials
     * password 授权申请地址：/oauth/token?client_id=客户端ID &client_secret= 客户端密钥 &grant_type=client_credentials &username=用户名 &password=用户密码
     * implicit 授权申请地址：/oauth/authorize?client_id=客户端ID &response_type=token&redirect_uri=回调地址
     * authorization_code 授权申请地址：/oauth/authorize?client_id=客户端ID &response_type=code&redirect_uri=回调地址
     * refresh_token 刷新令牌地址：/oauth/token?client_id=客户端ID &client_secret= 客户端密钥 &grant_type=refresh_token&refresh_token=需要刷新的令牌
     * </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/18 12:11 </p>
     * @param clients
     * @return void
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        LOG.info("=====> 配置客户端");
        clients.withClientDetails(clientDetails());
    }
    /**
     * <p>@Description 配置令牌端点的安全约束 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/18 12:45 </p>
     * @param security
     * @return void
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        LOG.info("=====> 配置令牌端点的安全约束");
        security.tokenKeyAccess("permitAll()")//oauth/token_key设置公开
                .checkTokenAccess("permitAll()")//oauth/check_token设置公开
                .allowFormAuthenticationForClients();//允许表单认证，申请令牌
    }
    /**
     * <p>@Description 配置令牌端点 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/18 12:36 </p>
     * @param endpoints
     * @return void
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        LOG.info("=====> 配置令牌端点");
        endpoints.authenticationManager(authenticationManager)//密码模式需要的管理器
                .userDetailsService(userDetailsService)//密码模式的用户信息管理
                .authorizationCodeServices(authorizationCodeServices)//授权码需要的服务
                .tokenServices(tokenServices())//令牌管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }
    /**
     * <p>@Description 配置数据库方式读取client信息 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/19 15:31 </p>
     * @return org.springframework.security.oauth2.provider.ClientDetailsService
     */
    @Bean
    public ClientDetailsService clientDetails() {
        JdbcClientDetailsService clientService = new JdbcClientDetailsService(dataSource);
        clientService.setPasswordEncoder(passwordEncoder);
        return clientService;
    }
    /**
     * <p>@Description 注册token服务 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/19 12:49 </p>
     * @return org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetails());//客户端详情服务
        services.setSupportRefreshToken(true);//允许令牌自动刷新
        services.setTokenStore(tokenStore);//令牌存储策略
        //使用数据库读取client信息后，有效时间以数据库的为准
//        services.setAccessTokenValiditySeconds(tokenValidityMinutes*60);//令牌默认有效时间（秒）
//        services.setRefreshTokenValiditySeconds(refreshValidityDays*24*60*60);//刷新令牌默认有效时间（秒）
        //令牌增强,设置使用jwt令牌
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter));
        services.setTokenEnhancer(tokenEnhancerChain);
        return services;
    }
    /**
     * <p>@Description 设置授权码模式的授权码存储方式</p>
     * <p>@Author www </p>
     * <p>@Date 2021/12/18 12:43 </p>
     * @return org.springframework.security.oauth2.provider.code.AuthorizationCodeServices
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        //数据库存储授权码
        return new JdbcAuthorizationCodeServices(dataSource);
    }
}
