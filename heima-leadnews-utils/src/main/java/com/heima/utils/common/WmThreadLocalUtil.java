package com.heima.utils.common;

import com.heima.model.wemedia.pojos.WmUser;

/**
 * 自媒体ThreadLocal线程变量工具类
 *
 * @author yaoh
 */
public class WmThreadLocalUtil {

    private static final ThreadLocal<WmUser> WM_USER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 获取用户
     *
     * @return WmUser
     */
    public static WmUser getUser() {
        return WM_USER_THREAD_LOCAL.get();
    }

    /**
     * 添加用户
     *
     * @param wmUser
     */
    public static void setUser(WmUser wmUser) {
        WM_USER_THREAD_LOCAL.set(wmUser);
    }

    /**
     * 清理用户
     */
    public static void clear() {
        WM_USER_THREAD_LOCAL.remove();
    }
}
