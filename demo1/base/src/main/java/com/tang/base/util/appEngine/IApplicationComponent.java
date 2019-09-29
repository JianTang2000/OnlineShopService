package com.tang.base.util.appEngine;

import java.util.List;

/**
 * < 调用管理对象，每一个带业务性质的app都需要实现这个接口,同时要以app类名注册bean ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/21 <br>
 */
public interface IApplicationComponent {

    /**
     * < 取BC编排的列表 > <br>
     *
     * @auther: tang
     * @return < 获取BC编排的列表 >
     */
    List<BusinessComponent> getBusinessComponents();
}
