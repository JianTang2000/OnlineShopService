package com.tang.base.util.appEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * < 一个BusinessComponent就是一个'FCsBeanNames的合集',由多个带业务意义的FC的bean名顺序addFc组成,存在functionComponentList里 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/21 <br>
 */
public class BusinessComponent {

    /**
     * 功能组件列表
     */
    private List<String> functionComponentList;

    /**
     * < 添加FC，添加的顺序就是执行的顺序 > <br>
     *
     * @auther: tang
     * @param functionComponentName < bean名 >
     */
    public void addFc(String functionComponentName) {
        if (null == functionComponentList) {
            functionComponentList = new ArrayList<>();
        }
        functionComponentList.add(functionComponentName);
    }

    /**
     * < 拿到这个BC的'FCsBeanNames的合集'，也就是functionComponentList > <br>
     *
     * @auther: tang
     * @return < >
     */
    public List<String> getFunctionComponentList() {
        return functionComponentList;
    }
}
