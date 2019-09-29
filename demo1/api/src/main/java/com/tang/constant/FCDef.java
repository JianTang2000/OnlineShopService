package com.tang.constant;

/**
 * < FC(function component)名称定义，注册的时候，请使得该名称就是bean名 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/22 <br>
 */
public class FCDef {
    /**
     * < > <br>
     *
     * @auther: tang
     */
    private FCDef() {

    }
    // =================================================================================

    /**
     * < InputToKey FC,将input转成唯一的key >
     */
    public static final String INPUT_TO_KEY_FC = "InputToKeyFC";

    /**
     * < 根据唯一标志key从内存中检索资源文件 >
     */
    public static final String GET_RESOURCE_BY_KEY_FC = "GetResourceByKeyFC";

    /**
     * < GetResourceByInputFC >
     */
    public static final String GET_RESOURCE_BY_INPUT_FC = "GetResourceByInputFC";

    /**
     * < DealQueryResultFC >
     */
    public static final String DEAL_QUERY_RESULT_FC = "DealQueryResultFC";

    // =================================================================================

    /**
     * < 获取本地缓存中所有定义好的一级key >
     */
    public static final String GET_ALL_LEVEL_1_KEY_FC = "GetAllLevel1KeyFC";

    /**
     * < 查询所有key的相关信息 >
     */
    public static final String QUERY_KEY_CONDITION_FC = "QueryKeyConditionFC";

}
