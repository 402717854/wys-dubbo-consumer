package com.wys.dubbo.consumer.util;

import com.wys.dubbo.consumer.common.entity.ThreadEntity;

/**
 * @Classname ThreadLocalUtil
 * @Description 线程私有方法工具
 * @Date 2020/12/11 13:39
 * @Created by 20113370
 */
public class ThreadLocalUtil {
    private static ThreadLocal<ThreadEntity> stringThreadLocal = new InheritableThreadLocal<>();

    /**
     * @return void
     * @Description 设置userId
     * @Param userId
     * @Date 11:27 2020/11/27
     * @Author lizy
     **/
    public static void setLocalUserId(Integer userId) {
        getThreadEntityInstance().setUserId(userId);
   }

    /**
     * @return void
     * @Description 设置用户token
     * @Param userId
     * @Date 11:27 2020/11/27
     * @Author lizy
     **/
    public static void setLocalAccessToken(String accessToken) {
        getThreadEntityInstance().setAccessToken(accessToken);
   }

    /**
     * @return Integer
     * @Description 获取线程变量中的用户Id
     * @Param
     * @Date 11:27 2020/11/27
     * @Author lizy
     **/
    public static Integer getLocalUserId() {


        if (stringThreadLocal.get() == null){
         //说明应该是系统自执行设置userId为0
            return 0;
       }
        return stringThreadLocal.get().getUserId();
   }

    /**
     * @return Integer
     * @Description 获取线程变量中的用户Id
     * @Param
     * @Date 11:27 2020/11/27
     * @Author lizy
     **/
    public static String getLocalAccessToken() {
        if (stringThreadLocal.get() == null){
            //说明应该是系统自执行设置system
            return "system";
       }
        return stringThreadLocal.get().getAccessToken();
   }

    /**
     * @return void
     * @Description 处理完删除线程变量
     * @Param
     * @Date 11:31 2020/11/27
     * @Author lizy
     **/
    public static void remove() {
        stringThreadLocal.remove();
   }

    protected static ThreadEntity getThreadEntityInstance() {
        if (stringThreadLocal.get() == null) {
            stringThreadLocal.set(new ThreadEntity());
       }
        return stringThreadLocal.get();
   }
}
