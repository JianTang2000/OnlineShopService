package com.tang.api.billing;

import com.tang.param.billing.*;

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

    /**
     * @param param param 默认数量为1
     */
    void addToCart(OperateCartParam param);

    void editResourceDetail(ResourceSearchResultParam param);

    String createNewResource(ResourceSearchResultParam param);



    /**
     * @param param  根据用户ID查询，返回包括商品资源得name 展示图，单价，数量 等消息
     */
    List<ResourceSearchResultSimpleParam> queryCart(OperateCartParam param);

    List<ResourceSearchResultSimpleParam> cartDeleteOne(OperateCartParam param);

    List<ResourceSearchResultSimpleParam> cartAddOne(OperateCartParam param);

    List<ResourceSearchResultSimpleParam> cleanCart(OperateCartParam param);

    List<ResourceSearchResultSimpleParam> sureToCheckOut(OperateCartParam param);

    List<OperateOrderParam> queryPreOrder(OperateCartParam param);

    List<OperateOrderParam> queryOrder(OperateCartParam param);

    List<OperateOrderParam> queryAllHisOrder(OperateCartParam param);

    List<OperateOrderParam> queryHisOrder(OperateCartParam param);

    List<OperateOrderParam> adminSearchOrder(OperateCartParam param);

    List<ResourceSearchResultSimpleParam> seeThisOrder(OperateCartParam param);


    List<OperateOrderParam> searchOrder(OperateCartParam param);

    List<OperateOrderParam> queryCheckOrder(OperateCartParam param);

    List<ResourceSearchResultSimpleParam> handleCheck(OperateCartParam param);

    List<ResourceSearchResultSimpleParam> handlePayView(OperateCartParam param);


    List<ResourceSearchResultSimpleParam> checkDeleteOne(OperateCartParam param);

    void sureThisOrder(OperateCartParam param);

    void orderPaid(OperateCartParam param);

    void orderCancel(OperateCartParam param);

    void orderClose(OperateCartParam param);

    void cancelThisOrder(OperateCartParam param);

















    /**
     * 添加图片
     */
    void insertResourcePhoto();

}
