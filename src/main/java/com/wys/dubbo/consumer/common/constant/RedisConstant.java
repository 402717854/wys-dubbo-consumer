package com.wys.dubbo.consumer.common.constant;

/**
 * @Classname RedisConstant
 * @Description redis常量类
 */
public class RedisConstant {
    /**虚拟数据过期时间毫秒 1小时*/
    public static final Long VIRTUAL_EX = 1000L*60L*60L;

    /**运营端用户*/
    public static final String OPERATION_USER_INIT = "sjt:operation:userInit:userId:";
    /**角色信息详情*/
    public static final String OPERATION_ROLE_INIT = "sjt:operation:roleInit:roleId:";

    /**菜单信息详情*/
    public static final String OPERATION_MENU_INIT = "sjt:operation:menuInit:menuId:";

    /**用户拥有的权限信息*/
    public static final String OPERATION_USER_PERMISSION_MENU = "sjt:operation:userPermsMenu:userId:";

    /**用户拥有的角色信息*/
    public static final String OPERATION_USER_VALID_ROLE = "sjt:operation:userValidRole:userId:";

    /**角色拥有的菜单Id信息*/
    public static final String OPERATION_ROLE_VALID_MENU = "sjt:operation:roleValidMenu:roleId:";

    /**角色拥有的菜单代码hash*/
    public static final String OPERATION_ROLE_VALID_MENU_HASH = "sjt:operation:roleValidMenu_hash";


}
