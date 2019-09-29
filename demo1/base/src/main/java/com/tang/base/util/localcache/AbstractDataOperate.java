package com.tang.base.util.localcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * < 数据处理动作抽象类，定义了缓存数据结构，其初始化方法等数据处理属性 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/10/22 <br>
 */
public abstract class AbstractDataOperate<T> implements IDataOperate {

    private Logger logger = LoggerFactory.getLogger(AbstractDataOperate.class);

    protected LocalCacheMap<T> cacheMap;

    /**
     * 默认使用本地缓存
     */
    protected static boolean isUseLocalCache = true;

    /**
     * < 只有当环境变量配置了LocalCache且其值为false（不分大小写）时，不使用缓存机制而是直接查表 >
     */
    static {
        String useLocalCache = System.getProperty("LocalCache", "true");
        if ("false".equalsIgnoreCase(useLocalCache)) {
            isUseLocalCache = false;
        }
    }

    /**
     * 默认的构造方法，暂时的理解是：初始化业务侧bean的时候，同一个bean,实现父类的构造方法，这个构造方法里面初始化了缓存数据结构，保证缓存的进程内唯一性
     */
    public AbstractDataOperate() {
        init();
        if (null == cacheMap) {
            logger.info("After function init(), the cacheMap is still null!");
            logger.info("Please initialize the cacheMap in function init()!");
        }
    }

    /**
     * < 本地缓存数据的初始化,在业务调用侧实现该方法，同时构造函数里调用该方法以初始化实际的缓存数据结构（也就是LocalCacheMap ）> <br>
     *
     * @auther: tang
     */
    public abstract void init();

}
