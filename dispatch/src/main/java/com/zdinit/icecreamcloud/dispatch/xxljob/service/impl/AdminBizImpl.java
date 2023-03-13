package com.zdinit.icecreamcloud.dispatch.xxljob.service.impl;

import com.zdinit.icecreamcloud.dispatch.xxljob.core.thread.JobCompleteHelper;
import com.zdinit.icecreamcloud.dispatch.xxljob.core.thread.JobRegistryHelper;
import com.zdinit.icecreamcloud.common.xxljobcore.biz.AdminBiz;
import com.zdinit.icecreamcloud.common.xxljobcore.biz.model.HandleCallbackParam;
import com.zdinit.icecreamcloud.common.xxljobcore.biz.model.RegistryParam;
import com.zdinit.icecreamcloud.common.xxljobcore.biz.model.ReturnT;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xuxueli 2017-07-27 21:54:20
 */
@Service
public class AdminBizImpl implements AdminBiz {


    @Override
    public ReturnT<String> callback(List<HandleCallbackParam> callbackParamList) {
        return JobCompleteHelper.getInstance().callback(callbackParamList);
    }

    @Override
    public ReturnT<String> registry(RegistryParam registryParam) {
        return JobRegistryHelper.getInstance().registry(registryParam);
    }

    @Override
    public ReturnT<String> registryRemove(RegistryParam registryParam) {
        return JobRegistryHelper.getInstance().registryRemove(registryParam);
    }

}
