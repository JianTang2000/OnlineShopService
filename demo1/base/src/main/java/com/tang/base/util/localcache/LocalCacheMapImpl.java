package com.tang.base.util.localcache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * < 缓存数据结构包装接口实现 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/10/22 <br>
 */
public class LocalCacheMapImpl<T> implements LocalCacheMap<T> {

    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(LocalCacheMapImpl.class);

    /**
     * 每组bean对应的缓存的合集，对于每一个不同的bean,都会实例化一个不同的localCacheMap; 一级key为String，表示缓存的实际意义类型,比如说成员表的表名;
     * 二级key为Object，比如说会写Long，表示类型的进一步细分,比如说成员表的具体Id; value数据存储集合为List<T>，由业务侧接口决定<T>,这里用list<T>而不是<T>是经过考虑了的，
     * 虽然大部分情况下1+2级索引出来是一个自定义的包装类型<T>,但也有可能是一个List,所以这里写list<T>， 如果1+2级索引对应存的是<T>，那么就存一个只有一条数据的List<T>，否则直接存list<T>.
     */
    private Map<String, Map<Object, List<T>>> localCacheMap = new ConcurrentHashMap<>();

    @Override
    public LocalCacheMap<T> addKey(String key) {
        if (!localCacheMap.containsKey(key)) {
            Map<Object, List<T>> chunkCacheMap = new ConcurrentHashMap<>();
            localCacheMap.put(key, chunkCacheMap);
        }
        return this;
    }

    @Override
    public Set<String> keySet() {
        return localCacheMap.keySet();
    }

    @Override
    public Set<Object> keyValueSet(String key) {
        if (StringUtils.isEmpty(key)) {
            logger.info("find keyValueSet by key, but key is null！");
            return null;
        }
        else {
            if (localCacheMap.containsKey(key)) {
                Map<Object, List<T>> chunkCacheMap = localCacheMap.get(key);
                return chunkCacheMap.keySet();
            }
            else {
                logger.info("localCache do not contains Key :{}", key);
                return null;
            }
        }
    }

    @Override
    public List<T> get(String key, Object keyValue) {
        if (null == keyValue || StringUtils.isEmpty(key)) {
            logger.info("find data from localCache ret is null, because key or keyValue is null！");
            return null;
        }
        List<T> valueList = new ArrayList<>();
        if (localCacheMap.containsKey(key)) {
            Map<Object, List<T>> chunkCacheMap = localCacheMap.get(key);
            if (chunkCacheMap.containsKey(keyValue)) {
                List<T> dataObject = chunkCacheMap.get(keyValue);
                if (null != dataObject && !dataObject.isEmpty()) {
                    for (T dataObjectEach : dataObject) {
                        valueList.add(dataObjectEach);
                    }
                }
            }
            else {
                logger.info("localCache do not contains keyValue :{}", keyValue);
            }
        }
        else {
            logger.info("localCache do not contains Key :{}", key);
        }
        return valueList;
    }

    @Override
    public Class getKeyLevel2Class(String key) {
        if (null == key || StringUtils.isEmpty(key)) {
            logger.info("getKeyLevel2Class find input param is null！");
            return null;
        }
        if (localCacheMap.containsKey(key)) {
            Map<Object, List<T>> chunkCacheMap = localCacheMap.get(key);
            if (chunkCacheMap.isEmpty()) {
                logger.info("getKeyLevel2Class find key Level2 is empty！");
                return null;
            }
            else {
                logger.info("ready to getKeyLevel2Class！");
                List<Object> keyList = new ArrayList<>(chunkCacheMap.keySet());
                return keyList.get(0).getClass();
            }
        }
        else {
            logger.info("localCache do not contains Key :{}", key);
            return null;
        }
    }

