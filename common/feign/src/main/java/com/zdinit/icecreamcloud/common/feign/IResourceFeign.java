package com.zdinit.icecreamcloud.common.feign;


import com.zdinit.icecreamcloud.common.entity.sys.entity.ResourceVo;
import com.zdinit.icecreamcloud.common.entity.sys.entity.RoleVo;
//import com.zdinit.icecreamcloud.common.feign.config.FeignInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 *
 降级方式有两种fallback 和 FallbackFactory，推荐用FallbackFactory
@FeignClient(name = "provide",fallback = RemoteHystrix.class)
@FeignClient(name = "provide",fallbackFactory = ProvideFeignFallbackFactory.class)

 */
@Component
@FeignClient(name = "authority",fallbackFactory = ResourceFeignFallbackFactory.class)
public interface IResourceFeign {
    @RequestMapping(value = "/sys/resource/listResourceByUserId",method = RequestMethod.GET)
    List<ResourceVo> listResourceByUserId(Long id);
    @RequestMapping(value = "/sys/resource/listRoleByUserId",method = RequestMethod.GET)
    List<RoleVo> listRoleByUserId(Long id);
}
