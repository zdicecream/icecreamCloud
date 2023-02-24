package com.zdinit.icecreamcloud.common.base.base;

public class CommonValue {
    /**
     * 启动
     */
    public static final String STATE_ENABLE = "1";
    /**
     * 禁用
     */
    public static final String STATE_DISABLE = "0";
    /**
     * 菜单
     */
    public static final int MENU = 1;
    /**
     * 操作
     */
    public static final int OPERATION = 2;
    /**
     * 叶子
     */
    public static final int TREE_LEAF = 1;
    /**
     * 非叶子
     */
    public static final int TREE_NOT_LEAF = 0;
    /**
     * 工作流-新建
     */
    public static final int FLOW_BUILT = 0;
    /**
     * 工作流-已提交
     */
    public static final int FLOW_SUBMIT = 1;
    /**
     * 工作流-已结束
     */
    public static final int FLOW_CLOSE = 2;
    /**
     * 工作流-驳回
     */
    public static final int FLOW_REJECT = 3;
    /**
     * 工作流-挂起
     */
    public static final int FLOW_SUSPEND = 4;
    /**
     * 工作流-同意
     */
    public static final String FLOW_YES = "yes";
    /**
     * 工作流-不同意
     */
    public static final String FLOW_NO = "no";
    /**
     * redis 前缀
     */
    public static final String TOKEN_NAME =CommonValue.TOKEN_NAME_PART1+":"+CommonValue.TOKEN_NAME_PART2+":"+CommonValue.TOKEN_NAME_PART3+":";
    /**
     * redis 前缀
     */
    public static final String TOKEN_NAME_PART1 = "Access-Token";
    /**
     * redis 前缀
     */
    public static final String TOKEN_NAME_PART2 = "login";
    /**
     * redis 前缀
     */
    public static final String TOKEN_NAME_PART3 = "authority";
}
