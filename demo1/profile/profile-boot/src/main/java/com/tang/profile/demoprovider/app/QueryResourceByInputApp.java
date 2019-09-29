package com.tang.profile.demoprovider.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tang.base.util.appEngine.BusinessComponent;
import com.tang.base.util.appEngine.IApplicationComponent;
import com.tang.constant.AppDef;
import com.tang.constant.FCDef;

/**
 * < 查询app,在前台输入一定查询条件时后台的响应app,这个app只包括一个BC，BC内包含4个FC ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/22 <br>
 */
@Component(AppDef.QUERY_RESOURCE_BY_INPUT_APP)
public class QueryResourceByInputApp implements IApplicationComponent {
    /**
     * < 取BC编排的列表 > <br>
     *
     * @return < 获取BC编排的列表 >
     * @auther: tang
     */
    @Override
    public List<BusinessComponent> getBusinessComponents() {
        List<BusinessComponent> bcs = new ArrayList<>();
        BusinessComponent bc = new BusinessComponent();
        bc.addFc(FCDef.INPUT_TO_KEY_FC);
        bc.addFc(FCDef.GET_RESOURCE_BY_KEY_FC);
        bc.addFc(FCDef.GET_RESOURCE_BY_INPUT_FC);
        bc.addFc(FCDef.DEAL_QUERY_RESULT_FC);
        bcs.add(bc);
        return bcs;
    }
}
