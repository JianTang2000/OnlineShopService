package com.tang.billing.demoprovider.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.tang.billing.demoprovider.serviceimpl.DemoResourceServiceImpl;
import com.tang.param.billing.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tang.api.billing.DemoResourceService;
import com.tang.base.util.BaseCommonUtil;
import com.tang.base.util.Json;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author tang.jian<br>
 */
@RestController
public class ResourceController {

    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    DemoResourceService demoResourceService;

    @RequestMapping(value = "file/upload0", method = RequestMethod.POST)
    public Map<String, Object> handleFileUpload0(String resourceName,
                                                 String resourceDesc,
                                                 Long price,
                                                 Long classification,
                                                 Long stock) throws IOException {
        ResourceSearchResultParam param = new ResourceSearchResultParam();
        param.setResourceName(resourceName);
        param.setResourceDesc(resourceDesc);
        param.setPrice(price);
        param.setClassification(String.valueOf(classDefault(classification)));
        param.setStock(stockDefault(stock));
        param.setPhotoDetail(DemoResourceServiceImpl.photoDefaultGet());
        param.setPhotoOne(DemoResourceServiceImpl.photoDefaultGet());
        param.setPhotoTwo(DemoResourceServiceImpl.photoDefaultGet());
        param.setPhotoThree(DemoResourceServiceImpl.photoDefaultGet());
        param.setPhotoFour(DemoResourceServiceImpl.photoDefaultGet());
        param.setPhotoFive(DemoResourceServiceImpl.photoDefaultGet());
        String result = demoResourceService.createNewResource(param);
        return Json.success(result);
    }


    @RequestMapping(value = "file/upload1", method = RequestMethod.POST)
    public Map<String, Object> handleFileUpload1(@RequestParam("file1") MultipartFile file1,
                                                 String resourceName,
                                                 String resourceDesc,
                                                 Long price,
                                                 Long classification,
                                                 Long stock) throws IOException {
        byte[] image1 = file1.getBytes();
        ResourceSearchResultParam param = new ResourceSearchResultParam();
        param.setResourceName(resourceName);
        param.setResourceDesc(resourceDesc);
        param.setPrice(price);
        param.setClassification(String.valueOf(classDefault(classification)));
        param.setStock(stockDefault(stock));
        param.setPhotoDetail(image1);
        param.setPhotoOne(image1);
        param.setPhotoTwo(DemoResourceServiceImpl.photoDefaultGet());
        param.setPhotoThree(DemoResourceServiceImpl.photoDefaultGet());
        param.setPhotoFour(DemoResourceServiceImpl.photoDefaultGet());
        param.setPhotoFive(DemoResourceServiceImpl.photoDefaultGet());
        String result = demoResourceService.createNewResource(param);
        return Json.success(result);
    }

    @RequestMapping(value = "file/upload2", method = RequestMethod.POST)
    public Map<String, Object> handleFileUpload2(@RequestParam("file1") MultipartFile file1,
                                                 @RequestParam("file2") MultipartFile file2,
                                                 String resourceName,
                                                 String resourceDesc,
                                                 Long price,
                                                 Long classification,
                                                 Long stock) throws IOException {
        byte[] image1 = file1.getBytes();
        ResourceSearchResultParam param = new ResourceSearchResultParam();
        param.setResourceName(resourceName);
        param.setResourceDesc(resourceDesc);
        param.setPrice(price);
        param.setClassification(String.valueOf(classDefault(classification)));
        param.setStock(stockDefault(stock));
        param.setPhotoDetail(image1);
        param.setPhotoOne(image1);
        param.setPhotoTwo(file2.getBytes());
        param.setPhotoThree(DemoResourceServiceImpl.photoDefaultGet());
        param.setPhotoFour(DemoResourceServiceImpl.photoDefaultGet());
        param.setPhotoFive(DemoResourceServiceImpl.photoDefaultGet());
        String result = demoResourceService.createNewResource(param);
        return Json.success(result);
    }

    @RequestMapping(value = "file/upload3", method = RequestMethod.POST)
    public Map<String, Object> handleFileUpload3(@RequestParam("file1") MultipartFile file1,
                                                 @RequestParam("file2") MultipartFile file2,
                                                 @RequestParam("file3") MultipartFile file3,
                                                 String resourceName,
                                                 String resourceDesc,
                                                 Long price,
                                                 Long classification,
                                                 Long stock) throws IOException {
        byte[] image1 = file1.getBytes();
        ResourceSearchResultParam param = new ResourceSearchResultParam();
        param.setResourceName(resourceName);
        param.setResourceDesc(resourceDesc);
        param.setPrice(price);
        param.setClassification(String.valueOf(classDefault(classification)));
        param.setStock(stockDefault(stock));
        param.setPhotoDetail(image1);
        param.setPhotoOne(image1);
        param.setPhotoTwo(file2.getBytes());
        param.setPhotoThree(file3.getBytes());
        param.setPhotoFour(DemoResourceServiceImpl.photoDefaultGet());
        param.setPhotoFive(DemoResourceServiceImpl.photoDefaultGet());
        String result = demoResourceService.createNewResource(param);
        return Json.success(result);
    }

