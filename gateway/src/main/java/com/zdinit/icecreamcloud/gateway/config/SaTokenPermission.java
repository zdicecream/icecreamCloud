package com.zdinit.icecreamcloud.gateway.config;


import cn.dev33.satoken.stp.StpInterface;
import com.zdinit.icecreamcloud.common.feign.IResourceFeign;
import com.zdinit.icecreamcloud.gateway.base.CommonValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义权限验证接口扩展
 */
@Component    // 保证此类被SpringBoot扫描，完成Sa-Token的自定义权限验证扩展
public class SaTokenPermission implements StpInterface {
    @Autowired
    private IResourceFeign resourceService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> permissionList = (List<String>) redisTemplate.opsForHash().get(loginId,"Resource");
        if (permissionList == null || permissionList.size() <= 0) {
            permissionList = resourceService.listResourceByUserId(Long.parseLong((String) loginId)).stream().filter(m->m.getType().equals(CommonValue.MENU)).map(m-> m.getResourceCode()).collect(Collectors.toList());
        }
        return permissionList;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> roleList = (List<String>) redisTemplate.opsForHash().get(loginId,"Role");
        if (roleList == null) {
            roleList = resourceService.listRoleByUserId().stream().map(role -> role.getRoleName()).collect(Collectors.toList());
        }
        return roleList;
    }
}