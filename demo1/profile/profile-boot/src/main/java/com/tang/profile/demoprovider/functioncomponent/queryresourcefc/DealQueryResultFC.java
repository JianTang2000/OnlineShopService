package com.tang.profile.demoprovider.functioncomponent.queryresourcefc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tang.base.util.BaseCommonUtil;
import com.tang.base.util.appEngine.ArtifactContext;
import com.tang.base.util.appEngine.IFunctionComponent;
import com.tang.constant.AppMapKeyDef;
import com.tang.constant.FCDef;
import com.tang.param.billing.GetDataInputParam;
import com.tang.param.billing.GetDataResultParam;

/**
 * < 记录查询记录，缓存查询结果 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/23 <br>
 */
@Component(FCDef.DEAL_QUERY_RESULT_FC)
public class DealQueryResultFC implements IFunctionComponent {

    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(DealQueryResultFC.class);

    /**
     * FC需要实现的invoke方法。(流程引擎里面会根据FC类名找到对应的bean来执行invoke)
     *
     * @return true-继续执行后续的组件,false-退出后续组件执行
     */
    @Override
    public boolean invoke() {
        logger.info("DealQueryResultFC start.");
        // 打印线程号
        long threadId = Thread.currentThread().getId();
        logger.info("DealQueryResultFC.threadId is {}", threadId);
        GetDataInputParam param = ArtifactContext.get(AppMapKeyDef.GET_DATA_INPUT_PARAM);
        List<GetDataResultParam> resultParamList = ArtifactContext.get(AppMapKeyDef.QUERY_RESOURCE_RESULT);
        String inputKey = ArtifactContext.get(AppMapKeyDef.INPUT_KEY);
        logger.info("ArtifactContext.get GET_DATA_INPUT_PARAM is {}", BaseCommonUtil.objectToJsonString(param));
        logger.info("ArtifactContext.get QUERY_RESOURCE_RESULT is {}", BaseCommonUtil.objectToJsonString(resultParamList));
        logger.info("ArtifactContext.get INPUT_KEY is {}", inputKey);
        return true;
    }
}
