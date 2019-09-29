package com.tang.base.util.localcache;

import java.util.List;
import java.util.Set;

/**
 * < 设计这个的原因是因为要实现锁获取的原语操作。在不使用synchronized或者lock或者各类lock的情况下，要想做到对某一块代码段的不间断连续执行，
 * 我想到的办法是在应用层层面，通过设计某种数据结构来间接实现这个动作><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/10/22 <br>
 */

/**
 * < 数据结构接口，包括一些基本的map操作方法 ><br>
 * * < 缓存里面有两级检索，这里的size当然仅仅指的一级检索点有多少，至于二级检索点，那就需要传一级检索点来计算了，或者加一个方法来提供所有的二级检索点的个数<br>
 * * ======这里的size,put,get，clean，以及抽象类中的一些update等接口可以用来写一个前台界面，====== 界面参考像 redis<br>
 * * 那样，以二级树的形式去展示缓存的实际情况，以jackson形式把缓存的内容打出来；<br>
 * * 这个界面可以做到完全不需要任何表结构；<br>
 * * （1级节点可以用反射或者静态注册的形式查出来）<br>
 * * （2级节点可以用反射得到bean再找到缓存再把detail查出来）<br>
 * * 同时界面可以提供如下功能）： <br>
 * * 1 手动刷新某个指定的缓存（数据库值变了，需要刷新，这时把缓存已经填充开关（isFullLoaded）设置为未填充，再执行一次reload方法，会先清掉所有key对应的value,再给所有的key对应的value *
 * reload）<br>
 * * 2手动释放某个指定的缓存（当把某个比较大的数据放进了内存，发现内存占过大，要手动释放掉,直接执行clean方法即可）<br>
 * * 3禁用/启用所有本地缓存（写一个环境变量或者配置文件或者配置表使得静态变量isUseLocalCache的检查值变成false） <br>
 * * 4禁用/启用某一个本地缓存<br>
 * * （禁：先clean掉缓存，再手动的将bean的静态isUseLocalCache设置为false，这样查localCache返回永远是空）<br>
 * * （启：手动的将bean的静态isUseLocalCache设置为true,然后执行一次刷新动作）<br>
 * * 5 展示每个节点的二级节点；<br>
 * * 6 展示每个节点的二级节点对应的数据的jackson； <br>
 * * 7 统计每种key对应的缓存数据结构占用了多大内存？这个我目前不知道怎么实现><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/10/22 <br>
 */
public interface LocalCacheMap<T> {

    /**
     * < > <br>
     *
     * @auther: tang
     * @param key < map的一级key名 >
     * @return < >
     */
    LocalCacheMap<T> addKey(String key);

    /**
     * < > <br>
     *
     * @auther: tang
     * @return < map内现有的所有的一级key >
     */
    Set<String> keySet();

    /**
     * < > <br>
     *
     * @auther: tang
     * @return < map指定1级key内现有的所有的2级key >
     */
    Set<Object> keyValueSet(String key);

    /**
     * < > <br>
     *
     * @auther: tang
     * @param key < map的1级key名 >
     * @param keyValue < map的2级key名 >
     * @return < 2级key对应的value是一个List<T> >
     */
    List<T> get(String key, Object keyValue);


    /**
     * < 获取二级key(Object)的class > <br>
     *
     * @auther: tang
     * @param key < map的1级key名 >
     * @return < 二级key(Object)的class >
     */
    Class getKeyLevel2Class(String key);

    /**
     * < > <br>
     *
     * @auther: tang
     * @param key < map的1级key名 >
     * @param keyValue < map的2级key名 >
     * @return < 2级key对应的value是一个List<T>，取第一个返回T >
     */
    T getOne(String key, Object keyValue);

    /**
     * < > <br>
     *
     * @auther: tang
     * @return < map是否为空 >
     */
    boolean isEmpty();

    /**
     * < > <br>
     *
     * @auther: tang
     * @return < 统计map的1级key的数量 >
     */
    int size();

    /**
     * < > <br>
     *
     * @auther: tang
     * @param key < map的1级key名 >
     * @param keyValue < map的2级key名 >
     * @param dataObject < 2级key对应的value >
     */
    void put(String key, Object keyValue, Object dataObject);

    /**
     * < 和put的唯一区别是:put认为缓存有值则不替换直接返回，modif发现有值直接替换 > <br>
     *
     * @auther: tang
     * @param key < map的1级key名 >
     * @param keyValue < map的2级key名 >
     * @param dataObject < 2级key对应的value >
     */
    void modify(String key, Object keyValue, Object dataObject);

}
