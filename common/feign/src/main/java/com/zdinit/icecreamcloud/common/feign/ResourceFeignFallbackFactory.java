package com.zdinit.icecreamcloud.common.feign;



import com.zdinit.icecreamcloud.common.entity.sys.entity.ResourceVo;
import com.zdinit.icecreamcloud.common.entity.sys.entity.RoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ResourceFeignFallbackFactory implements FallbackFactory<IResourceFeign> {
    @Override
    public IResourceFeign create(Throwable throwable) {
        log.info("异常原因："+throwable.getMessage());
        return new IResourceFeign() {
            @Override
            public List<ResourceVo> listResourceByUserId(Long id) {
                log.info("熔断降级");
                return new ArrayList<>();
            }

            @Override
            public List<RoleVo> listRoleByUserId(Long id) {
                log.info("熔断降级");
                return new ArrayList<>();
            }
        };
    }
}
