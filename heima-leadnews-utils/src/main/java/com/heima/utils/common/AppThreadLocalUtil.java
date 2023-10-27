package com.heima.utils.common;

import com.heima.model.user.pojos.ApUser;

/**
 * app端ThreadLocal线程变量工具类
 *
 * @author yaoh
 */
public class AppThreadLocalUtil {

    private static final ThreadLocal<ApUser> APP_USER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 获取用户
     *
     * @return ApUser
     */
    public static ApUser getUser() {
        return APP_USER_THREAD_LOCAL.get();
    }

    /**
     * 设置用户
     *
     * @param apUser
     */
    public static void setUser(ApUser apUser) {
        APP_USER_THREAD_LOCAL.set(apUser);
    }

    /**
     * 清理用户
     */
    public static void clear() {
        APP_USER_THREAD_LOCAL.remove();
    }
}
