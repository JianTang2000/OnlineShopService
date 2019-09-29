package com.tang.profile.demoprovider.serviceimpl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tang.api.billing.LocalCacheService;
import com.tang.base.util.ApplicationContextHolder;
import com.tang.base.util.BaseCommonUtil;
import com.tang.base.util.appEngine.AppEngine;
import com.tang.base.util.localcache.AbstractFullLoad;
import com.tang.constant.AppDef;
import com.tang.constant.AppMapKeyDef;
import com.tang.constant.LocalCacheRepositoryBeanNameDef;
import com.tang.param.billing.localcachequery.LocalCacheTreeChildrenParam;
import com.tang.param.billing.localcachequery.LocalCacheTreeRootParam;
import com.tang.profile.demoprovider.constant.localCacheConstant.LocalCacheKeyLevel1Def;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/12/11 <br>
 */
@Service("LocalCacheService")
public class LocalCacheServiceImpl implements LocalCacheService {

    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(LocalCacheServiceImpl.class);

    /**
     * < 1拿到bean map name ;key1; key2> <br>
     * < 2拿到bean map name-key1-key2里面Object的class> <br>
     * < 3反转(反射得到class，再new一个classObj然后反转赋值)> <br>
     * < 4存进去> <br>
     *
     * @param param
     * @return < >
     * @auther: tang
     */
    @Override
    public void handleSave(LocalCacheTreeChildrenParam param) {
        // 1拿到bean map name ;key1; key2
        String beanMapName = param.getBeanMapName();
        String keyLevel1 = param.getParentLabel();
        // 2拿到bean map name-key1-key2里面Object的class
        AbstractFullLoad<Object> obj = (AbstractFullLoad<Object>) ApplicationContextHolder.getBeanByName(beanMapName);
        // 因为2级key是Object,所以这里先获取其class
        Class keyLevel2Class = obj.getValueClassByKey(keyLevel1);
        Class<?> cls = obj.getValueByKey(keyLevel1, BaseCommonUtil.StringToObject(param.getLabel(), keyLevel2Class)).getClass();
        // 3反转(反射得到class，再new一个classObj然后反转赋值)
        Object classObj = null; // 反射实例化对象
        try {
            classObj = cls.newInstance();
        }
        catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        classObj = BaseCommonUtil.StringToObject(param.getCacheValue(), cls);
        if (classObj == null) {
            logger.info("classObj is empty then return!");
            return;
        }
        // 4存进去
        obj.modifyValueByKey(keyLevel1, BaseCommonUtil.StringToObject(param.getLabel(), keyLevel2Class), classObj);
    }

    /**
     * < 1 找到是哪个bean map；> <br>
     *
     * @param param
     * @return < >
     * @auther: tang
     */
    @Override
    public void handleClean(LocalCacheTreeChildrenParam param) {
        // 1 找到是哪个bean map
        String beanMapName = param.getBeanMapName();
        if (StringUtils.isEmpty(beanMapName)) {
            logger.info("run ReloadByKeyLevel2 but beanMapName not found then return!");
            return;
        }
        AbstractFullLoad<Object> obj = (AbstractFullLoad<Object>) ApplicationContextHolder.getBeanByName(beanMapName);
        // 2 cleanMap
        obj.cleanMap();
    }

    /**
     * < 响应前台请求"刷新缓存中某个指定的二级key"> <br>
     * < 这里考虑了一段时间，认为还是直接刷新整个map比较合适，如果要支细分到刷新某个Key,那么缓存bean内就要自己实现每个key的刷新方法，要求较高> <br>
     *
     * @param param
     * @return < >
     * @auther: tang
     */
    @Override
    public void ReloadByKeyLevel2(LocalCacheTreeChildrenParam param) {
        // 1 找到是哪个bean map
        String beanMapName = param.getBeanMapName();
        if (StringUtils.isEmpty(beanMapName)) {
            logger.info("run ReloadByKeyLevel2 but beanMapName not found then return!");
            return;
        }
        AbstractFullLoad<Object> obj = (AbstractFullLoad<Object>) ApplicationContextHolder.getBeanByName(beanMapName);
        // 2 isFullLoaded置false
        obj.changeIsFullLoadedFalse();
        // 3 调用reload方法（线程安全且完成后isFullLoaded会自动改为true）
        obj.reload();
        logger.info("run ReloadByKeyLevel2 finished!");
    }

    /**
     * < 响应前台请求"全查缓存信息，返回拼好的树形结构"> <br>
     *
     * @return < >
     * @auther: tang
     */
    @Override
    public List<LocalCacheTreeRootParam> getCacheInfo() {
        logger.info("getCacheInfo start.");
        Map<String, Object> map = new HashMap<String, Object>();
        List<LocalCacheTreeRootParam> finalTreeData = new ArrayList<>();
        map.put(AppMapKeyDef.FINAL_TREE_DATA, finalTreeData); // 在流程中get FINAL_TREE_DATA,然后改变其值,定义处的finalTreeData也会被更改
        AppEngine.invoke(AppDef.QUERY_LOCAL_CACHE_INFO_APP, map);
        logger.info("getCacheInfo end. finalTreeData is {}", finalTreeData);
        return finalTreeData;
    }

