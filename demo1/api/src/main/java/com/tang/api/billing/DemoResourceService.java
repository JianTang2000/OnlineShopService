package com.tang.api.billing;

import java.util.List;

import com.tang.param.billing.GetDataInputParam;
import com.tang.param.billing.GetDataResultParam;
import com.tang.param.billing.ResourceDetailParam;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/21 <br>
 */
public interface DemoResourceService {

    /**
     * < 响应前台请求“查询资源文件列表”的接口，支持精确/模糊搜索，指定/不指定 类型/搜索范围，输入为空/非空 这里会对每次带用户的请求做习惯性统计，存储查询记录，做后续的数据统计分析> <br>
     *
     * @auther: tang
     * @param param < >
     * @return < >
     */
    List<GetDataResultParam> getData(GetDataInputParam param);

    /**
     * < 查询某个指定资源的detail ，包括各种用以展示的关联信息> <br>
     *
     * @auther: tang
     * @param id < >
     * @return < >
     */
    ResourceDetailParam getResourceDetail(Long id);

}
