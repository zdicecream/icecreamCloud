package com.zdinit.icecreamcloud.authority.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zdinit.icecreamcloud.authority.sys.mapper.RoleMapper;
import com.zdinit.icecreamcloud.authority.sys.service.IResourceService;
import com.zdinit.icecreamcloud.authority.sys.service.IRoleService;
import com.zdinit.icecreamcloud.authority.sys.entity.Resource;
import com.zdinit.icecreamcloud.authority.sys.entity.Role;
import com.zdinit.icecreamcloud.common.entity.sys.entity.RoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zd
 * @since 2021-05-06
 */
@Service
@Slf4j
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    private IResourceService resourceService;

    @Override
    public List<RoleVo> listRoleByIds(List<Long> list) {
        return this.baseMapper.listRoleByIds(list);
    }

    @Override
    public List<Role> listRoleByUserId(Long id) {
        return this.baseMapper.listRoleByUserId(id);
    }

    @Transactional
    @Override
    public void saveRole(Role role) {
        //全部权限
        List<Resource> allResourceList = this.resourceService.list();
        if (null !=role.getId()){
            UpdateWrapper<Role> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("role_name",role.getRoleName());
            updateWrapper.set("state",role.getState());
            updateWrapper.eq("id",role.getId());
            this.update(updateWrapper);
            /**
             * 原权限列表
             */
            List<Resource> resourceList = resourceService.listResourceById(role.getId());
            List<Long> allKeys = resourceList.stream().map(m->m.getId()).collect(Collectors.toList());
            /**
             * 新权限列表
             */
            List<Long> userKeys = (List<Long>) role.getResourceList().stream().filter(k->null!=k).map(k ->Long.parseLong((String) k)).collect(Collectors.toList());
            List<Long> allUserkeys= this.getPid(userKeys,allResourceList);
            //删除
            List<Long> delList = allKeys.stream().filter( key -> !allUserkeys.contains(key)).collect(Collectors.toList());
            delList.forEach(resourceId-> this.resourceService.delResource(role.getId(),resourceId));
            //添加
            List<Long> addList = allUserkeys.stream().filter(key -> !allKeys.contains(key)).collect(Collectors.toList());
            addList.forEach(resourceId ->this.resourceService.addResource(role.getId(),resourceId));
        }else {
            Role roleAdd = new Role();
            roleAdd.setRoleName(role.getRoleName());
            roleAdd.setState(role.getState());
            this.save(roleAdd);
            /**
             * 保存权限
             */
            List<Long> userKeys = new ArrayList<>();
            role.getResourceList().stream().filter(k -> null!=k).forEach(key-> {
//                for (int i = 0; i < key. ; i++) {
//                    userKeys.add(Long.parseLong((String) ( key).get(i)));
//                }
            });
            List<Long> allUserKey = this.getPid(userKeys,allResourceList);
            allUserKey.forEach(resourceId ->this.resourceService.addResource(roleAdd.getId(),resourceId));
        }
    }

    @Override
    @Transactional
    public void removeRoleById(Long id) {
        this.resourceService.delResourceByRoleId(id);
        this.removeById(id);
    }

    private List<Resource> getResourceTree() {
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",1);
        List<Resource> resourceList =  resourceService.list(queryWrapper);
        return resourceList;
    }

    private List<Long> getPid(List<Long> keys,List<Resource> resourceList){
        List<Long> longs = new ArrayList<>();
        Map<Long,Resource> map = resourceList.stream().collect(Collectors.toMap(m->m.getId(),m->m));
        resourceList.forEach(resource -> {
            //包含id则将id加入longs并迭代处理父id
            if (keys.contains(resource.getId())){
                while (null !=resource.getPid()){
                    longs.add(resource.getPid());
                    resource = map.get(resource.getPid());
                }
            }
        });
        keys.addAll(longs);
        //去重
        return keys.stream().distinct().collect(Collectors.toList());
    }
}
