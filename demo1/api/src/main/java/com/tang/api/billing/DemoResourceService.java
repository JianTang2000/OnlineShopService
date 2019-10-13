package com.tang.api.billing;

import com.tang.param.billing.ResourceSearchInputParam;
import com.tang.param.billing.ResourceSearchResultParam;
import com.tang.param.billing.ResourceSearchResultSimpleParam;

import java.util.List;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 */
public interface DemoResourceService {

    /**
     * < 响应前台请求“查询资源文件列表”的接口，支持精确/模糊搜索，指定/不指定 类型/搜索范围，输入为空/非空 <br>
     * 这里会对每次带用户的请求做习惯性统计，存储查询记录，做后续的数据统计分析> <br>
     *
     * @auther: tang
     * @param param < input,search type, rough match >
     * @return < price,name user name,main photo >
     */
    List<ResourceSearchResultSimpleParam> getData(ResourceSearchInputParam param);

    /**
     * < 查询某个指定资源的detail ，包括各种用以展示的关联信息> <br>
     *
     * @auther: tang
     * @param id < the id of the detail resource >
     * @return < price,name user name,main photo and all the rest >
     */
    ResourceSearchResultParam getResourceDetail(Long id);

}