    @Override
    public T getOne(String key, Object keyValue) {
        if (null == keyValue || StringUtils.isEmpty(key)) {
            logger.info("find data from localCache ret is null, because key or keyValue is null！");
            return null;
        }
        if (localCacheMap.containsKey(key)) {
            Map<Object, List<T>> chunkCacheMap = localCacheMap.get(key);
            // logger.info("chunkCacheMap is {}", BaseCommonUtil.objectToJsonString(chunkCacheMap));
            if (chunkCacheMap.containsKey(keyValue)) {
                List<T> dataObject = chunkCacheMap.get(keyValue);
                if (null != dataObject && !dataObject.isEmpty()) {
                    return dataObject.get(0);
                }
                else {
                    logger.info("No data found from localCache by key:{} keyValue :{}", key, keyValue);
                }
            }
            else {
                logger.info("localCache do not contains keyValue :{}", keyValue);
            }
        }
        else {
            logger.info("localCache do not contains Key :{}", key);
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return localCacheMap.isEmpty();
    }

    /**
     * < 缓存里面有两级检索，这里的size当然仅仅指的一级检索点有多少，至于二级检索点，那就需要传一级检索点来计算了，或者加一个方法来提供所有的二级检索点的个数<br>
     * ======这里的size,put,get，clean，以及抽象类中的一些update等接口可以用来写一个前台界面，====== 界面参考像 redis<br>
     * 那样，以二级树的形式去展示缓存的实际情况，以jackson形式把缓存的内容打出来；<br>
     * 这个界面可以做到完全不需要任何表结构；<br>
     * （1级节点可以用反射或者静态注册的形式查出来）<br>
     * （2级节点可以用反射得到bean再找到缓存再把detail查出来）<br>
     * 同时界面可以提供如下功能）： <br>
     * 1 手动刷新某个指定的缓存（数据库值变了，需要刷新，这时把缓存已经填充开关（isFullLoaded）设置为未填充，再执行一次reload方法，会先清掉所有key对应的value,再给所有的key对应的value
     * reload）<br>
     * 2手动释放某个指定的缓存（当把某个比较大的数据放进了内存，发现内存占过大，要手动释放掉,直接执行clean方法即可）<br>
     * 3禁用/启用所有本地缓存（写一个环境变量或者配置文件或者配置表使得静态变量isUseLocalCache的检查值变成false） <br>
     * 4禁用/启用某一个本地缓存<br>
     * （禁：先clean掉缓存，再手动的将bean的静态isUseLocalCache设置为false，这样查localCache返回永远是空）<br>
     * （启：手动的将bean的静态isUseLocalCache设置为true,然后执行一次刷新动作）<br>
     * 5 展示每个节点的二级节点；<br>
     * 6 展示每个节点的二级节点对应的数据的jackson； <br>
     * 7 统计每种key对应的缓存数据结构占用了多大内存？这个我目前不知道怎么实现><br>
     */
    @Override
    public int size() {
        return localCacheMap.size();
    }

    @Override
    public void put(String key, Object keyValue, Object dataObject) {
        // 1.判断这个数据是否为空或者已经存在
        if (null == dataObject || !isAbsent(key, keyValue, dataObject)) {
            logger.info("put data to localCache failed, because dataObject is null or data is not Absent");
            return;
        }
        if (null == keyValue || StringUtils.isEmpty(key)) {
            logger.info("put data to localCache failed, because key or keyValue is null！");
            return;
        }

        Map<Object, List<T>> chunkCacheMap = localCacheMap.get(key);
        // 2.把数据的二级索引map拼出来，根据dataObject是否是list区分操作
        // 这里有一个点，二级map要保持实时更新，也就是说put操作的二级map要是原来的二级map加上这次put的增量，而不是使用这次的增量去全覆盖二级map
        if (dataObject instanceof List) {
            // logger.info("dataObject instanceof List");
            List<T> valueList = (List<T>) dataObject;
            chunkCacheMap.put(keyValue, valueList);

        }
        else {
            // logger.info("dataObject is not instanceof List");
            List<T> valueList = new ArrayList<>();
            valueList.add((T) dataObject);
            // logger.info("valueList size {}", valueList.size());
            chunkCacheMap.put(keyValue, valueList);
            // logger.info("chunkCacheMap size {}", chunkCacheMap.size());
        }
        localCacheMap.put(key, chunkCacheMap);
    }

    @Override
    public void modify(String key, Object keyValue, Object dataObject) {
        // 1.判断这个数据是否为空或者已经存在
        if (null == dataObject) {
            logger.info("modify data to localCache failed, because dataObject is null！");
            return;
        }
        if (null == keyValue || StringUtils.isEmpty(key)) {
            logger.info("modify data to localCache failed, because key or keyValue is null！");
            return;
        }
        Map<Object, List<T>> chunkCacheMap = localCacheMap.get(key);
        // 2.把数据的二级索引map拼出来，根据dataObject是否是list区分操作
        if (dataObject instanceof List) {
            List<T> valueList = (List<T>) dataObject;
            chunkCacheMap.put(keyValue, valueList);

        }
        else {
            List<T> valueList = new ArrayList<>();
            valueList.add((T) dataObject);
            chunkCacheMap.put(keyValue, valueList);
        }
        localCacheMap.put(key, chunkCacheMap);
    }

    /**
     * < 判断三个入参组成的数据在缓存中是否缺少，如果没有，返回true 暂时的想法是根据1/2级索引去检索，拿到的值非空就认为不缺少数据了> <br>
     *
     * @auther: tang
     * @param key < >
     * @param keyValue < >
     * @param dataObject < >
     * @return < boolean >
     */
    private final synchronized boolean isAbsent(String key, Object keyValue, Object dataObject) {
        if (localCacheMap.containsKey(key)) {
            Map<Object, List<T>> chunkCacheMap = localCacheMap.get(key);
            if (chunkCacheMap.containsKey(keyValue)) {
                List<T> dataObjectIsAbsent = chunkCacheMap.get(keyValue);
                if (null != dataObjectIsAbsent && !dataObjectIsAbsent.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

}
