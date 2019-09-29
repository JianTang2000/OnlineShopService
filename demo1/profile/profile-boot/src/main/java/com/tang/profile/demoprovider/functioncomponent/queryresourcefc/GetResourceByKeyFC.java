package com.tang.profile.demoprovider.functioncomponent.queryresourcefc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tang.base.util.BaseCommonUtil;
import com.tang.base.util.appEngine.ArtifactContext;
import com.tang.base.util.appEngine.IFunctionComponent;
import com.tang.constant.AppMapKeyDef;
import com.tang.constant.FCDef;
import com.tang.param.billing.GetDataResultParam;
import com.tang.profile.demoprovider.infrastrucrute.DAO.profile.IDemoResourceDAO;
import com.tang.profile.demoprovider.infrastrucrute.model.DemoResourceDetailDto;

/**
 * < 根据唯一标志key从内存中检索资源文件 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/22 <br>
 */
@Component(FCDef.GET_RESOURCE_BY_KEY_FC)
public class GetResourceByKeyFC implements IFunctionComponent {

    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(GetResourceByKeyFC.class);

    /**
     * < >
     */
    @Autowired
    IDemoResourceDAO demoResourceDAO;

    /**
     * FC需要实现的invoke方法。(流程引擎里面会根据FC类名找到对应的bean来执行invoke)
     *
     * @return true-继续执行后续的组件,false-退出后续组件执行
     */
    @Override
    public boolean invoke() {
        logger.info("GetResourceByKeyFC start.");
        // 打印线程号
        long threadId = Thread.currentThread().getId();
        logger.info("GetResourceByKeyFC.threadId is {}", threadId);
        // 获取key
        String inputKey = ArtifactContext.get(AppMapKeyDef.INPUT_KEY);
        logger.info("inputKey found from ArtifactContext is {}", inputKey);
        // 根据key去检索本地缓存，有的话直接往结果塞值，没找到就塞个固定标志，这里先查DB写值，后面改，用以排查流程本身是否正常
        List<GetDataResultParam> resultParamList = ArtifactContext.get(AppMapKeyDef.QUERY_RESOURCE_RESULT);
        List<DemoResourceDetailDto> DemoResourceDetailLists = demoResourceDAO.selectResourceByHotDesc();
        for (DemoResourceDetailDto detailDto : DemoResourceDetailLists) {
            GetDataResultParam getDataResultParam = new GetDataResultParam();
            getDataResultParam.setResourceId(detailDto.getResourceId());
            getDataResultParam.setResourceName(detailDto.getResourceName());
            getDataResultParam.setUserName(detailDto.getUserName());
            getDataResultParam.setHot(detailDto.getHot().toString());
            getDataResultParam.setResourceSize(detailDto.getResourceSize());
            resultParamList.add(getDataResultParam);
        }
        // 将根据key的检索结果放入上下文(get然后修改了对象内容，上下文中就已经被修改了，不用重新put)
        logger.info("QUERY_RESOURCE_RESULT inside ArtifactContext has been updated! , which is {}", BaseCommonUtil.objectToJsonString(resultParamList));
        logger.info("GetResourceByKeyFC end.");
        return true;
    }
}
