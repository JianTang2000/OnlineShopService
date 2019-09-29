package com.tang.profile.demoprovider.functioncomponent.queryresourcefc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tang.base.util.BaseCommonUtil;
import com.tang.base.util.ValidateUtil;
import com.tang.base.util.appEngine.ArtifactContext;
import com.tang.base.util.appEngine.IFunctionComponent;
import com.tang.constant.AppMapKeyDef;
import com.tang.constant.FCDef;
import com.tang.param.billing.GetDataResultParam;

/**
 * < 根据input从DB中检索资源文件 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/23 <br>
 */
@Component(FCDef.GET_RESOURCE_BY_INPUT_FC)
public class GetResourceByInputFC implements IFunctionComponent {

    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(GetResourceByInputFC.class);

    /**
     * FC需要实现的invoke方法。(流程引擎里面会根据FC类名找到对应的bean来执行invoke)
     *
     * @return true-继续执行后续的组件,false-退出后续组件执行
     */
    @Override
    public boolean invoke() {
        logger.info("GetResourceByInputFC start.");
        // 打印线程号
        long threadId = Thread.currentThread().getId();
        logger.info("GetResourceByInputFC.threadId is {}", threadId);
        // 校验并打印上‘根据key的检索结果’
        List<GetDataResultParam> GetDataResultParams = ArtifactContext.get(AppMapKeyDef.QUERY_RESOURCE_RESULT);
        logger.info("ArtifactContext.get QUERY_RESOURCE_RESULT is {}", BaseCommonUtil.objectToJsonString(GetDataResultParams));
        // 如果非空，认为缓存查询到了数据，FC直接返回，这里因为上一步写死了，所以~
        if (ValidateUtil.validateNotEmpty(GetDataResultParams)) {
            logger.info("check QUERY_RESOURCE_RESULT has been found from localCache, GetResourceByInputFC doNothing and end!");
            return true;
        }
        else {
            logger.info("check QUERY_RESOURCE_RESULT not found from localCache, start to Get Resource ByInput");
            logger.info("not finished!");
        }
        logger.info("GetResourceByInputFC end.");
        return true;
    }
}
