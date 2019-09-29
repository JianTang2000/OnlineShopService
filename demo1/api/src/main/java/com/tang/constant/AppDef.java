package com.tang.constant;

/**
 * < app名称定义，注册的时候，请使得该名称就是bean名 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/22 <br>
 */
public final class AppDef {

    /**
     * < > <br>
     *
     * @auther: tang
     */
    private AppDef() {
    }

    /**
     * < 查询app,在前台输入一定查询条件时后台的响应app >
     */
    public static final String QUERY_RESOURCE_BY_INPUT_APP = "QueryResourceByInputApp";

    /**
     * < 查询localCache app,检索进程内的进程缓存信息， >
     */
    public static final String QUERY_LOCAL_CACHE_INFO_APP = "QueryLocalCacheInfoApp";
}
