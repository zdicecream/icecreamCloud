package com.zdinit.icecreamcloud.authority.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zdinit.icecreamcloud.authority.sys.mapper.GroupMapper;
import com.zdinit.icecreamcloud.authority.sys.service.IGroupService;
import com.zdinit.icecreamcloud.common.entity.sys.entity.Group;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zd
 * @since 2021-05-06
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {

}
