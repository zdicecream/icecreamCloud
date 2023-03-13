package com.zdinit.icecreamcloud.dispatch.xxljob.core.route.strategy;

import com.zdinit.icecreamcloud.dispatch.xxljob.core.route.ExecutorRouter;
import com.zdinit.icecreamcloud.common.xxljobcore.biz.model.ReturnT;
import com.zdinit.icecreamcloud.common.xxljobcore.biz.model.TriggerParam;

import java.util.List;

/**
 * Created by xuxueli on 17/3/10.
 */
public class ExecutorRouteLast extends ExecutorRouter {

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList) {
        return new ReturnT<String>(addressList.get(addressList.size()-1));
    }

}