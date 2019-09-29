package com.tang.base.util.localcache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

/**
 * < 数据加载抽象类，定义了数据加载的动作，业务侧直接继承这个类即可><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/10/22 <br>
 */
public abstract class AbstractFullLoad<T> extends AbstractDataOperate<T> {

    /**
     * 是否已经执行全量加载
     */
    protected boolean isFullLoaded = false;

    /**
     * < 更新map,其实也就是isFullLoaded置false,在高层中实现。（本来是想写反更新DB的，但暂时还没实现 ）> <br>
     *
     * @auther: tang
     * @param operator < >
     * @param tableName < >
     * @param data < >
     */
    @Override
    public void update(String operator, String tableName, String data) {
        // 全量加载的情况下只需要将isFullLoaded置为false即可，在处理find方法的时候会根据isFullLoaded来判断是否需要再重新加载
        isFullLoaded = false;
    }

    public void reload() {
        // 当使用本地缓存时，内部的判断及加载过程需要加上同步锁，避免对象被重复赋值
        if (isUseLocalCache) {
            synchronized (this) {
                if (!isFullLoaded) {
                    // 获取原key值并进行初始化
                    Set<String> keySet = cacheMap.keySet();
                    String[] keyArray = (String[]) keySet.toArray(new String[keySet.size()]);
                    cacheMap = new LocalCacheMapImpl<>();
                    for (String key : keyArray) {
                        cacheMap.addKey(key);
                    }

                    loadAll();
                    if (isUseLocalCache) {
                        isFullLoaded = true;
                    }
                }
            }
        }
    }

    /**
     * < 数据加载抽象方法，业务侧提供方法的实现（比如根据某个/些/无条件去查寻，然后把结果放到缓存） > <br>
     *
     * @auther: tang
     */
    public abstract void loadAll();

    /**
     * < 判断缓存map是否为空 > <br>
     *
     * @auther: tang
     * @return < >
     */
    public boolean mapIsEmpty() {
        return cacheMap.isEmpty();
    }

    /**
     * < mapKeySet 1级key > <br>
     *
     * @auther: tang
     * @return < >
     */
    public Set<String> mapKeySet() {
        return cacheMap.keySet();
    }

    /**
     * < mapKeyValueSet 2级key > <br>
     *
     * @auther: tang
     * @param key < 1级key Name >
     * @return < >
     */
    public Set<Object> mapKeyValueSet(String key) {
        return cacheMap.keyValueSet(key);
    }

    /**
     * <得到一级key下的某个二级key，返回二级key的class（每个二级key的class一般是一样的）> <br>
     *
     * @auther: tang
     * @param key < 1级key Name >
     */
    public Class getValueClassByKey(String key) {
        return cacheMap.getKeyLevel2Class(key);
    }

    /**
     * < mapKeyValueSet 2级key > <br>
     *
     * @auther: tang
     * @param key < 1级key Name >
     * @param keyValue < 2级key Name >
     * @return < >
     */
    public List<T> getValueByKey(String key, Object keyValue) {
        return cacheMap.get(key, keyValue);
    }

    /**
     * < map的reload状态会被置为否，即非填充状态；这时查询请求到来时查询为空则会指定reload；也可以再手动调用reload实现刷新map > <br>
     *
     * @auther: tang
     */
    public void changeIsFullLoadedFalse() {
        isFullLoaded = false;
    }

    /**
     * < 获取原key值并进行初始化 > <br>
     *
     * @auther: tang
     */
    public void cleanMap() {
        // 获取原key值并进行初始化
        Set<String> keySet = cacheMap.keySet();
        String[] keyArray = (String[]) keySet.toArray(new String[keySet.size()]);
        cacheMap = new LocalCacheMapImpl<>();
        for (String key : keyArray) {
            cacheMap.addKey(key);
        }
    }

    /**
     * < 更改某个指定key的value,value要和原数据的class保持一致 > <br>
     *
     * @auther: tang
     * @param key < 1级key >
     * @param keyValue < 2级key >
     * @param value < 要和原数据的class保持一致 >
     */
    public void modifyValueByKey(String key, Object keyValue, Object value) {
        cacheMap.modify(key, keyValue, value);
    }

}
