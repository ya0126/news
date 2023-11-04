package com.stu.common.constants;

/**
 * 延迟队列相关常量
 *
 * @author yaoh
 */
public class ScheduleConstants {
    /**
     * 初始化状态
     */
    public static final int SCHEDULED = 0;
    /**
     * 已执行状态
     */
    public static final int EXECUTED = 1;
    /**
     * 已取消状态
     */
    public static final int CANCELLED = 2;
    /**
     * 未来数据Key前缀
     */
    public static String FUTURE = "future_";
    /**
     * 当前数据Key前缀
     */
    public static String TOPIC = "topic_";
}
