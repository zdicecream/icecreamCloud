package com.zdinit.icecreamcloud.gateway.config;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zdinit.icecreamcloud.gateway.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


/**
 * webflux不能使用常规拦截器，需要使用过滤器，全局异常的处理与webMvc不一致
 *
 */
@Slf4j
@Configuration
public class SaTokenConfigure {
    @Autowired
    private ObjectMapper objectMapper;
    // 获取配置Bean (以代码的方式配置Sa-Token, 此配置会覆盖yml中的配置)
    @Primary
    @Bean(name="SaTokenConfigure")
    public SaTokenConfig getSaTokenConfig() {
        SaTokenConfig config = new SaTokenConfig();
        config.setTokenName("Access-Token");             // token名称 (同时也是cookie名称)
        config.setTimeout(30 * 60);       // token有效期，单位s 默认30天30 * 24 * 60 * 60
        config.setActivityTimeout(-1);              // token临时有效期 (指定时间内无操作就视为token过期) 单位: 秒
        config.setIsConcurrent(true);               // 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)
        config.setIsShare(true);                    // 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)
        config.setTokenStyle("uuid");               // token风格
        config.setIsLog(true);                     // 是否输出操作日志
        return config;
    }

    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 指定 [拦截路由]
                .addInclude("/**")    /* 拦截所有path */
                // 指定 [放行路由]
                .addExclude("/favicon.ico","/swagger-ui.html")
                // 指定[认证函数]: 每次请求执行
                .setAuth(obj -> {
                    // 登录校验 -- 拦截所有路由，并排除Login 用于开放登录
//                    SaRouter.match("/**", "/sys/login", r -> StpUtil.checkLogin());
                    SaRouter.match("/**", "/sys/**", r -> StpUtil.checkLogin());
                    // 权限认证 -- 不同模块, 校验不同权限
                    SaRouter.match("/sys/resource/**", () -> StpUtil.checkPermission("myResourceList"));
                    SaRouter.match("/sys/role/**", () -> StpUtil.checkPermission("myRoleList"));
                    SaRouter.match("/sys/user/**", () -> StpUtil.checkPermission("myUserList"));
                })
                // 指定[异常处理函数]：每次[认证函数]发生异常时执行此函数
                .setError(e -> {
                    log.info("登录信息异常",e);
                    BaseResponse baseResponse = new BaseResponse(BaseResponse.falseCode,e.getMessage(),null);
                    String jsonString = "{\"code\":400,\"msg\":\"未能读取到有效Token\",\"data\":null}";
                    try {
                        jsonString = objectMapper.writeValueAsString(baseResponse);
                    } catch (JsonProcessingException jsonProcessingException) {
                        log.info(jsonProcessingException.getMessage());
                    }
                    return jsonString;
                })
                ;
    }
}
