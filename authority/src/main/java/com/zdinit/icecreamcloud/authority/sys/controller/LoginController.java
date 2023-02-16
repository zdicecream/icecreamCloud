package com.zdinit.icecreamcloud.authority.sys.controller;



import cn.dev33.satoken.stp.StpUtil;
import com.zdinit.icecreamcloud.authority.sys.service.IGroupService;
import com.zdinit.icecreamcloud.authority.sys.service.IResourceService;
import com.zdinit.icecreamcloud.authority.sys.service.IUserService;
import com.zdinit.icecreamcloud.common.base.base.BaseController;
import com.zdinit.icecreamcloud.common.base.base.BaseResponse;
import com.zdinit.icecreamcloud.common.base.base.CheckException;
import com.zdinit.icecreamcloud.common.base.util.ResponseUtil;
import com.zdinit.icecreamcloud.common.entity.sys.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zd
 * @since 2020-09-01
 */
@Slf4j
@RestController
@RequestMapping("/sys")
public class LoginController extends BaseController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IResourceService resourceService;
    @Autowired
    private IGroupService groupService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponse login(@RequestBody User userInfo){

        User user = userService.judge(userInfo.getUsername(),userInfo.getPassword());
        if (user == null) {
            return ResponseUtil.error("无此用户");
        }
        if (!userInfo.getPassword().equals(user.getPassword())) {
            return ResponseUtil.error("密码错误");
        }
        StpUtil.login(user.getId());
        log.info(StpUtil.getTokenValue());
        return ResponseUtil.sucess(StpUtil.getTokenInfo());
    }

    @RequestMapping(value = "/isLogin", method = RequestMethod.POST)
    public BaseResponse isLogin(){
        if (1 == 1) {
            throw new CheckException("1");
        }

        return ResponseUtil.sucess("当前会话是否登录：" + StpUtil.isLogin());
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public BaseResponse logout(){
        StpUtil.logout();
        return ResponseUtil.sucess("已登出");
    }

}
