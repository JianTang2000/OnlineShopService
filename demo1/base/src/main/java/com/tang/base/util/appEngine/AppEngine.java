package com.tang.base.util.appEngine;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;

import com.tang.base.util.ApplicationContextHolder;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/21 <br>
 */
public final class AppEngine {

    /**
     * 私有构造方法
     */
    private AppEngine() {

    }

    /**
     * < 日志 >
     */
    private static Logger logger = LoggerFactory.getLogger(AppEngine.class);

    /**
     * < 执行应用 > <br>
     *
     * @auther: tang
     * @param appName 应用名称
     * @param map 应用入参
     */
    public static void invoke(String appName, Map<? extends Object, ? extends Object> map) {
        invokeAppByCodeConfig(appName, map);
    }

    /**
     * < 执行应用 > <br>
     *
     * @auther: tang
     * @param appName 应用名称
     * @param map 应用入参
     */
    private static void invokeAppByCodeConfig(String appName, Map<? extends Object, ? extends Object> map) {
        // 1.从Spring容器中取出app的bean,找不到就报错(app需要实现接口IApplicationComponent并注册bean)
        IApplicationComponent applicationComponent = null;
        try {
            applicationComponent = (IApplicationComponent) ApplicationContextHolder.getBeanByName(appName);
        }
        catch (BeansException e) {
            // 找不到这个bean，说明没有实现：(app需要实现接口IApplicationComponent并注册bean)
            logger.info("find no app bean:{},AppEngine doNothing then end.", appName);
            return;
        }

        // 2.执行APP
        Long startTime = System.currentTimeMillis();
        try {
            ArtifactContext.push(map);
            // 2.1.取BC编排集合,循环多个BC
            List<BusinessComponent> businessComponentList = applicationComponent.getBusinessComponents();
            for (BusinessComponent businessComponent : businessComponentList) {
                // 2.1.1.对每一个BC，取FC编排集合,对每个FC,取出FC的Bean并执行invoke方法,方法有个boolean返回值，用以判断是否中断当前BC
                boolean invokeResult = true;
                List<String> functionComponentList = businessComponent.getFunctionComponentList();
                for (String functionComponentClass : functionComponentList) {
                    logger.info("==================================================================");
                    logger.info("======Begin to invoke fc : {}======", functionComponentClass);
                    logger.info("==================================================================");
                    if (invokeResult) {
                        IFunctionComponent functionComponentBean = (IFunctionComponent) ApplicationContextHolder
                            .getBeanByName(functionComponentClass);
                        Long fcTimeStart = System.currentTimeMillis();
                        invokeResult = functionComponentBean.invoke();
                        logger.info("----------invoke {} fc costs {} millisecond----------", functionComponentClass,
                            System.currentTimeMillis() - fcTimeStart);
                    }
                }
            }
        }
        catch (RuntimeException e) {
            logger.info("invokeApp error!App name is {}", appName, e);

        }
        finally {
            // 始终要弹出栈顶元素
            // 注意，这里如果要拿到某个中间值的话，可以在流程开始之前往里面放
            ArtifactContext.pop();
            logger.info("----------------------------------------------------------------------");
            logger.info("----------invoke {} app costs {} millisecond----------", appName, System.currentTimeMillis() - startTime);
            logger.info("----------------------------------------------------------------------");
        }
    }
}
