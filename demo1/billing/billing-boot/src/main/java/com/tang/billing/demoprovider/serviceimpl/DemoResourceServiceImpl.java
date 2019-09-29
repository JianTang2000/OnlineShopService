package com.tang.billing.demoprovider.serviceimpl;

import com.tang.api.billing.DemoResourceService;
import com.tang.base.util.BaseCommonUtil;
import com.tang.base.util.appEngine.AppEngine;
import com.tang.billing.demoprovider.infrastrucrute.DAO.billing.IDemoResourceDAO;
import com.tang.billing.demoprovider.infrastrucrute.model.DemoResourceDetailDto;
import com.tang.billing.demoprovider.infrastrucrute.model.DemoResourceDto;
import com.tang.constant.AppDef;
import com.tang.constant.AppMapKeyDef;
import com.tang.param.billing.GetDataInputParam;
import com.tang.param.billing.GetDataResultParam;
import com.tang.param.billing.ResourceDetailParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("DemoResourceService")
public class DemoResourceServiceImpl implements DemoResourceService {

    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(DemoResourceServiceImpl.class);

    /**
     * < >
     */
    @Autowired
    IDemoResourceDAO demoResourceDAO;

    @Override
    public List<GetDataResultParam> getData(GetDataInputParam param) {
        logger.info("getData start, GetDataInputParam is {}", BaseCommonUtil.objectToJsonString(param));
        List<GetDataResultParam> resultParamList = new ArrayList<>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(AppMapKeyDef.GET_DATA_INPUT_PARAM, param); // 入参
        map.put(AppMapKeyDef.QUERY_RESOURCE_RESULT, resultParamList); // 结果
        AppEngine.invoke(AppDef.QUERY_RESOURCE_BY_INPUT_APP, map);
        return resultParamList;
    }

    /**
     * < 查询某个指定资源的detail ，包括各种用以展示的关联信息> <br>
     *
     * @param id < >
     * @return < >
     * @auther: tang
     */
    @Override
    public ResourceDetailParam getResourceDetail(Long id) {
        ResourceDetailParam param = new ResourceDetailParam();
        DemoResourceDto dto = demoResourceDAO.queryResourceById(id);
        DemoResourceDetailDto detailDto = demoResourceDAO.queryResourceDetailById(id);
        param.setResourceId(dto.getResourceId());
        param.setResourceName(dto.getResourceName());
        param.setUserId(dto.getUserId());
        param.setUserName(dto.getUserName());
        param.setResourceDesc(dto.getResourceDesc());
        param.setHot(detailDto.getHot());
        return param;
    }
}
