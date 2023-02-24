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
                    SaRouter.match("/**", "/sys/login", r -> StpUtil.checkLogin());
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