    @RequestMapping(value = "file/upload4", method = RequestMethod.POST)
    public Map<String, Object> handleFileUpload4(@RequestParam("file1") MultipartFile file1,
                                                @RequestParam("file2") MultipartFile file2,
                                                @RequestParam("file3") MultipartFile file3,
                                                @RequestParam("file4") MultipartFile file4,
                                                String resourceName,
                                                String resourceDesc,
                                                Long price,
                                                Long classification,
                                                Long stock) throws IOException {
        byte[] image1 = file1.getBytes();
        ResourceSearchResultParam param = new ResourceSearchResultParam();
        param.setResourceName(resourceName);
        param.setResourceDesc(resourceDesc);
        param.setPrice(price);
        param.setClassification(String.valueOf(classDefault(classification)));
        param.setStock(stockDefault(stock));
        param.setPhotoDetail(image1);
        param.setPhotoOne(image1);
        param.setPhotoTwo(file2.getBytes());
        param.setPhotoThree(file3.getBytes());
        param.setPhotoFour(file4.getBytes());
        param.setPhotoFive(DemoResourceServiceImpl.photoDefaultGet());
        String result = demoResourceService.createNewResource(param);
        return Json.success(result);
    }


    @RequestMapping(value = "file/upload5", method = RequestMethod.POST)
    public Map<String, Object> handleFileUpload5(@RequestParam("file1") MultipartFile file1,
                                                @RequestParam("file2") MultipartFile file2,
                                                @RequestParam("file3") MultipartFile file3,
                                                @RequestParam("file4") MultipartFile file4,
                                                @RequestParam("file5") MultipartFile file5,
                                                String resourceName,
                                                String resourceDesc,
                                                Long price,
                                                Long classification,
                                                Long stock) throws IOException {
        byte[] image1 = file1.getBytes();
        ResourceSearchResultParam param = new ResourceSearchResultParam();
        param.setResourceName(resourceName);
        param.setResourceDesc(resourceDesc);
        param.setPrice(price);
        param.setClassification(String.valueOf(classDefault(classification)));
        param.setStock(stockDefault(stock));
        param.setPhotoDetail(image1);
        param.setPhotoOne(image1);
        param.setPhotoTwo(file2.getBytes());
        param.setPhotoThree(file3.getBytes());
        param.setPhotoFour(file4.getBytes());
        param.setPhotoFive(file5.getBytes());
        String result = demoResourceService.createNewResource(param);
        return Json.success(result);
    }

    private Long stockDefault(Long in) {
        if (in == null) {
            return 100L;
        }
        return in;
    }

    private Long classDefault(Long in) {
        if (in == null) {
            return 6L;
        }
        return in;
    }


    //http://127.0.0.1:8080/mainView/restfulTest0045

    /**
     * @param param Search by the Input ,//只拿 getPhotoDetail 那一张大图，而不是全部的图片
     * @return Search result
     */
    @PutMapping("mainView/getDataByInput")
    public Map<String, Object> getDataByInput(ResourceSearchInputParam param) {
        logger.info("getDataByInput start, param is {}", BaseCommonUtil.objectToJsonString(param));
        List<ResourceSearchResultSimpleParam> result = demoResourceService.getData(param);
        logger.info("getDataByInput finished");
        return Json.success(result);
    }

    /**
     * @param id the id of the detail resource，拿到全部的图片
     * @return the  resource detail
     */
    @GetMapping("mainView/getResourceDetail/{id}")
    public Map<String, Object> getResourceDetail(@PathVariable Long id) {
        logger.info("getResourceDetail start, id is {}", BaseCommonUtil.objectToJsonString(id));
        ResourceSearchResultParam param = demoResourceService.getResourceDetail(id);
        logger.info("getResourceDetail finished");
        if (param != null) {
            return Json.success(param);
        } else {
            return Json.fail();
        }
    }

