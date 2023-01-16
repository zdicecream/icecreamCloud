package com.zdinit.icecreamcloud.common.base.feign;



import com.zdinit.icecreamcloud.common.entity.sys.entity.Resource;
import com.zdinit.icecreamcloud.common.entity.sys.entity.Role;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
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
            public List<Resource> listResourceByUserId(Long id) {
                log.info("熔断降级");
                return new ArrayList<>();
            }

            @Override
            public List<Role> roleList() {
                log.info("熔断降级");
                return new ArrayList<>();
            }
        };
    }
}
