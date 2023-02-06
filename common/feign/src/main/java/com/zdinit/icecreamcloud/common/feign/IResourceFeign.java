package com.zdinit.icecreamcloud.common.feign;


import com.zdinit.icecreamcloud.common.entity.sys.entity.Resource;
import com.zdinit.icecreamcloud.common.entity.sys.entity.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 *
 降级方式有两种fallback 和 FallbackFactory，推荐用FallbackFactory
@FeignClient(name = "provide",fallback = RemoteHystrix.class)
@FeignClient(name = "provide",fallbackFactory = ProvideFeignFallbackFactory.class)

 */
@FeignClient(name = "resource",fallbackFactory = ResourceFeignFallbackFactory.class)
public interface IResourceFeign {
    @RequestMapping(value = "/listResourceByUserId",method = RequestMethod.GET)
    List<Resource> listResourceByUserId(Long id);
    @RequestMapping(value = "/listRoleByUserId",method = RequestMethod.GET)
    List<Role> listRoleByUserId();
}