    @PutMapping("mainView/editResourceDetail")
    public Map<String, Object> editResourceDetail(ResourceSearchResultParam param) {
        logger.info("editResourceDetail start, param is {}", BaseCommonUtil.objectToJsonString(param));
        demoResourceService.editResourceDetail(param);
        logger.info("editResourceDetail finished");
        return Json.success("OK");
    }


    /**
     * @param param 加入购物车，指定了用户ID，商品Id（数量字段预留使用，目前默认为1），
     * @return OK
     */
    @PutMapping("mainView/addToCart")
    public Map<String, Object> addToCart(OperateCartParam param) {
        logger.info("addToCart start, param is {}", BaseCommonUtil.objectToJsonString(param));
        demoResourceService.addToCart(param);
        logger.info("addToCart finished");
        return Json.success("OK");
    }

    /**
     * @param param 根据用户查他的购物车数据
     * @return OK
     */
    @PutMapping("mainView/queryCart")
    public Map<String, Object> queryCart(OperateCartParam param) {
        logger.info("queryCart start, param is {}", BaseCommonUtil.objectToJsonString(param));
        List<ResourceSearchResultSimpleParam> result = demoResourceService.queryCart(param);
        logger.info("queryCart finished");
        return Json.success(result);
    }

    @PutMapping("mainView/cartDeleteOne")
    public Map<String, Object> cartDeleteOne(OperateCartParam param) {
        logger.info("cartDeleteOne start, param is {}", BaseCommonUtil.objectToJsonString(param));
        List<ResourceSearchResultSimpleParam> result = demoResourceService.cartDeleteOne(param);
        logger.info("cartDeleteOne finished");
        return Json.success(result);
    }

    @PutMapping("mainView/cartAddOne")
    public Map<String, Object> cartAddOne(OperateCartParam param) {
        logger.info("cartAddOne start, param is {}", BaseCommonUtil.objectToJsonString(param));
        List<ResourceSearchResultSimpleParam> result = demoResourceService.cartAddOne(param);
        logger.info("cartAddOne finished");
        return Json.success(result);
    }


    @PutMapping("mainView/cleanCart")
    public Map<String, Object> cleanCart(OperateCartParam param) {
        logger.info("cleanCart start, param is {}", BaseCommonUtil.objectToJsonString(param));
        List<ResourceSearchResultSimpleParam> result = demoResourceService.cleanCart(param);
        logger.info("cleanCart finished");
        return Json.success(result);
    }


    @PutMapping("mainView/sureToCheckOut")
    public Map<String, Object> sureToCheckOut(OperateCartParam param) {
        logger.info("sureToCheckOut start, param is {}", BaseCommonUtil.objectToJsonString(param));
        List<ResourceSearchResultSimpleParam> result = demoResourceService.sureToCheckOut(param);
        logger.info("sureToCheckOut finished");
        return Json.success(result);
    }


    @PutMapping("mainView/queryPreOrder")
    public Map<String, Object> queryPreOrder(OperateCartParam param) {
        logger.info("queryPreOrder start, param is {}", BaseCommonUtil.objectToJsonString(param));
        List<OperateOrderParam> result = demoResourceService.queryPreOrder(param);
        logger.info("queryPreOrder finished");
        return Json.success(result);
    }

    @PutMapping("mainView/queryOrder")
    public Map<String, Object> queryOrder(OperateCartParam param) {
        logger.info("queryOrder start, param is {}", BaseCommonUtil.objectToJsonString(param));
        List<OperateOrderParam> result = demoResourceService.queryOrder(param);
        logger.info("queryOrder finished");
        return Json.success(result);
    }

    //全查（作为admin用户）
    @PutMapping("mainView/queryAllHisOrder")
    public Map<String, Object> queryAllHisOrder(OperateCartParam param) {
        logger.info("queryAllHisOrder start, param is {}", BaseCommonUtil.objectToJsonString(param));
        List<OperateOrderParam> result = demoResourceService.queryAllHisOrder(param);
        logger.info("queryAllHisOrder finished");
        return Json.success(result);
    }

    //仅查此人
    @PutMapping("mainView/queryHisOrder")
    public Map<String, Object> queryHisOrder(OperateCartParam param) {
        logger.info("queryHisOrder start, param is {}", BaseCommonUtil.objectToJsonString(param));
        List<OperateOrderParam> result = demoResourceService.queryHisOrder(param);
        logger.info("queryHisOrder finished");
        return Json.success(result);
    }

    //全查
    @PutMapping("mainView/adminSearchOrder")
    public Map<String, Object> adminSearchOrder(OperateCartParam param) {
        logger.info("adminSearchOrder start, param is {}", BaseCommonUtil.objectToJsonString(param));
        List<OperateOrderParam> result = demoResourceService.adminSearchOrder(param);
        logger.info("adminSearchOrder finished");
        return Json.success(result);
    }

