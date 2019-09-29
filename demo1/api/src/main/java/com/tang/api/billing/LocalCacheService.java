package com.tang.api.billing;

import java.util.List;

import com.tang.param.billing.GetDataResultParam;
import com.tang.param.billing.localcachequery.LocalCacheTreeChildrenParam;
import com.tang.param.billing.localcachequery.LocalCacheTreeRootParam;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/12/11 <br>
 */
public interface LocalCacheService {

    /**
     * < handleSave,用入参中的value来> <br>
     *
     * @auther: tang
     * @return < >
     */
    void handleSave(LocalCacheTreeChildrenParam param);

    /**
     * < handleClean"> <br>
     *
     * @auther: tang
     * @return < >
     */
    void handleClean(LocalCacheTreeChildrenParam param);

    /**
     * < 响应前台请求"刷新缓存中某个指定的二级key"> <br>
     *
     * @auther: tang
     * @return < >
     */
    void ReloadByKeyLevel2(LocalCacheTreeChildrenParam param);

    /**
     * < 响应前台请求"全查缓存信息，返回拼好的树形结构"> <br>
     *
     * @auther: tang
     * @return < >
     */
    List<LocalCacheTreeRootParam> getCacheInfo();

    /**
     * < 反射得到LocalCacheKeyLevel1Def的所有属性名（这些属性名其实也就是1级key的名字）> <br>
     * <这里拿到的是所有的key,有些key可能本身在进程内是还没有缓存的> <br>
     *
     * @auther: tang
     */
    List<String> queryAllDefKeyLevel1();

    /**
     * <在进程中有值的缓存结构的1级key> <br>
     * <这里拿到的是所有有值的key,无无值的key，> <br>
     * “有值“ 指的有1级key，可能存在有1级key但是没有2级key的场景，比如说bean只初始化放了1级key但没有load具体数据到2级结构，这时2级结构就是空的。> <br>
     * 
     * @param beanName < beanName >
     * @auther: tang
     * @return < 所有有值的key >
     */
    List<String> queryAllEffKeyLevel1(String beanName);

    /**
     * < 拿到beanName对应的map的1级key对应的所有的2级key > <br>
     *
     * @auther: tang
     * @param beanName < beanName >
     * @param keyLevel1 < 1级key名 >
     * @return < 1级key在进程中存在的2级key名 >
     */
    List<Object> queryAllEffKeyLevel2(String beanName, String keyLevel1);

    /**
     * < 拿到bean名+1/2级key定位到的Value值 > <br>
     *
     * @auther: tang
     * @param beanName < beanName >
     * @param keyLevel1 < 1级key名 >
     * @param keyLevel2 < 2级key名 >
     * @return < bean名+1/2级key定位到的Value值 >
     */
    List<Object> queryValueByBeanNameAndKey12(String beanName, String keyLevel1, Object keyLevel2);

    /**
     * < 反射得到LocalCacheRepositoryBeanNameDef的所有属性名（这些属性名其实也就是缓存map所在的bean的bean名）> <br>
     * <bean是默认的单例，所以对应的缓存map也是唯一的> <br>
     *
     * @auther: tang
     */
    List<String> queryAllDefineRepositoryBeanName();
}
