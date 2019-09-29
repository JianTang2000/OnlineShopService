package com.tang.constant;

/**
 * < 这里的每一组key，分别用作map(每个线程会建一个stack存放自己的map)的key，注意value尽量使用包装类型 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/22 <br>
 */
public final class AppMapKeyDef {
    /**
     * < > <br>
     *
     * @auther: tang
     */
    private AppMapKeyDef() {

    }
    // =================================================================================

    /**
     * < 模糊查询的入参 >
     */
    public static final String GET_DATA_INPUT_PARAM = "GetDataInputParam";

    /**
     * < 模糊查询的结果，这个value可能会比较大 >
     */
    public static final String QUERY_RESOURCE_RESULT = "QueryResourceResult";

    /**
     * < 入参转成的唯一key，这个key的重复不影响流程本身，但影响本地缓存的精确匹配，所以FC里要转成唯一的key >
     */
    public static final String INPUT_KEY = "InputKey";

    // =================================================================================

    /**
     * < LocalCacheKeyLevel1Def的所有属性名（这些属性名其实也就是1级key的名字） >
     */
    public static final String ALL_DEF_KEY_NAME = "allDefKey";

    /**
     * < LocalCacheRepositoryBeanNameDef的所有属性名（这些属性名其实也就是缓存map所在的bean的bean名） >
     */
    public static final String ALL_DEF_BEAN_NAME = "allDefBeanName";

    /**
     * < 最终需要的树形数据结构 >
     */
    public static final String FINAL_TREE_DATA = "finalTreeData";

}
