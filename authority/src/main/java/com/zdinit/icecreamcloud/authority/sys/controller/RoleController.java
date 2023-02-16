package com.zdinit.icecreamcloud.authority.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.zdinit.icecreamcloud.authority.sys.service.IResourceService;
import com.zdinit.icecreamcloud.authority.sys.service.IRoleService;
import com.zdinit.icecreamcloud.authority.sys.service.IUserService;
import com.zdinit.icecreamcloud.common.base.base.BaseController;
import com.zdinit.icecreamcloud.common.base.base.BaseResponse;
import com.zdinit.icecreamcloud.common.base.util.ResponseUtil;
import com.zdinit.icecreamcloud.common.entity.sys.entity.Resource;
import com.zdinit.icecreamcloud.common.entity.sys.entity.Role;
import com.zdinit.icecreamcloud.common.entity.sys.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zd
 * @since 2021-05-06
 */
@RestController
@RequestMapping("/sys/role")
public class RoleController extends BaseController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IResourceService resourceService;
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/getRoleList", method = RequestMethod.GET)
    public BaseResponse getRoleList(Role role){
        QueryWrapper<Role> wrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(role.getRoleName())){
            wrapper.and(w-> w.likeLeft("role_name",role.getRoleName()).or().likeRight("role_name",role.getRoleName()));
        }
        if (null != role.getState()){
            wrapper.eq("state",role.getState());
        }
        IPage<Role> roleList = roleService.page(page(),wrapper);

        if (roleList.getTotal()>0) {
            List keys = new ArrayList();
            roleList.getRecords().forEach(role1 -> keys.add(role1.getId()));
            List<Resource> resourceList = this.resourceService.listResourceByids(keys);

            List<Resource> itemList = null;
            List<Resource> menuList = null;
            for (Role r:
                 roleList.getRecords()) {
                itemList = resourceList.stream().filter(resource -> resource.getRoleId().equals(r.getId())).collect(Collectors.toList());
                /**
                 * 组装权限列表 将权限挂载到角色上
                 */
                menuList = this.resourceService.pack(itemList);
                r.setResourceList(menuList);
            }
        }
        return ResponseUtil.sucess(roleList);
    }

    @RequestMapping(value = "/getAllRoleList", method = RequestMethod.GET)
    public BaseResponse getAllRoleList(){
        List<Role> roleList = this.roleService.list();
        return ResponseUtil.sucess(roleList);
    }
    @RequestMapping(value = "/saveRole", method = RequestMethod.POST)
    public BaseResponse saveRole(@RequestBody Role role){
        this.roleService.saveRole(role);
        return ResponseUtil.sucess(null);
    }
    @RequestMapping(value = "/delRole", method = RequestMethod.POST)
    public BaseResponse delRole(@RequestParam Long id){
        List<User> users = this.userService.listUserByRoleId(id);
        if (users == null || users.size()<=0) {
            this.roleService.removeRoleById(id);
        }else {
            StringBuilder sb = new StringBuilder();
            users.forEach(user -> sb.append(user.getUsername()+","));
            return ResponseUtil.error("无法删除角色，尚有用户勾选此角色："+sb.toString());
        }
        return ResponseUtil.sucess(null);
    }

}
