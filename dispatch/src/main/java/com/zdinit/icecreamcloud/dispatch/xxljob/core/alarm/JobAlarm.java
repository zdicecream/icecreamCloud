package com.zdinit.icecreamcloud.dispatch.xxljob.core.alarm;

import com.zdinit.icecreamcloud.dispatch.xxljob.core.model.XxlJobInfo;
import com.zdinit.icecreamcloud.dispatch.xxljob.core.model.XxlJobLog;

/**
 * @author xuxueli 2020-01-19
 */
public interface JobAlarm {

    /**
     * job alarm
     *
     * @param info
     * @param jobLog
     * @return
     */
    public boolean doAlarm(XxlJobInfo info, XxlJobLog jobLog);

}
