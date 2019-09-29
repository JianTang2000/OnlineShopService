package com.tang.profile.demoprovider.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tang.base.util.appEngine.BusinessComponent;
import com.tang.base.util.appEngine.IApplicationComponent;
import com.tang.constant.AppDef;
import com.tang.constant.FCDef;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/12/12 <br>
 */
@Component(AppDef.QUERY_LOCAL_CACHE_INFO_APP)
public class QueryLocalCacheInfoApp implements IApplicationComponent {
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
        bc.addFc(FCDef.GET_ALL_LEVEL_1_KEY_FC);
        bc.addFc(FCDef.QUERY_KEY_CONDITION_FC);
        bcs.add(bc);
        return bcs;
    }
}