    /**
     * < 反射得到LocalCacheKeyLevel1Def的所有属性名（这些属性名其实也就是1级key的名字）> <br>
     * <这里拿到的是所有的key,有些key可能本身在进程内是还没有缓存的> <br>
     *
     * @auther: tang
     * @return < 这个结果只要有静态定义，则不可能为空 >
     */
    @Override
    public List<String> queryAllDefKeyLevel1() {
        List<String> allDefKey = new ArrayList<>();
        Class clazz = LocalCacheKeyLevel1Def.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            allDefKey.add(name);
        }
        logger.info("queryAllDefKeyLevel1 end, allDefKey is {}", BaseCommonUtil.objectToJsonString(allDefKey));
        return allDefKey;
    }

    /**
     * <在进程中有值的缓存结构的1级key> <br>
     * <这里拿到的是所有有值的key,无无值的key，> <br>
     * “有值“ 指的有1级key，可能存在有1级key但是没有2级key的场景，比如说bean只初始化放了1级key但没有load具体数据到2级结构，这时2级结构就是空的。> <br>
     *
     * @param beanName < beanName >
     * @auther: tang
     * @return < 所有有值的key >
     */
    @Override
    public List<String> queryAllEffKeyLevel1(String beanName) {
        logger.info("queryAllEffKeyLevel1 start, beanName is {}", beanName);
        AbstractFullLoad<Object> obj = (AbstractFullLoad<Object>) ApplicationContextHolder.getBeanByName(beanName);
        boolean mapIsEmpty = obj.mapIsEmpty();
        if (mapIsEmpty) {
            logger.info("queryAllEffKeyLevel1 found bean map Is Empty, return null!");
            return null;
        }
        else {
            List<String> result = new ArrayList<>(obj.mapKeySet());
            logger.info("queryAllEffKeyLevel1 result is {}", BaseCommonUtil.objectToJsonString(result));
            return result;
        }
    }

    /**
     * < 拿到beanName对应的map的1级key对应的所有的2级key > <br>
     *
     * @param beanName < beanName >
     * @param keyLevel1 < 1级key名 >
     * @return < 1级key在进程中存在的2级key名 >
     * @auther: tang
     */
    @Override
    public List<Object> queryAllEffKeyLevel2(String beanName, String keyLevel1) {
        logger.info("queryAllEffKeyLevel2 start, beanName is {}, keyLevel1 name is {}", beanName, keyLevel1);
        AbstractFullLoad<Object> obj = (AbstractFullLoad<Object>) ApplicationContextHolder.getBeanByName(beanName);
        Set<Object> objectSet = obj.mapKeyValueSet(keyLevel1);
        List<Object> result = new ArrayList<>(objectSet);
        logger.info("queryAllEffKeyLevel2 result is {}", BaseCommonUtil.objectToJsonString(result));
        return result;
    }

    /**
     * < 拿到bean名+1/2级key定位到的Value值 > <br>
     *
     * @param beanName < beanName >
     * @param keyLevel1 < 1级key名 >
     * @param keyLevel2 < 2级key名 >
     * @return < bean名+1/2级key定位到的Value值 >
     * @auther: tang
     */
    @Override
    public List<Object> queryValueByBeanNameAndKey12(String beanName, String keyLevel1, Object keyLevel2) {
        logger.info("queryValueByBeanNameAndKey12 start, beanName is {}, keyLevel1 is {}, keyLevel2 is {}", beanName, keyLevel1, keyLevel2);
        AbstractFullLoad<Object> obj = (AbstractFullLoad<Object>) ApplicationContextHolder.getBeanByName(beanName);
        List<Object> objectList = obj.getValueByKey(keyLevel1, keyLevel2);
        logger.info("queryValueByBeanNameAndKey12 result is {}", BaseCommonUtil.objectToJsonString(objectList));
        return objectList;
    }

    /**
     * < 反射得到LocalCacheRepositoryBeanNameDef的所有属性名（这些属性名其实也就是缓存map所在的bean的bean名）> <br>
     * <bean是默认的单例，所以对应的缓存map也是唯一的> <br>
     *
     * @auther: tang
     */
    @Override
    public List<String> queryAllDefineRepositoryBeanName() {
        List<String> allDefBeanName = new ArrayList<>();
        Class clazz = LocalCacheRepositoryBeanNameDef.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            allDefBeanName.add(name);
        }
        logger.info("allDefBeanName is {}", BaseCommonUtil.objectToJsonString(allDefBeanName));
        return allDefBeanName;
    }

}
