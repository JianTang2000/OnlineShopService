package com.tang.base.util.localcache;

/**
 * < 一个基础工具，为多个业务中心提供基础服务（作为base被其他项目maven引用）<br>
 * 提供抽象接口和基础方法，多个业务中心在自己的内部写业务接口，仅需要继承这里的这个抽象接口<br>
 * 业务中心的业务接口提供find方法（比如根据某个/些/无条件去查寻），保证在进程不重启的情况下，find方法只会查询一次物理库，其他的都会直接拿内存的缓存值<br>
 * 业务中心的业务接口提供load方法（find的物理库实现动作）<br>
 * 抽象类会提供基础方法，包括：判断内存中是否有缓存值；如果没有缓存值的load函数（仅有接口，业务侧实现具体）；如果有缓存值的get函数；手动刷新缓存的reload函数等，暂时我还没有想全><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/10/22 <br>
 */
public interface IDataOperate {

    /**
     * < 更新map,在高层中实现。（本来是想写反更新DB的，但暂时还没实现 ）> <br>
     *
     * @auther: tang
     * @param operator < >
     * @param tableName < >
     * @param data < >
     */
    void update(String operator, String tableName, String data);

}
