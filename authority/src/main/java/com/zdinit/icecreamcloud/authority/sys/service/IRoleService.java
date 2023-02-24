package com.zdinit.icecreamcloud.authority.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zdinit.icecreamcloud.authority.sys.entity.Role;
import com.zdinit.icecreamcloud.common.entity.sys.entity.RoleVo;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zd
 * @since 2021-05-06
 */
public interface IRoleService extends IService<Role> {
    List<RoleVo> listRoleByIds(List<Long> list);

    List<Role> listRoleByUserId(Long id);

    void saveRole(Role role);

    void removeRoleById(Long id);
}
