package com.zdinit.icecreamcloud.common.db.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zdinit.icecreamcloud.common.entity.sys.entity.User;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zd
 * @since 2021-05-10
 */
public interface UserMapper extends BaseMapper<User> {

    void saveRole(Long userid, Long roleid);

    void deleRole(Long userid, Long roleid);

    void deleRoleByUserId(Long userId);

    List<User> listUserByRoleId(Long id);
}
