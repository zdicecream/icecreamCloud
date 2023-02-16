package com.zdinit.icecreamcloud.authority.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zdinit.icecreamcloud.common.entity.sys.entity.Resource;
import com.zdinit.icecreamcloud.common.entity.sys.entity.Role;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zd
 * @since 2021-06-10
 */
public interface IResourceService extends IService<Resource> {

    List<Resource> listResourceByids(List keys);

    List<Resource> pack(List<Resource> resourceList);

    List<Resource> listResourceById(Long id);

    void delResource(Long roleId, Long resourceId);

    void addResource(Long roleId, Long resourceId);

    void delResourceByRoleId(Long id);

    List<Resource> tree(List<Resource> menuList);

    void saveResource(Resource resource);

    List<Role> listRoleByResourceId(Long id);

    void removeResourceById(Long id);

    List<Resource> listResourceByUserId(Long id);
}