    //seeThisOrder
    @PutMapping("mainView/seeThisOrder")
    public Map<String, Object> seeThisOrder(OperateCartParam param) {
        logger.info("seeThisOrder start, param is {}", BaseCommonUtil.objectToJsonString(param));
        List<ResourceSearchResultSimpleParam> result = demoResourceService.seeThisOrder(param);
        logger.info("seeThisOrder finished");
        return Json.success(result);
    }

    @PutMapping("mainView/orderClose")
    public Map<String, Object> orderClose(OperateCartParam param) {
        logger.info("orderClose start, param is {}", BaseCommonUtil.objectToJsonString(param));
        demoResourceService.orderClose(param);
        logger.info("orderClose finished");
        return Json.success("OK");
    }


    //某个用户的查询
    @PutMapping("mainView/searchOrder")
    public Map<String, Object> searchOrder(OperateCartParam param) {
        logger.info("searchOrder start, param is {}", BaseCommonUtil.objectToJsonString(param));
        List<OperateOrderParam> result = demoResourceService.searchOrder(param);
        logger.info("searchOrder finished");
        return Json.success(result);
    }


    @PutMapping("mainView/queryCheckOrder")
    public Map<String, Object> queryCheckOrder(OperateCartParam param) {
        logger.info("queryCheckOrder start, param is {}", BaseCommonUtil.objectToJsonString(param));
        List<OperateOrderParam> result = demoResourceService.queryCheckOrder(param);
        logger.info("queryCheckOrder finished");
        return Json.success(result);
    }

    @PutMapping("mainView/handleCheck")
    public Map<String, Object> handleCheck(OperateCartParam param) {
        logger.info("handleCheck start, param is {}", BaseCommonUtil.objectToJsonString(param));
        List<ResourceSearchResultSimpleParam> result = demoResourceService.handleCheck(param);
        logger.info("handleCheck finished", BaseCommonUtil.objectToJsonString(result));
        return Json.success(result);
    }


    @PutMapping("mainView/handlePayView")
    public Map<String, Object> handlePayView(OperateCartParam param) {
        logger.info("handlePayView start, param is {}", BaseCommonUtil.objectToJsonString(param));
        List<ResourceSearchResultSimpleParam> result = demoResourceService.handlePayView(param);
        logger.info("handlePayView finished");
        return Json.success(result);
    }

    @PutMapping("mainView/checkDeleteOne")
    public Map<String, Object> checkDeleteOne(OperateCartParam param) {
        logger.info("checkDeleteOne start, param is {}", BaseCommonUtil.objectToJsonString(param));
        List<ResourceSearchResultSimpleParam> result = demoResourceService.checkDeleteOne(param);
        logger.info("checkDeleteOne finished");
        return Json.success(result);
    }


    @PutMapping("mainView/orderPaid")
    public Map<String, Object> orderPaid(OperateCartParam param) {
        logger.info("orderPaid start, param is {}", BaseCommonUtil.objectToJsonString(param));
        demoResourceService.orderPaid(param);
        logger.info("orderPaid finished");
        return Json.success("OK");
    }

    @PutMapping("mainView/sureThisOrder")
    public Map<String, Object> sureThisOrder(OperateCartParam param) {
        logger.info("sureThisOrder start, param is {}", BaseCommonUtil.objectToJsonString(param));
        demoResourceService.sureThisOrder(param);
        logger.info("sureThisOrder finished");
        return Json.success("OK");
    }

    @PutMapping("mainView/cancelThisOrder")
    public Map<String, Object> cancelThisOrder(OperateCartParam param) {
        logger.info("cancelThisOrder start, param is {}", BaseCommonUtil.objectToJsonString(param));
        demoResourceService.cancelThisOrder(param);
        logger.info("cancelThisOrder finished");
        return Json.success("OK");
    }

    @PutMapping("mainView/orderCancel")
    public Map<String, Object> orderCancel(OperateCartParam param) {
        logger.info("orderCancel start, param is {}", BaseCommonUtil.objectToJsonString(param));
        demoResourceService.orderCancel(param);
        logger.info("orderCancel finished");
        return Json.success("OK");
    }


    /**
     * 这是一个rest接口，用来做些简单的测试
     */
    @GetMapping("mainView/restfulTest0045")
    public void test() {
        logger.info("Run restful test");
        demoResourceService.insertResourcePhoto();
        logger.info("Run restful end");
    }


}
