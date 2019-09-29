package com.tang.profile.demoprovider.functioncomponent.querylocalcachefc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tang.api.billing.LocalCacheService;
import com.tang.base.util.BaseCommonUtil;
import com.tang.base.util.ValidateUtil;
import com.tang.base.util.appEngine.ArtifactContext;
import com.tang.base.util.appEngine.IFunctionComponent;
import com.tang.constant.AppMapKeyDef;
import com.tang.constant.FCDef;
import com.tang.param.billing.localcachequery.LocalCacheTreeChildrenParam;
import com.tang.param.billing.localcachequery.LocalCacheTreeParentParam;
import com.tang.param.billing.localcachequery.LocalCacheTreeRootParam;

/**
 * < 查询所有key的相关信息,这个FC拼出了TreeData,所以略有些绕 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/12/12 <br>
 */
@Component(FCDef.QUERY_KEY_CONDITION_FC)
public class QueryKeyConditionFC implements IFunctionComponent {

    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(QueryKeyConditionFC.class);

    /**
     * < >
     */
    @Autowired
    LocalCacheService localCacheService;

    /**
     * FC需要实现的invoke方法。(流程引擎里面会根据FC类名找到对应的bean来执行invoke)
     *
     * @return true-继续执行后续的组件,false-退出后续组件执行
     */
    @Override
    public boolean invoke() {
        logger.info("QueryKeyConditionFC start.");
        List<String> allDefKey = ArtifactContext.get(AppMapKeyDef.ALL_DEF_KEY_NAME);
        List<String> allDefBeanName = ArtifactContext.get(AppMapKeyDef.ALL_DEF_BEAN_NAME);
        // New一个List<LocalCacheTreeRootParam>
        // 对每一个DefBeanName，填充其localCacheTreeRootParam结构
        List<LocalCacheTreeRootParam> finalTreeData = new ArrayList<>();
        for (String beanName : allDefBeanName) {
            logger.info("start to check beanName {}", beanName);
            LocalCacheTreeRootParam localCacheTreeRootParam = new LocalCacheTreeRootParam();
            localCacheTreeRootParam.setLabel(beanName);
            localCacheTreeRootParam.setMapRoughSize("Not clear");
            List<LocalCacheTreeParentParam> childrenOfRoot = new ArrayList<>();
            // 找到这个bean map的所有 Eff 的 1级key
            List<String> allEffKeyLevel1 = localCacheService.queryAllEffKeyLevel1(beanName);
            localCacheTreeRootParam.setKeyLevel1Count(String.valueOf(allEffKeyLevel1.size()));
            localCacheTreeRootParam.setKeyLevel2Count(String.valueOf(this.countKeyLevel2(beanName)));
            // 循环1级key，校验1级key对应的每一个2级key
            for (String effKeyLevel1 : allEffKeyLevel1) {
                logger.info("start to check effKeyLevel1 {}", effKeyLevel1);
                LocalCacheTreeParentParam localCacheTreeParentParam = new LocalCacheTreeParentParam();
                localCacheTreeParentParam.setLabel(effKeyLevel1);
                List<Object> allEffKeyLevel2 = localCacheService.queryAllEffKeyLevel2(beanName, effKeyLevel1);
                // 2级key为空，跳出这个1级key的查找
                if (!ValidateUtil.validateNotEmpty(allEffKeyLevel2)) {
                    logger.info("found allEffKeyLevel2 null, continue.");
                    childrenOfRoot.add(localCacheTreeParentParam);
                    continue;
                }
                // 2级key不为空，循环得到每一个2级key的value
                else {
                    List<LocalCacheTreeChildrenParam> childrenOfLevel1 = new ArrayList<>();
                    for (Object effKeyLevel2 : allEffKeyLevel2) {
                        logger.info("start to check effKeyLevel2 {}", effKeyLevel2);
                        LocalCacheTreeChildrenParam localCacheTreeChildrenParam = new LocalCacheTreeChildrenParam();
                        localCacheTreeChildrenParam.setBeanMapName(beanName);
                        localCacheTreeChildrenParam.setParentLabel(effKeyLevel1);
                        localCacheTreeChildrenParam.setLabel(String.valueOf(effKeyLevel2));
                        // 这里会把Map的value转成Json存起来，
                        // 一般来讲在有2级key的情况下其value不会为空，如果手动界面操作清空了某个value才会为空
                        String cacheValue = checkString(BaseCommonUtil
                            .objectToJsonString(localCacheService.queryValueByBeanNameAndKey12(beanName, effKeyLevel1, effKeyLevel2)));
                        localCacheTreeChildrenParam.setCacheValue(cacheValue);
                        childrenOfLevel1.add(localCacheTreeChildrenParam);
                    }
                    localCacheTreeParentParam.setChildren(childrenOfLevel1);
                }
                childrenOfRoot.add(localCacheTreeParentParam);
            }
            localCacheTreeRootParam.setChildren(childrenOfRoot);
            finalTreeData.add(localCacheTreeRootParam);
        }
        List<LocalCacheTreeRootParam> treeData = ArtifactContext.get(AppMapKeyDef.FINAL_TREE_DATA);
        for (LocalCacheTreeRootParam localCacheTreeRootParam : finalTreeData) {
            treeData.add(localCacheTreeRootParam);
        }
        logger.info("QueryKeyConditionFC.finalTreeData is{}.", BaseCommonUtil.objectToJsonString(treeData));
        logger.info("QueryKeyConditionFC end.");
        return true;
    }

    /**
     * < 计算所有的二级key的数量， > <br>
     *
     * @auther: tang
     * @param beanName < >
     * @return < >
     */
    private int countKeyLevel2(String beanName) {
        logger.info("start to countKeyLevel2");
        int ret = 0;
        List<String> allEffKeyLevel1 = localCacheService.queryAllEffKeyLevel1(beanName);
        for (String effKeyLevel1 : allEffKeyLevel1) {
            List<Object> allEffKeyLevel2 = localCacheService.queryAllEffKeyLevel2(beanName, effKeyLevel1);
            if (!ValidateUtil.validateNotEmpty(allEffKeyLevel2)) {
                continue;
            }
            // 2级key不为空，循环得到每一个2级key的value
            else {
                ret += allEffKeyLevel2.size();
            }
        }
        logger.info("countKeyLevel2 result is {}", ret);
        return ret;
    }

    /**
     * < 如果不为null返回它本身，如果为null返回空字符串 > <br>
     *
     * @auther: tang
     * @param str < >
     * @return < >
     */
    private String checkString(String str) {
        if (null != str) {
            return str;
        }
        return "";
    }
}
