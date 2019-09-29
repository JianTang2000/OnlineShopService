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
 * < InputToKey FC,将input转成唯一的key ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/22 <br>
 */
@Component(FCDef.INPUT_TO_KEY_FC)
public class InputToKeyFC implements IFunctionComponent {

    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(InputToKeyFC.class);

    /**
     * FC需要实现的invoke方法。(流程引擎里面会根据FC类名找到对应的bean来执行invoke)
     *
     * @return true-继续执行后续的组件,false-退出后续组件执行
     */
    @Override
    public boolean invoke() {
        logger.info("InputToKeyFC start.");
        // 打印线程号
        long threadId = Thread.currentThread().getId();
        logger.info("InputToKeyFC.threadId is {}", threadId);
        // 获取并打印app入参
        GetDataInputParam param = ArtifactContext.get(AppMapKeyDef.GET_DATA_INPUT_PARAM);
        List<GetDataResultParam> resultParamList = ArtifactContext.get(AppMapKeyDef.QUERY_RESOURCE_RESULT);
        logger.info("ArtifactContext.get GET_DATA_INPUT_PARAM is {}", BaseCommonUtil.objectToJsonString(param));
        logger.info("ArtifactContext.get QUERY_RESOURCE_RESULT is {}", BaseCommonUtil.objectToJsonString(resultParamList));
        // 转换成唯一的key，然后继续（下一个FC根据key去反查），这里先随便写个值用以排查流程本身是否正常
        String inputKey = "InputKey" + String.valueOf(threadId);
        logger.info("generate INPUT_KEY is {}, put it to ArtifactContext", inputKey);
        // key放入上下文
        ArtifactContext.put(AppMapKeyDef.INPUT_KEY, inputKey);
        logger.info("InputToKeyFC end.");
        return true;
    }
}
