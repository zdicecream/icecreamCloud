package com.zdinit.icecreamcloud.gateway.config;


import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.zdinit.icecreamcloud.gateway.base.CommonValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限验证接口扩展
 */
@Slf4j
@Component    // 保证此类被SpringBoot扫描，完成Sa-Token的自定义权限验证扩展
public class SaTokenPermission implements StpInterface {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> permissionList = (List<String>) redisTemplate.opsForHash().get(CommonValue.TOKEN_NAME+ loginId,"resource");
        return permissionList;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> roleList = (List<String>) redisTemplate.opsForHash().get(CommonValue.TOKEN_NAME+loginId,"role");
        return roleList;
    }
}
