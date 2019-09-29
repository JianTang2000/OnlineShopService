package com.tang.profile.demoprovider.functioncomponent.querylocalcachefc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tang.api.billing.LocalCacheService;
import com.tang.base.util.appEngine.ArtifactContext;
import com.tang.base.util.appEngine.IFunctionComponent;
import com.tang.constant.AppMapKeyDef;
import com.tang.constant.FCDef;

/**
 * < 获取本地缓存中key的相关信息 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/12/12 <br>
 */
@Component(FCDef.GET_ALL_LEVEL_1_KEY_FC)
public class GetAllLevel1KeyFC implements IFunctionComponent {

    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(GetAllLevel1KeyFC.class);

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
        logger.info("GetAllLevel1KeyFC start.");
        List<String> allDefKey = localCacheService.queryAllDefKeyLevel1();
        List<String> allDefBeanName = localCacheService.queryAllDefineRepositoryBeanName();
        // 放入上下文
        ArtifactContext.put(AppMapKeyDef.ALL_DEF_KEY_NAME, allDefKey);
        ArtifactContext.put(AppMapKeyDef.ALL_DEF_BEAN_NAME, allDefBeanName);
        logger.info("GetAllLevel1KeyFC end.");
        return true;
    }
}
