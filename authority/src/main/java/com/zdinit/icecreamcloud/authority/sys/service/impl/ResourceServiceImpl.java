package com.zdinit.icecreamcloud.authority.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zdinit.icecreamcloud.authority.sys.mapper.ResourceMapper;
import com.zdinit.icecreamcloud.authority.sys.service.IResourceService;
import com.zdinit.icecreamcloud.common.base.base.CommonValue;
import com.zdinit.icecreamcloud.common.entity.sys.entity.Resource;
import com.zdinit.icecreamcloud.common.entity.sys.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zd
 * @since 2021-06-10
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public List<Resource> listResourceByids(List keys) {
        return this.baseMapper.listResourceByids(keys);
    }

    @Override
    public List<Resource> pack(List<Resource> resourceList) {
        //将所有操作挂载到菜单上
        //过滤所有操作
        List<Resource> list = resourceList.stream().filter(resource -> resource.getType().equals(CommonValue.OPERATION)).collect(Collectors.toList());
        //组装为（pid，resourcelsit）的map
        Map<Long,List<Resource>> operationMap = this.packMap(list);
        //循环挂载
        List<Resource> menuList = resourceList.stream().filter(resource -> resource.getType().equals(CommonValue.MENU) && resource.getLeaf().equals(CommonValue.TREE_LEAF)).collect(Collectors.toList());

        menuList.forEach(resource -> {
            resource.setActionList(operationMap.get(resource.getId()));
        });
        return menuList;

/*        //过滤三级菜单并将三级菜单挂载到过滤的二级菜单
        List<Resource> menu3List =  resourceList.stream().filter(resource -> resource.getType().equals(CommonValue.MENU) && resource.getLevel().equals(CommonValue.MENU_LEVEL3)).collect(Collectors.toList());
        Map<Long,List<Resource>> menu3Map = this.packMap(menu3List);
        List<Resource> menu2List = resourceList.stream().filter(r-> r.getLevel().equals(CommonValue.MENU_LEVEL2) && r.getType().equals(CommonValue.MENU))
                .map( r-> {
                    r.getChildList().addAll(menu3Map.get(r.getId()));
                    return r;
                }).collect(Collectors.toList());

        //将二级菜单挂载到过滤的一级菜单
        Map<Long,List<Resource>> menu2Map = this.packMap(menu2List);
        List<Resource> menu1List = resourceList.stream().filter(r-> r.getLevel().equals(CommonValue.MENU_LEVEL1) && r.getType().equals(CommonValue.MENU))
                .map( r-> {
                    r.getChildList().addAll(menu2Map.get(r.getId()));
                    return r;
                }).collect(Collectors.toList());*/
    }

    @Override
    public List<Resource> listResourceById(Long id) {
        return this.baseMapper.listResourceByid(id);
    }

    @Override
    public void delResource(Long roleId, Long resourceId) {
        this.baseMapper.delResource(roleId, resourceId);
    }

    @Override
    public void addResource(Long roleId, Long resourceId) {
        this.baseMapper.addResource(roleId,resourceId);
    }

    @Override
    public void delResourceByRoleId(Long id) {
        this.baseMapper.delResourceByRoleId(id);
    }

    @Override
    public List<Resource> tree(List<Resource> menuList) {
        List<Resource> resourceList = this.list();
        List<Resource> res = menuList;
        while (menuList!=null && menuList.size()>0) {
            List<Resource> resources = null;
            List<Resource> allResources = new ArrayList<>();
            for (Resource resource:
                 menuList) {
                resources = resourceList.stream().filter(resource1 -> null!=resource1.getPid() && resource1.getPid().equals(resource.getId())).collect(Collectors.toList());
                if (resources != null) {
                    if (resource.getLeaf().equals(CommonValue.TREE_LEAF)){
                        resource.setActionList(resources);
                    }else {
                        resource.setChildren(resources);
                    }
                    allResources.addAll(resources);
                }
            }
            menuList = allResources;
        }
        return res;
    }

    @Override
    @Transactional
    public void saveResource(Resource resource) {
        if (null != resource.getId()){
            UpdateWrapper<Resource> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("resource_code",resource.getResourceCode());
            updateWrapper.set("resource_name",resource.getResourceName());
            updateWrapper.set("orders",resource.getOrders());
            updateWrapper.eq("id",resource.getId());
            this.update(updateWrapper);

            QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("pid",resource.getId());
            queryWrapper.eq("type",CommonValue.OPERATION);
            List<Resource> actionList = this.list(queryWrapper);
            List keys = actionList.stream().map(m->m.getResourceCode()).collect(Collectors.toList());
            /**
             * 删除未选actionList
             */
            actionList.forEach(item ->{
                if(!resource.getActionList().contains(item.getResourceCode())){
                    this.removeById(item.getId());
                }
            });
            /**
             * 添加actionList
             */
            List addList = (List) resource.getActionList().stream().filter(key -> !keys.contains(key)).collect(Collectors.toList());
            if (null!=addList && addList.size()>0) {
                addList.forEach(code -> {
                    Resource action = new Resource();
                    action.setResourceCode((String) code);
                    action.setResourceName((String) code);
                    action.setOrders(1);
                    action.setType(CommonValue.OPERATION);
                    action.setLeaf(CommonValue.TREE_LEAF);
                    action.setPid(resource.getId());
                    this.save(action);
                });
            }
        }else {
            Resource resourceAdd = new Resource();
            resourceAdd.setResourceCode(resource.getResourceCode());
            resourceAdd.setResourceName(resource.getResourceName());
            resourceAdd.setOrders(resource.getOrders());
            resourceAdd.setType(CommonValue.MENU);
            resourceAdd.setLeaf(CommonValue.TREE_LEAF);
            resourceAdd.setPid(resource.getPid());
            this.save(resourceAdd);

            if (null != resourceAdd.getPid()){
                UpdateWrapper<Resource> wrapper = new UpdateWrapper<>();
                wrapper.set("leaf",CommonValue.TREE_NOT_LEAF);
                wrapper.eq("id",resourceAdd.getPid());
                this.update(wrapper);
            }
            /**
             * 添加actionList
             */
            if (null!=resource.getActionList() && resource.getActionList().size()>0){
                resource.getActionList().forEach(code -> {
                    Resource action = new Resource();
                    action.setResourceCode((String) code);
                    action.setResourceName((String) code);
                    action.setOrders(1);
                    action.setType(CommonValue.OPERATION);
                    action.setLeaf(CommonValue.TREE_LEAF);
                    action.setPid(resourceAdd.getId());
                    this.save(action);
                });
            }

        }

    }

    @Override
    public List<Role> listRoleByResourceId(Long id) {
        return this.baseMapper.listRoleByResourceId(id);
    }

    @Override
    @Transactional
    public void removeResourceById(Long id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("pid",id);
        this.remove(queryWrapper);
        this.removeById(id);
    }

    @Override
    public List<Resource> listResourceByUserId(Long id) {
        if(redisTemplate.hasKey("Resource{"+id+"}")){
            List<Resource> res = (List<Resource>) redisTemplate.opsForValue().get("Resource{"+id+"}");
            return res;
        }else {
            List<Resource> res = this.baseMapper.listResourceByUserId(id);
            redisTemplate.opsForValue().set("Resource{"+id+"}",res,5, TimeUnit.MINUTES);
            return res;
        }
    }

    private Map<Long, List<Resource>> packMap(List<Resource> list){
        Map<Long,List<Resource>> resourceMap = new HashMap<>();
        List<Resource> listItem = null;
        for (Resource resource : list) {
            if (resourceMap.containsKey(resource.getPid())){
                resourceMap.get(resource.getPid()).add(resource);
            }else {
                listItem = new ArrayList<>();
                listItem.add(resource);
                resourceMap.put(resource.getPid(),listItem);
            }
        }
        return resourceMap;
    }
}
