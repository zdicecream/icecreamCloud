package com.zdinit.icecreamcloud.authority.sys.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zdinit.icecreamcloud.authority.sys.mapper.UserMapper;
import com.zdinit.icecreamcloud.authority.sys.service.IRoleService;
import com.zdinit.icecreamcloud.authority.sys.service.IUserService;
import com.zdinit.icecreamcloud.common.entity.sys.entity.Role;
import com.zdinit.icecreamcloud.common.entity.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zd
 * @since 2021-05-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private IRoleService roleService;

    @Override
    public User judge(String name, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",name);
        return this.getOne(queryWrapper);
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        if (null != user.getId()){
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.set("username", user.getUsername());
            updateWrapper.set("state",user.getState());
            updateWrapper.eq("id",user.getId());
            this.update(updateWrapper);

            /**
             * 原角色列表
             */
            List<Role> roleList = roleService.listRoleByUserId(user.getId());
            List<Long> roleKeys = new ArrayList();
            roleList.forEach(role -> roleKeys.add(role.getId()));
            /**
             * 新角色列表
             */
            List<Long> userKeys = new ArrayList<>();
            user.getRoleList().forEach(role ->userKeys.add(Long.parseLong((String) role) ));

            List<Long> deleList = roleKeys.stream().filter( key -> !userKeys.contains(key)).collect(Collectors.toList());
            deleList.forEach( key -> this.baseMapper.deleRole(user.getId(),key));

            List<Long> addList = userKeys.stream().filter( key -> !roleKeys.contains(key)).collect(Collectors.toList());
            addList.forEach(key -> this.baseMapper.saveRole(user.getId(),key));

        }else {
            User userAdd = new User();
            userAdd.setUsername(user.getUsername());
            userAdd.setPassword(SaSecureUtil.md5("111111"));
            userAdd.setShowName(user.getUsername());
            userAdd.setMobile("021-12345678");
            userAdd.setEmail("5645654@654.com");
            userAdd.setGroupId(2L);
            userAdd.setState(user.getState());
            userAdd.setCreateTime(LocalDateTime.now());
            this.save(userAdd);

            user.getRoleList().forEach(roleid -> this.baseMapper.saveRole(userAdd.getId(),Long.parseLong((String) roleid)));
        }
    }

    @Override
    @Transactional
    public void removeUserById(String id) {
        this.baseMapper.deleRoleByUserId(Long.parseLong(id));
        this.removeById(id);
    }

    @Override
    public List<User> listUserByRoleId(Long id) {
        return this.baseMapper.listUserByRoleId(id);
    }

}
