package com.tang.base.util.appEngine;

/**
 * < 调用管理对象，每一个带业务性质的FC都需要实现这个接口,同时要以FC类名注册bean ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/21 <br>
 */
public interface IFunctionComponent {

    /**
     * FC需要实现的invoke方法。(流程引擎里面会根据FC类名找到对应的bean来执行invoke)
     *
     * @return true-继续执行后续的组件,false-退出后续组件执行
     */
    boolean invoke();
}
