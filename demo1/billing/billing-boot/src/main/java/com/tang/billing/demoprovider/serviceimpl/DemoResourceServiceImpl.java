package com.tang.billing.demoprovider.serviceimpl;

import com.tang.api.billing.DemoResourceService;
import com.tang.billing.demoprovider.infrastrucrute.DAO.billing.IDemoResourceDAO;
import com.tang.billing.demoprovider.infrastrucrute.model.DemoResourceDetailDto;
import com.tang.billing.demoprovider.infrastrucrute.model.DemoResourceDto;
import com.tang.param.billing.ResourceSearchInputParam;
import com.tang.param.billing.ResourceSearchResultParam;
import com.tang.param.billing.ResourceSearchResultSimpleParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("DemoResourceService")
public class DemoResourceServiceImpl implements DemoResourceService {

    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(DemoResourceServiceImpl.class);

    @Autowired
    IDemoResourceDAO demoResourceDAO;

    @Override
    public List<ResourceSearchResultSimpleParam> getData(ResourceSearchInputParam param) {
        List<ResourceSearchResultSimpleParam> ret = new ArrayList<>();
        List<DemoResourceDto> demoResourceDtoList = demoResourceDAO.selectAllResource();
        for (DemoResourceDto dto : demoResourceDtoList) {
            ResourceSearchResultSimpleParam param1 = new ResourceSearchResultSimpleParam();
            param1.setResourceId(dto.getResourceId());
            param1.setResourceName(dto.getResourceName());
            param1.setUserId(dto.getUserId());
            param1.setUserName(dto.getUserName());
            param1.setPrice(dto.getPrice());
            DemoResourceDetailDto dto1 = demoResourceDAO.queryResourceDetailById(dto.getResourceId());
            param1.setPhoto1(dto1.getPhoto1());
            ret.add(param1);
        }
        return ret;
    }

    @Override
    public ResourceSearchResultParam getResourceDetail(Long id) {
        ResourceSearchResultParam ret = new ResourceSearchResultParam();
        DemoResourceDto dto1 = demoResourceDAO.queryResourceById(id);
        DemoResourceDetailDto dto2 = demoResourceDAO.queryResourceDetailById(id);
        BeanUtils.copyProperties(dto1, ret, ResourceSearchResultParam.class);
        BeanUtils.copyProperties(dto2, ret, ResourceSearchResultParam.class);
        return ret;
    }
}
