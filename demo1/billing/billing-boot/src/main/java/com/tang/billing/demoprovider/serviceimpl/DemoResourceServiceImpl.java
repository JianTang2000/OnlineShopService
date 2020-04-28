package com.tang.billing.demoprovider.serviceimpl;

import com.tang.api.billing.DemoResourceService;
import com.tang.base.util.BaseCommonUtil;
import com.tang.base.util.DateUtil;
import com.tang.base.util.FileHelper;
import com.tang.base.util.ValidateUtil;
import com.tang.base.util.sequenceutil.SequenceUtil;
import com.tang.base.util.splitWords.SplitSentences;
import com.tang.base.util.splitWords.Words;
import com.tang.billing.demoprovider.infrastrucrute.DAO.billing.IDemoResourceDAO;
import com.tang.billing.demoprovider.infrastrucrute.DAO.billing.IDemoUserDAO;
import com.tang.billing.demoprovider.infrastrucrute.DAO.billing.IUserBasketDAO;
import com.tang.billing.demoprovider.infrastrucrute.DAO.billing.IUserOrderDAO;
import com.tang.billing.demoprovider.infrastrucrute.model.*;
import com.tang.param.billing.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

@Service("DemoResourceService")
public class DemoResourceServiceImpl implements DemoResourceService {

    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(DemoResourceServiceImpl.class);

    @Autowired
    IDemoResourceDAO demoResourceDAO;

    @Autowired
    IDemoUserDAO demoUserDAO;

    static String defaultValue = "\\billing\\billing-boot\\src\\main\\resources\\extraData\\defaultJerry.jpg";

    /**
     * 在不选中的情况下，搜索的动作大概如下（如果不知道什么叫做文字分词，可能会不知道下面在说啥玩意）：
     * <p>
     * 将输入的内容进行中英文分词，得到若干词根，（系统所有的商品，其名称和描述也会进行中英文分词，并形成词典），查询包含所有输入内容词根的商品集合并返回。也就是说输入的内容在分词后，可以是商品名称和描述的部分，可以是全部，可以颠倒顺序，但不可以多。  比如说有商品的名称和描述如下;
     * <p>
     * 名称：佛系背包  ，描述：这是一个背包，它佛系，它贼大，它好用，它便宜
     * <p>
     * 输入如下形式都可以搜出来：
     * <p>
     * 佛系背包，背包，背包佛系， 背包 佛系，背包贼大 ，贼大，佛系 等
     * <p>
     * 但输入如下形式不能搜出来（中文分词对于佛系这种词其实效果真的是不敢保证啊）：
     * <p>
     * 佛系背包贼小（贼小这个属性不匹配），佛背包（佛这个属性不匹配，佛性才行）等
     *
     * @param param < userId, input,search type, rough match ,item>
     * @return data
     */
    @Override
    public List<ResourceSearchResultSimpleParam> getData(ResourceSearchInputParam param) {
        List<ResourceSearchResultSimpleParam> ret = new ArrayList<>();
        //按照类别
        if (param.getItem() != null) {
            List<DemoResourceDto> demoResourceDtoList = demoResourceDAO.selectByItem(param.getItem());
            return getGoodDataByRaw(demoResourceDtoList);
        }
        //按照用户
        if (param.getUserId() != null) {
            List<DemoResourceDto> demoResourceDtoList = demoResourceDAO.selectByUser(param.getUserId());
            return getGoodDataByRaw(demoResourceDtoList);
        }
        //输入为空，且用户，类别都是空，返回全部
        if (StringUtils.isEmpty(param.getInput())) {
            List<DemoResourceDto> demoResourceDtoList = demoResourceDAO.selectAllResource();
            return getGoodDataByRaw(demoResourceDtoList);
        }
        //输入不为空
        else {
            // 指定的是商品
            if (param.getSearchType().equals("1")) {
                //下文123步是一样的，这里把它抽取，放在外面，减少重复代码
                String rawInput = param.getInput().trim();
                Words words = SplitSentences.splitSentences(rawInput, null);
                List<String> inputDictionary = words.wordsWithIgnore();
                List<DemoResourceDto> allResource = demoResourceDAO.selectAllResource();
                Map<String, List<String>> dbDictionary = new HashMap<>();
                for (DemoResourceDto resourceDto : allResource) {
                    dbDictionary.put(resourceDto.getResourceName(),
                            SplitSentences.splitSentences(resourceDto.getResourceName() + resourceDto.getResourceDesc(),
                                    null).wordsWithIgnore());
                }
                // 粗略匹配
                if (param.getRoughMatch()) {
                    //1 查出所有的资源表的资源名称，及其资源描述，；
                    //2 对每一个：资源名+资源描述：进行中文分词加上英文分词，其分词结果是一个词典，放入资源名称为索引的数据结构中；
                    //3 对输入进行中文分词加上英文分词，其分词结果是一个词典，
                    //4 遍历3的词典，每次遍历去2词典中寻找，只要2中存在3，则挑出该用户，并计算这是第几次被挑出
                    //5 遍历完后。所有被挑出的用户即为选中项，相关性高低按挑出次数降序排列
                    Map<String, Integer> fitMap = new HashMap<>();
                    for (String i : inputDictionary) {
                        for (DemoResourceDto resourceDto : allResource) {
                            if (dbDictionary.get(resourceDto.getResourceName()).contains(i)) {
                                if (!fitMap.containsKey(resourceDto.getResourceName())) {
                                    fitMap.put(resourceDto.getResourceName(), 1);
                                } else {
                                    int newValue = fitMap.get(resourceDto.getResourceName()) + 1;
                                    fitMap.put(resourceDto.getResourceName(), newValue);
                                }
                            }
                        }
                    }
                    //判断分词结果是不是空，如果为空使用数据库正则匹配
                    if (!ValidateUtil.validateNotEmpty(fitMap.keySet())) {
                        return getGoodDataByRaw(demoResourceDAO.selectBySomeResourceNameOrDesc(rawInput));
                    }
                    //不为空，返回分词找到的关联用户
                    else {
                        List<DemoResourceDto> demoResourceDtoList = new ArrayList<>();
                        for (String name : fitMap.keySet()) {
                            demoResourceDtoList.addAll(demoResourceDAO.selectByResourceName(name));
                        }
                        return getGoodDataByRaw(demoResourceDtoList);
                    }
                }
                // 非粗略匹配
                //1 查出所有的资源表的资源名称，及其资源描述，；
                //2 对每一个：资源名+资源描述：进行中文分词加上英文分词，其分词结果是一个词典，放入资源名称为索引的数据结构中；
                //3 对输入进行中文分词加上英文分词，其分词结果是一个词典，
                //4 遍历3的词典，每次遍历去2词典中寻找，去掉不存在3中词根的2
                //5 遍历完留下来的2即为选中项
                else {
                    for (String i : inputDictionary) {
                        for (DemoResourceDto resourceDto : allResource) {
                            if (dbDictionary.containsKey(resourceDto.getResourceName())) {
                                if (!dbDictionary.get(resourceDto.getResourceName()).contains(i)) {
                                    dbDictionary.remove(resourceDto.getResourceName());
                                }
                            }
                        }
                    }
                    //判断分词结果是不是空，如果为空使用数据库正则匹配
                    if (!ValidateUtil.validateNotEmpty(dbDictionary.keySet())) {
                        return getGoodDataByRaw(demoResourceDAO.selectBySomeResourceNameOrDesc(rawInput));
                    }
                    //不为空，返回分词找到的关联用户
                    else {
                        List<DemoResourceDto> demoResourceDtoList = new ArrayList<>();
                        for (String name : dbDictionary.keySet()) {
                            demoResourceDtoList.addAll(demoResourceDAO.selectByResourceName(name));
                        }
                        return getGoodDataByRaw(demoResourceDtoList);
                    }
                }
            }
            // 指定的为用户
            else if (param.getSearchType().equals("3")) {
                //下文123步是一样的，这里把它抽取，放在外面，减少重复代码
                String userName = param.getInput().trim();
                Words words = SplitSentences.splitSentences(userName, null);
                List<String> inputDictionary = words.wordsWithIgnore();
                List<String> dbUserNames = demoResourceDAO.selectAllUserNames();
                Map<String, List<String>> dbDictionary = new HashMap<>();
                for (String s : dbUserNames) {
                    dbDictionary.put(s, SplitSentences.splitSentences(s, null).wordsWithIgnore());
                }
                // 粗略匹配
                if (param.getRoughMatch()) {
                    //1 查出所有的资源表的用户，；
                    //2 对每一个用户名进行中文分词加上英文分词，其分词结果是一个词典，放入用户id为索引的数据结构中；
                    //3 对输入进行中文分词加上英文分词，其分词结果是一个词典，
                    //4 遍历3的词典，每次遍历去2词典中寻找，只要2中存在3，则挑出该用户，并计算这是第几次被挑出
                    //5 遍历完后。所有被挑出的用户即为选中项，相关性高低按挑出次数降序排列
                    Map<String, Integer> fitMap = new HashMap<>();
                    for (String i : inputDictionary) {
                        for (String s : dbUserNames) {
                            if (dbDictionary.get(s).contains(i)) {
                                if (!fitMap.containsKey(s)) {
                                    fitMap.put(s, 1);
                                } else {
                                    int newValue = fitMap.get(s) + 1;
                                    fitMap.put(s, newValue);
                                }
                            }
                        }
                    }
                    //判断分词结果是不是空，如果为空使用数据库正则匹配
                    if (!ValidateUtil.validateNotEmpty(fitMap.keySet())) {
                        return getGoodDataByRaw(demoResourceDAO.selectBySomeUserName(userName));
                    }
                    //不为空，返回分词找到的关联用户
                    else {
                        List<DemoResourceDto> demoResourceDtoList = new ArrayList<>();
                        for (String name : fitMap.keySet()) {
                            demoResourceDtoList.addAll(demoResourceDAO.selectByUserName(name));
                        }
                        return getGoodDataByRaw(demoResourceDtoList);
                    }

                }
                // 非粗略匹配
                else {
                    //1 查出所有的资源表的用户，；
                    //2 对每一个用户名进行中文分词加上英文分词，其分词结果是一个词典，放入用户id为索引的数据结构中；
                    //3 对输入进行中文分词加上英文分词，其分词结果是一个词典，
//                   （舍弃） 6 执行SQL正则匹配，得到的结果
                    //4 遍历3的词典，每次遍历去2词典中寻找，去掉不存在3中词根的2
                    //5 遍历完留下来的2即为选中项
                    for (String i : inputDictionary) {
                        for (String s : dbUserNames) {
                            if (dbDictionary.containsKey(s)) {
                                if (!dbDictionary.get(s).contains(i)) {
                                    dbDictionary.remove(s);
                                }
                            }
                        }
                    }
                    //判断分词结果是不是空，如果为空使用数据库正则匹配
                    if (!ValidateUtil.validateNotEmpty(dbDictionary.keySet())) {
                        List<DemoResourceDto> demoResourceDtoList = demoResourceDAO.selectBySomeUserName(userName);
                        return getGoodDataByRaw(demoResourceDtoList);
                    }
                    //不为空，返回分词找到的关联用户
                    else {
                        List<DemoResourceDto> demoResourceDtoList = new ArrayList<>();
                        for (String name : dbDictionary.keySet()) {
                            demoResourceDtoList.addAll(demoResourceDAO.selectByUserName(name));
                        }
                        return getGoodDataByRaw(demoResourceDtoList);
                    }
                }
            } else {
                //都没指定，这个不可能发生，返回全部
                List<DemoResourceDto> demoResourceDtoList = demoResourceDAO.selectAllResource();
                return getGoodDataByRaw(demoResourceDtoList);
            }
        }
    }


    /**
     * @param raw 拆分raw,英文按照空格拆分并得到词根，中文分词也要去掉感叹词
     * @return data
     */
//
    public static List<String> splitString(String raw) {
        List<String> ret = new ArrayList<>();
        ret.add(raw);
        return ret;
    }

    //只拿 getPhotoDetail 而不是全部的图片
    private List<ResourceSearchResultSimpleParam> getGoodDataByRaw(List<DemoResourceDto> demoResourceDtoList) {
        List<ResourceSearchResultSimpleParam> ret = new ArrayList<>();
        for (DemoResourceDto dto : demoResourceDtoList) {
            ResourceSearchResultSimpleParam param1 = new ResourceSearchResultSimpleParam();
            param1.setResourceId(dto.getResourceId());
            param1.setResourceName(dto.getResourceName());
            param1.setUserId(dto.getUserId());
            param1.setUserName(dto.getUserName());
            param1.setPrice(dto.getPrice());
            DemoResourceDetailDto dto1 = demoResourceDAO.queryResourceDetailById(dto.getResourceId());
            try {
                param1.setPhotoDetail(dto1.getPhotoDetail());
            } catch (Exception e) {
                // 找不到照片就放一张默认图片
                param1.setPhotoDetail(getPicture(defaultValue));
            }
            ret.add(param1);
        }
        return ret;
    }

    /**
     * @param halfPath 绝对路径
     * @return 图片的byte
     */
    private byte[] getPicture(String halfPath) {
        String url = System.getProperty("user.dir") + halfPath;
        byte[] pic = null;
        try {
            pic = FileHelper.readFileToByte(url);
        } catch (
                IOException e) {
        }
        return pic;
    }

    @Override
    public ResourceSearchResultParam getResourceDetail(Long id) {
        ResourceSearchResultParam ret = new ResourceSearchResultParam();
        DemoResourceDto dto1 = demoResourceDAO.queryResourceById(id);
        DemoResourceDetailDto dto2 = demoResourceDAO.queryResourceDetailById(id);
        BeanUtils.copyProperties(dto1, ret, ResourceSearchResultParam.class);
        BeanUtils.copyProperties(dto2, ret, ResourceSearchResultParam.class);
        return ret;
    }

    @Override
    public void editResourceDetail(ResourceSearchResultParam param) {
        Long resourceId = param.getResourceId();
        Long price = param.getPrice();
        String desc = param.getResourceDesc();
        Long stock = param.getStock();
        demoResourceDAO.updatePriceAndDetail(resourceId, price, desc);
        demoResourceDAO.updateStock(resourceId, stock);
    }

    static String photo_path = System.getenv("photo_path");
    static String pmml_path = System.getenv("pmml_path");
    static String dir = System.getProperty("user.dir");
    private static String SEQ_RE = "SEQ_RESOURCE_ID";

    private static String getPathHead() {
        if (!StringUtils.isEmpty(photo_path)) {
            return photo_path;
        } else {
            if(!StringUtils.isEmpty(pmml_path)) {
                return pmml_path;
            }
            return dir;
        }
    }

    public static byte[] photoDefaultGet() {
        byte[] pic;
        try {
            pic = FileHelper.readFileToByte(getPathHead() + "/extraData/defaultJerry.jpg");
            return pic;
        } catch (IOException e) {
            return null;
        }
    }


    @Override
    public String createNewResource(ResourceSearchResultParam param) {
        DemoResourceDto resourceDto = demoResourceDAO.selectByReName(param.getResourceName());
        if (resourceDto == null) {
            Long resourceId = SequenceUtil.next(SEQ_RE);
            Date now = DateUtil.getNowTimeSQLDate();
            DemoResourceDto param1 = new DemoResourceDto();
            param1.setResourceId(resourceId);
            param1.setUserId(1L);
            param1.setResourceName(param.getResourceName());
            param1.setUserName("admin");
            param1.setResourceDesc(param.getResourceDesc());
            param1.setCreateDate(now);
            param1.setDeliverType("1");
            param1.setDeliverPrice(0L);
            param1.setPrice(param.getPrice());
            param1.setClassification(param.getClassification());
            demoResourceDAO.insertDemoResource(param1);
            DemoResourceDetailDto param2 = new DemoResourceDetailDto();
            param2.setResourceId(resourceId);
            param2.setStock(param.getStock());
            param2.setHot(0L);
            param2.setPhotoDetail(param.getPhotoDetail());
            param2.setPhotoOne(param.getPhotoOne());
            param2.setPhotoTwo(param.getPhotoTwo());
            param2.setPhotoThree(param.getPhotoThree());
            param2.setPhotoFour(param.getPhotoFour());
            param2.setPhotoFive(param.getPhotoFive());
            demoResourceDAO.insertDemoResourceDetail(param2);
            return "success!";
        }
        else {
//            Long resourceId = resourceDto.getResourceId();
//            byte[] photo = param.getPhotoDetail();
//            if(param.getFileIndex().equals(1L)) {
//                demoResourceDAO.updatePhoto1(resourceId, photo);
//            }
//            else if(param.getFileIndex().equals(2L)) {
//                demoResourceDAO.updatePhoto2(resourceId, photo);
//            }
//            else if(param.getFileIndex().equals(3L)) {
//                demoResourceDAO.updatePhoto3(resourceId, photo);
//            }
//            else if(param.getFileIndex().equals(4L)) {
//                demoResourceDAO.updatePhoto4(resourceId, photo);
//            }
//            else if(param.getFileIndex().equals(5L)) {
//                demoResourceDAO.updatePhoto5(resourceId, photo);
//            }
            return "failed! the same good exists";
        }
    }


    @Autowired
    IUserBasketDAO iUserBasketDAO;

    @Override
    public void addToCart(OperateCartParam param) {
        UserBasketDto exist = iUserBasketDAO.selectByIds(param.getUserId(), param.getResourceId());
        if (exist == null) {
            UserBasketDto dto = new UserBasketDto();
            dto.setUserId(param.getUserId());
            dto.setResourceId(param.getResourceId());
            dto.setItemNum(1L);
            iUserBasketDAO.insertBasket(dto);
        } else {
            iUserBasketDAO.updateUserDetail(param.getUserId(), param.getResourceId(), exist.getItemNum() + 1L);
        }
    }

    @Override
    public List<ResourceSearchResultSimpleParam> queryCart(OperateCartParam param) {
        List<UserBasketDto> exist = iUserBasketDAO.selectByUserId(param.getUserId());
        if (ValidateUtil.validateNotEmpty(exist)) {
            List<DemoResourceDto> demoResourceDtoList = new ArrayList<>();
            for (UserBasketDto dto : exist) {
                DemoResourceDto re = demoResourceDAO.queryResourceById(dto.getResourceId());
                demoResourceDtoList.add(re);
            }
            List<ResourceSearchResultSimpleParam> ret = getGoodDataByRaw(demoResourceDtoList);
            for (ResourceSearchResultSimpleParam in1 : ret) {
                for (UserBasketDto in2 : exist) {
                    if (in2.getResourceId().equals(in1.getResourceId())) {
                        in1.setNum(in2.getItemNum());
                        in1.setSumPrice(in2.getItemNum() * in1.getPrice());
                        continue;
                    }
                }
            }
            return ret;
        } else {
            return null;
        }

    }

    @Override
    public List<ResourceSearchResultSimpleParam> cartDeleteOne(OperateCartParam param) {
        UserBasketDto exist = iUserBasketDAO.selectByIds(param.getUserId(), param.getResourceId());
        // 如果数量为1，直接删除这个记录，否则数量-1
        if (exist.getItemNum().equals(1L)) {
            iUserBasketDAO.deleteByIds(param.getUserId(), param.getResourceId());
        } else {
            exist.setItemNum(exist.getItemNum() - 1L);
            iUserBasketDAO.updateUserDetail(exist.getUserId(), exist.getResourceId(), exist.getItemNum());
        }
        return queryCart(param);
    }

    @Override
    public List<ResourceSearchResultSimpleParam> cartAddOne(OperateCartParam param) {
        UserBasketDto exist = iUserBasketDAO.selectByIds(param.getUserId(), param.getResourceId());
        exist.setItemNum(exist.getItemNum() + 1L);
        iUserBasketDAO.updateUserDetail(exist.getUserId(), exist.getResourceId(), exist.getItemNum());
        return queryCart(param);
    }

    @Override
    public List<ResourceSearchResultSimpleParam> cleanCart(OperateCartParam param) {
        iUserBasketDAO.deleteByUserId(param.getUserId());
        return queryCart(param);
    }

    @Override
    public List<ResourceSearchResultSimpleParam> sureToCheckOut(OperateCartParam param) {
        //检查是否有当前3种types组合得预订单存在，如果存在，这个预订单操作将会不被接受(直接返回购物车数据，前台判断非空，则表示失败)
        List<PreOrderDto> dtoList = iUserBasketDAO.selectPreByIdAndTypes(
                param.getUserId(), param.getDeliverType(), param.getDeliverTime(), param.getAddressType());
        //存在
        if (ValidateUtil.validateNotEmpty(dtoList)) {
            return queryCart(param);
        } else {
            List<UserBasketDto> exist = iUserBasketDAO.selectByUserId(param.getUserId());
            for (UserBasketDto dto : exist) {
                PreOrderDto preOrderDto = new PreOrderDto();
                preOrderDto.setUserId(dto.getUserId());
                preOrderDto.setResourceId(dto.getResourceId());
                preOrderDto.setItemNum(dto.getItemNum());
                preOrderDto.setDeliverType(param.getDeliverType());
                preOrderDto.setDeliverTime(param.getDeliverTime());
                preOrderDto.setAddressType(param.getAddressType());
                iUserBasketDAO.insertPreOrder(preOrderDto);
            }
            // 清空购物车，再返回空的购物车列表
            iUserBasketDAO.deleteByUserId(param.getUserId());
            return queryCart(param);
        }
    }

    @Override
    public List<ResourceSearchResultSimpleParam> handleCheck(OperateCartParam param) {
        Long userId = param.getUserId();
        String deliverType = param.getDeliverType();
        String deliverTime = param.getDeliverTime();
        String addressType = param.getAddressType();
        List<PreOrderDto> exist = iUserBasketDAO.selectPreByIdAndTypes(userId, deliverType, deliverTime, addressType);

        List<DemoResourceDto> demoResourceDtoList = new ArrayList<>();
        for (PreOrderDto dto : exist) {
            DemoResourceDto re = demoResourceDAO.queryResourceById(dto.getResourceId());
            demoResourceDtoList.add(re);
        }
        List<ResourceSearchResultSimpleParam> ret = getGoodDataByRaw(demoResourceDtoList);
        for (ResourceSearchResultSimpleParam in1 : ret) {
            in1.setUserId(userId);
            for (PreOrderDto in2 : exist) {
                if (in2.getResourceId().equals(in1.getResourceId())) {
                    in1.setNum(in2.getItemNum());
                    in1.setSumPrice(in2.getItemNum() * in1.getPrice());
                    continue;
                }
            }
        }
        return ret;

    }

    @Override
    public List<ResourceSearchResultSimpleParam> handlePayView(OperateCartParam param) {
        Long userId = param.getUserId();
        String deliverType = param.getDeliverType();
        String deliverTime = param.getDeliverTime();
        String addressType = param.getAddressType();
        Long extraCost = 0L;
        Long discount = 0L;
        String comment = "";
        List<UserOrderDto> exist = iUserOrderDAO.selectOrdByIdAndTypes(userId, deliverType, deliverTime, addressType);

        List<DemoResourceDto> demoResourceDtoList = new ArrayList<>();
        for (UserOrderDto dto : exist) {
            DemoResourceDto re = demoResourceDAO.queryResourceById(dto.getResourceId());
            demoResourceDtoList.add(re);
            extraCost = dto.getExtraCost();
            discount = dto.getDiscount();
            comment = dto.getOrderComment();
        }
        List<ResourceSearchResultSimpleParam> ret = getGoodDataByRaw(demoResourceDtoList);
        for (ResourceSearchResultSimpleParam in1 : ret) {
            in1.setUserId(userId);
            for (UserOrderDto in2 : exist) {
                if (in2.getResourceId().equals(in1.getResourceId())) {
                    in1.setNum(in2.getItemNum());
                    in1.setSumPrice(in2.getItemNum() * in1.getPrice());
                    continue;
                }
            }
        }

        Long sumPay = 0L;
        for (ResourceSearchResultSimpleParam in : ret) {
            sumPay += in.getSumPrice();
        }
        for (ResourceSearchResultSimpleParam in : ret) {
            in.setDiscount(discount);
            in.setComment(comment);
            in.setExtraCost(extraCost);
            in.setSumPay(positiveLong(sumPay + extraCost - discount));
        }
        return ret;

    }

    @Override
    public List<ResourceSearchResultSimpleParam> seeThisOrder(OperateCartParam param) {
        Long extraCost = 0L;
        Long discount = 0L;
        String comment = "";
        Long sumPay = 0L;
        Long orderId = param.getOrderId();
        Long userId = param.getUserId();
        String state = "";
        List<UserOrderHisDto> exist = iUserOrderDAO.selectByOrderId(orderId);
        List<DemoResourceDto> demoResourceDtoList = new ArrayList<>();
        for (UserOrderHisDto dto : exist) {
            DemoResourceDto re = demoResourceDAO.queryResourceById(dto.getResourceId());
            demoResourceDtoList.add(re);
            extraCost = dto.getExtraCost();
            discount = dto.getDiscount();
            comment = dto.getOrderComment();
            sumPay = dto.getSumPay();
            state = dto.getState();
        }
        List<ResourceSearchResultSimpleParam> ret = getGoodDataByRaw(demoResourceDtoList);
        for (ResourceSearchResultSimpleParam in1 : ret) {
            in1.setUserId(userId);
            in1.setDiscount(discount);
            in1.setComment(comment);
            in1.setExtraCost(extraCost);
            in1.setSumPay(sumPay);
            in1.setState(state);
            for (UserOrderHisDto in2 : exist) {
                if (in2.getResourceId().equals(in1.getResourceId())) {
                    in1.setNum(in2.getItemNum());
                    in1.setSumPrice(in2.getItemNum() * in1.getPrice());
                    continue;
                }
            }
        }
        return ret;

    }


    @Override
    public List<ResourceSearchResultSimpleParam> checkDeleteOne(OperateCartParam param) {
        Long userId = param.getUserId();
        Long resourceId = param.getResourceId();
        String deliverType = param.getDeliverType();
        String deliverTime = param.getDeliverTime();
        String addressType = param.getAddressType();
        PreOrderDto preOrderDto = iUserBasketDAO.selectPreByIdsAndTypes(userId, resourceId, deliverType, deliverTime, addressType);
        if (preOrderDto.getItemNum().equals(1L)) {
            iUserBasketDAO.deletePreByIdsTypes(userId, resourceId, deliverType, deliverTime, addressType);
        } else {
            preOrderDto.setItemNum(preOrderDto.getItemNum() - 1L);
            iUserBasketDAO.updatePreOrder(userId, resourceId, deliverType, deliverTime, addressType, preOrderDto.getItemNum());
        }
        return handleCheck(param);

    }

    @Autowired
    IUserOrderDAO iUserOrderDAO;

    @Override
    public void sureThisOrder(OperateCartParam param) {
        Long userId = param.getUserId();
        String deliverType = param.getDeliverType();
        String deliverTime = param.getDeliverTime();
        String addressType = param.getAddressType();
        Long extraCost = param.getExtraCost();
        Long discount = param.getDiscount();
        String comment = param.getComment();
        List<PreOrderDto> exist = iUserBasketDAO.selectPreByIdAndTypes(userId, deliverType, deliverTime, addressType);
        for (PreOrderDto in : exist) {
            UserOrderDto dto = new UserOrderDto();
            dto.setUserId(userId);
            dto.setResourceId(in.getResourceId());
            dto.setItemNum(in.getItemNum());
            dto.setDeliverType(deliverType);
            dto.setDeliverTime(deliverTime);
            dto.setAddressType(addressType);
            dto.setExtraCost(extraCost);
            dto.setDiscount(discount);
            dto.setOrderComment(comment);
            iUserOrderDAO.insertUserOrder(dto);
        }
        iUserBasketDAO.deletePreByIdTypes(userId, deliverType, deliverTime, addressType);
    }

    // 已付款，存到his中，状态为已付款
    @Override
    public void orderPaid(OperateCartParam param) {
        Long userId = param.getUserId();
        String deliverTime = param.getDeliverTime();
        String deliverType = param.getDeliverType();
        String addressType = param.getAddressType();
        Long extraCost = param.getExtraCost();
        Long discount = param.getDiscount();
        String comment = param.getComment();
        Long sumPay = param.getSumPay();
        List<UserOrderDto> exist = iUserOrderDAO.selectOrdByIdAndTypes(userId, deliverType, deliverTime, addressType);
        Long orderId = SequenceUtil.next(orderIdSeq);
        Date now = DateUtil.getNowTimeSQLDate();
        for (UserOrderDto in : exist) {
            UserOrderHisDto dto = new UserOrderHisDto();
            dto.setOrderId(orderId);
            dto.setUserId(userId);
            dto.setResourceId(in.getResourceId());
            dto.setItemNum(in.getItemNum());
            dto.setDeliverType(deliverType);
            dto.setDeliverTime(deliverTime);
            dto.setAddressType(addressType);
            dto.setExtraCost(extraCost);
            dto.setDiscount(discount);
            dto.setOrderComment(defaultComment);
            dto.setState("C"); //--A saler canceled ;B user canceled ;C user paid ;D saler closed
            dto.setStateComment1(defaultComment);
            dto.setStateComment2(defaultComment);
            dto.setStateComment3(comment);
            dto.setStateComment4(defaultComment);
            dto.setCreateDate(now);
            dto.setSumPay(sumPay);
            iUserOrderDAO.insertUserOrderHis(dto);
        }
        iUserOrderDAO.deleteByIdTypes(userId, deliverType, deliverTime, addressType);
    }


    private static String defaultComment = "default comment";
    private static String orderIdSeq = "HIS_AND_PAID_ORDER_ID";

    //这是商家再审核界面选择取消顶单
    @Override
    public void cancelThisOrder(OperateCartParam param) {
        String deliverType = param.getDeliverType();
        String deliverTime = param.getDeliverTime();
        String addressType = param.getAddressType();
        Long extraCost = param.getExtraCost();
        Long discount = param.getDiscount();
        String comment = param.getComment();
        Long userId = param.getUserId();
        List<PreOrderDto> exist = iUserBasketDAO.selectPreByIdAndTypes(userId, deliverType, deliverTime, addressType);
        Long orderId = SequenceUtil.next(orderIdSeq);
        Date now = DateUtil.getNowTimeSQLDate();
        for (PreOrderDto in : exist) {
            UserOrderHisDto dto = new UserOrderHisDto();
            dto.setOrderId(orderId);
            dto.setResourceId(in.getResourceId());
            dto.setUserId(userId);
            dto.setItemNum(in.getItemNum());
            dto.setDeliverType(deliverType);
            dto.setDeliverTime(deliverTime);
            dto.setAddressType(addressType);
            dto.setExtraCost(extraCost);
            dto.setDiscount(discount);
            dto.setOrderComment(defaultComment);
            dto.setState("A");
            dto.setStateComment1(comment);
            dto.setStateComment2(defaultComment);
            dto.setStateComment3(defaultComment);
            dto.setStateComment4(defaultComment);
            dto.setCreateDate(now);
            dto.setSumPay(0L);
            iUserOrderDAO.insertUserOrderHis(dto);
        }
        iUserBasketDAO.deletePreByIdTypes(userId, deliverType, deliverTime, addressType);
    }

    @Override
    public void orderClose(OperateCartParam param) {
        Long orderId = param.getOrderId();
        iUserOrderDAO.updateStateToD(orderId);
    }


    //这是用户在付款界面选择取消
    @Override
    public void orderCancel(OperateCartParam param) {
        Long sumPay = param.getSumPay();
        String deliverTime = param.getDeliverTime();
        Long userId = param.getUserId();
        String deliverType = param.getDeliverType();
        String addressType = param.getAddressType();
        Long extraCost = param.getExtraCost();
        Long discount = param.getDiscount();
        String comment = param.getComment();
        List<UserOrderDto> exist = iUserOrderDAO.selectOrdByIdAndTypes(userId, deliverType, deliverTime, addressType);
        Long orderId = SequenceUtil.next(orderIdSeq);
        Date now = DateUtil.getNowTimeSQLDate();
        for (UserOrderDto in : exist) {
            UserOrderHisDto dto = new UserOrderHisDto();
            dto.setOrderId(orderId);
            dto.setUserId(userId);
            dto.setResourceId(in.getResourceId());
            dto.setItemNum(in.getItemNum());
            dto.setDeliverType(deliverType);
            dto.setDeliverTime(deliverTime);
            dto.setAddressType(addressType);
            dto.setExtraCost(extraCost);
            dto.setDiscount(discount);
            dto.setOrderComment(comment); // A 和B 类型的取消，仅存商家的评论 在这个字段里
            dto.setState("B"); //--A saler canceled ;B user canceled ;C user paid ;D saler closed
            dto.setStateComment1(defaultComment);
            dto.setStateComment2(defaultComment);
            dto.setStateComment3(defaultComment);
            dto.setStateComment4(defaultComment);
            dto.setCreateDate(now);
            dto.setSumPay(sumPay);
            iUserOrderDAO.insertUserOrderHis(dto);
        }
        iUserOrderDAO.deleteByIdTypes(userId, deliverType, deliverTime, addressType);
    }


    // 作为管理员，查看所有 pre_order的数据
    @Override
    public List<OperateOrderParam> queryCheckOrder(OperateCartParam param) {
        List<OperateOrderParam> ret = new ArrayList<>();
        List<PreOrderDto> preOrders = iUserBasketDAO.selectAllPreUserIds();
        for (PreOrderDto pre : preOrders) {
            OperateCartParam pa = new OperateCartParam();
            pa.setUserId(pre.getUserId());
            List<OperateOrderParam> one = queryPreOrder(pa);
            ret.addAll(one);
        }
        for (OperateOrderParam in : ret) {
            in.setTypeDesc("待审核");
        }
        return ret;
    }

    @Override
    public List<OperateOrderParam> queryOrder(OperateCartParam param) {
        List<UserOrderDto> typesList = iUserOrderDAO.selectAllTypesById(param.getUserId());
        if (!ValidateUtil.validateNotEmpty(typesList)) {
            return null;
        }
        List<OperateOrderParam> ret = new ArrayList<>();
        for (UserOrderDto type : typesList) {
            List<UserOrderDto> dtoList = iUserOrderDAO.selectOrdByIdAndTypes(
                    param.getUserId(), type.getDeliverType(), type.getDeliverTime(), type.getAddressType());
            OperateOrderParam param1 = new OperateOrderParam();
            param1.setTypeDesc("待付款");
            param1.setUserId(param.getUserId());
            param1.setDeliverType(type.getDeliverType());
            param1.setDeliverTime(type.getDeliverTime());
            param1.setAddressType(type.getAddressType());
            param1.setItemsNum((long) dtoList.size());
            DemoUserDetailDto userDetailDto = demoUserDAO.selectUserDetailById(param.getUserId());

            DemoUserDto userDtoR = demoUserDAO.selectUserById(param.getUserId());
            Long phoneNumberR = userDtoR.getPhoneNumber();

            String address2 = userDetailDto.getAddress2Line1() + userDetailDto.getAddress2Line2() + userDetailDto.getAddress2PostCode();
            String address3 = userDetailDto.getAddress3Line1() + userDetailDto.getAddress3Line2() + userDetailDto.getAddress3PostCode();
            String address1 = userDetailDto.getAddress1Line1() + userDetailDto.getAddress1Line2() + userDetailDto.getAddress1PostCode();
            if (type.getAddressType().equals("1")) {
                param1.setOrderDetail(switchDeliverType(phoneNumberR, type.getDeliverType()) + ",  " +
                        switchDeliverTime(type.getDeliverTime()) + ", " + address1);
            } else if (type.getAddressType().equals("2")) {
                param1.setOrderDetail(switchDeliverType(phoneNumberR, type.getDeliverType()) + ",  " +
                        switchDeliverTime(type.getDeliverTime()) + ", " + address2);
            } else {
                param1.setOrderDetail(switchDeliverType(phoneNumberR, type.getDeliverType()) + ",  " +
                        switchDeliverTime(type.getDeliverTime()) + ", " + address3);
            }
            Long sumPrice = 0L;
            for (UserOrderDto dto : dtoList) {
                param1.setExtraCost(dto.getExtraCost());
                param1.setDiscount(dto.getDiscount());
                DemoResourceDto re = demoResourceDAO.queryResourceById(dto.getResourceId());
                sumPrice += (re.getPrice() * dto.getItemNum());

            }
            param1.setItemsPrice(sumPrice);
            Long sumPay = sumPrice + param1.getExtraCost() - param1.getDiscount();
            param1.setSumPay(positiveLong(sumPay));

            ret.add(param1);
        }
        return ret;
    }


    //                        <el-radio :label="5">全部</el-radio>
//                    <el-radio :label="1">已付款</el-radio>
//                    <el-radio :label="2">买家取消</el-radio>
//                    <el-radio :label="3">卖家取消</el-radio>
//                    <el-radio :label="4">已完成</el-radio>
    @Override
    public List<OperateOrderParam> queryAllHisOrder(OperateCartParam param) {
        Long radio = param.getRadio();
        String state = radioToState(radio);
        List<UserOrderHisDto> orderHisDtoList;
        if (state.equals("E")) {
            orderHisDtoList = iUserOrderDAO.selectByOrderAllState();
        } else {
            orderHisDtoList = iUserOrderDAO.selectByOrderByState(state);
        }
        if (!ValidateUtil.validateNotEmpty(orderHisDtoList)) {
            return null;
        }
        List<OperateOrderParam> ret = new ArrayList<>();
        for (UserOrderHisDto orderHis : orderHisDtoList) {
            OperateOrderParam param1 = new OperateOrderParam();
            param1.setOrderId(orderHis.getOrderId());
            param1.setUserId(orderHis.getUserId());
            param1.setDeliverType(orderHis.getDeliverType());
            param1.setDeliverTime(orderHis.getDeliverTime());
            param1.setAddressType(orderHis.getAddressType());
            param1.setSumPay(orderHis.getSumPay());
            param1.setTypeDesc(StateToDesc(orderHis.getState()));
            param1.setHisComment(retStateComment(orderHis));
            DemoUserDetailDto userDetailDto = demoUserDAO.selectUserDetailById(orderHis.getUserId());

            DemoUserDto userDtoR = demoUserDAO.selectUserById(orderHis.getUserId());
            Long phoneNumberR = userDtoR.getPhoneNumber();

            String address2 = userDetailDto.getAddress2Line1() + ",  " + userDetailDto.getAddress2Line2() + ",  " + userDetailDto.getAddress2PostCode();
            String address3 = userDetailDto.getAddress3Line1() + ",  " + userDetailDto.getAddress3Line2() + ",  " + userDetailDto.getAddress3PostCode();
            String address1 = userDetailDto.getAddress1Line1() + ",  " + userDetailDto.getAddress1Line2() + ",  " + userDetailDto.getAddress1PostCode();
            if (orderHis.getAddressType().equals("1")) {
                param1.setOrderDetail(switchDeliverType(phoneNumberR, orderHis.getDeliverType()) + ",  " +
                        switchDeliverTime(orderHis.getDeliverTime()) + ", " + address1);
            } else if (orderHis.getAddressType().equals("2")) {
                param1.setOrderDetail(switchDeliverType(phoneNumberR, orderHis.getDeliverType()) + ",  " +
                        switchDeliverTime(orderHis.getDeliverTime()) + ", " + address2);
            } else {
                param1.setOrderDetail(switchDeliverType(phoneNumberR, orderHis.getDeliverType()) + ",  " +
                        switchDeliverTime(orderHis.getDeliverTime()) + ", " + address3);
            }
            DemoUserDto userDto = demoUserDAO.selectUserById(orderHis.getUserId());
            param1.setUserName(userDto.getUserName());
            ret.add(param1);
        }
        return ret;
    }

    @Override
    public List<OperateOrderParam> queryHisOrder(OperateCartParam param) {
        Long radio = param.getRadio();
        Long userId = param.getUserId();
        String state = radioToState(radio);
        List<UserOrderHisDto> orderHisDtoList;
        if (state.equals("E")) {
            orderHisDtoList = iUserOrderDAO.selectByOrderIdAllState(userId);
        } else {
            orderHisDtoList = iUserOrderDAO.selectByOrderByIdState(state, userId);
        }
        if (!ValidateUtil.validateNotEmpty(orderHisDtoList)) {
            return null;
        }
        List<OperateOrderParam> ret = new ArrayList<>();
        for (UserOrderHisDto orderHis : orderHisDtoList) {
            OperateOrderParam param1 = new OperateOrderParam();
            param1.setOrderId(orderHis.getOrderId());
            param1.setUserId(orderHis.getUserId());
            param1.setDeliverType(orderHis.getDeliverType());
            param1.setDeliverTime(orderHis.getDeliverTime());
            param1.setAddressType(orderHis.getAddressType());
            param1.setSumPay(orderHis.getSumPay());
            param1.setTypeDesc(StateToDesc(orderHis.getState()));
            param1.setHisComment(retStateComment(orderHis));
            DemoUserDetailDto userDetailDto = demoUserDAO.selectUserDetailById(orderHis.getUserId());

            DemoUserDto userDtoR = demoUserDAO.selectUserById(orderHis.getUserId());
            Long phoneNumberR = userDtoR.getPhoneNumber();

            String address2 = userDetailDto.getAddress2Line1() + ",  " + userDetailDto.getAddress2Line2() + ",  " + userDetailDto.getAddress2PostCode();
            String address3 = userDetailDto.getAddress3Line1() + ",  " + userDetailDto.getAddress3Line2() + ",  " + userDetailDto.getAddress3PostCode();
            String address1 = userDetailDto.getAddress1Line1() + ",  " + userDetailDto.getAddress1Line2() + ",  " + userDetailDto.getAddress1PostCode();
            if (orderHis.getAddressType().equals("1")) {
                param1.setOrderDetail(switchDeliverType(phoneNumberR, orderHis.getDeliverType()) + ",  " +
                        switchDeliverTime(orderHis.getDeliverTime()) + ", " + address1);
            } else if (orderHis.getAddressType().equals("2")) {
                param1.setOrderDetail(switchDeliverType(phoneNumberR, orderHis.getDeliverType()) + ",  " +
                        switchDeliverTime(orderHis.getDeliverTime()) + ", " + address2);
            } else {
                param1.setOrderDetail(switchDeliverType(phoneNumberR, orderHis.getDeliverType()) + ",  " +
                        switchDeliverTime(orderHis.getDeliverTime()) + ", " + address3);
            }
            DemoUserDto userDto = demoUserDAO.selectUserById(orderHis.getUserId());
            param1.setUserName(userDto.getUserName());
            ret.add(param1);
        }
        return ret;
    }

    @Override
    public List<OperateOrderParam> adminSearchOrder(OperateCartParam param) {
        String input = param.getInput();
        String select = param.getSelect();
        String inputUserName;
        Long inputOrderId;
        if (select.equals("2")) {
            inputUserName = input;
            DemoUserDto userDto = demoUserDAO.selectUserByName(inputUserName);
            if (userDto == null) {
                return null;
            }
            Long userId = userDto.getUserId();
            List<UserOrderHisDto> orderHisDtoList = iUserOrderDAO.selectHisByUserId(userId);
            if (!ValidateUtil.validateNotEmpty(orderHisDtoList)) {
                return null;
            }
            List<OperateOrderParam> ret = new ArrayList<>();
            for (UserOrderHisDto orderHis : orderHisDtoList) {
                OperateOrderParam param1 = new OperateOrderParam();
                param1.setOrderId(orderHis.getOrderId());
                param1.setUserId(orderHis.getUserId());
                param1.setDeliverType(orderHis.getDeliverType());
                param1.setDeliverTime(orderHis.getDeliverTime());
                param1.setAddressType(orderHis.getAddressType());
                param1.setSumPay(orderHis.getSumPay());
                param1.setTypeDesc(StateToDesc(orderHis.getState()));
                param1.setHisComment(retStateComment(orderHis));
                DemoUserDetailDto userDetailDto = demoUserDAO.selectUserDetailById(orderHis.getUserId());

                DemoUserDto userDtoR = demoUserDAO.selectUserById(orderHis.getUserId());
                Long phoneNumberR = userDtoR.getPhoneNumber();

                String address2 = userDetailDto.getAddress2Line1() + ",  " + userDetailDto.getAddress2Line2() + ",  " + userDetailDto.getAddress2PostCode();
                String address3 = userDetailDto.getAddress3Line1() + ",  " + userDetailDto.getAddress3Line2() + ",  " + userDetailDto.getAddress3PostCode();
                String address1 = userDetailDto.getAddress1Line1() + ",  " + userDetailDto.getAddress1Line2() + ",  " + userDetailDto.getAddress1PostCode();
                if (orderHis.getAddressType().equals("1")) {
                    param1.setOrderDetail(switchDeliverType(phoneNumberR, orderHis.getDeliverType()) + ",  " +
                            switchDeliverTime(orderHis.getDeliverTime()) + ", " + address1);
                } else if (orderHis.getAddressType().equals("2")) {
                    param1.setOrderDetail(switchDeliverType(phoneNumberR, orderHis.getDeliverType()) + ",  " +
                            switchDeliverTime(orderHis.getDeliverTime()) + ", " + address2);
                } else {
                    param1.setOrderDetail(switchDeliverType(phoneNumberR, orderHis.getDeliverType()) + ",  " +
                            switchDeliverTime(orderHis.getDeliverTime()) + ", " + address3);
                }
                DemoUserDto userInfoDto = demoUserDAO.selectUserById(orderHis.getUserId());
                param1.setUserName(userInfoDto.getUserName());
                ret.add(param1);
            }
            return ret;
        } else {
            try {
                inputOrderId = Long.valueOf(input);
                List<UserOrderHisDto> orderHisDtoList = iUserOrderDAO.selectHisByOrderId(inputOrderId);
                if (!ValidateUtil.validateNotEmpty(orderHisDtoList)) {
                    return null;
                }
                List<OperateOrderParam> ret = new ArrayList<>();
                for (UserOrderHisDto orderHis : orderHisDtoList) {
                    OperateOrderParam param1 = new OperateOrderParam();
                    param1.setOrderId(orderHis.getOrderId());
                    param1.setUserId(orderHis.getUserId());
                    param1.setDeliverType(orderHis.getDeliverType());
                    param1.setDeliverTime(orderHis.getDeliverTime());
                    param1.setAddressType(orderHis.getAddressType());
                    param1.setSumPay(orderHis.getSumPay());
                    param1.setTypeDesc(StateToDesc(orderHis.getState()));
                    param1.setHisComment(retStateComment(orderHis));
                    DemoUserDetailDto userDetailDto = demoUserDAO.selectUserDetailById(orderHis.getUserId());

                    DemoUserDto userDtoR = demoUserDAO.selectUserById(orderHis.getUserId());
                    Long phoneNumberR = userDtoR.getPhoneNumber();

                    String address2 = userDetailDto.getAddress2Line1() + ",  " + userDetailDto.getAddress2Line2() + ",  " + userDetailDto.getAddress2PostCode();
                    String address3 = userDetailDto.getAddress3Line1() + ",  " + userDetailDto.getAddress3Line2() + ",  " + userDetailDto.getAddress3PostCode();
                    String address1 = userDetailDto.getAddress1Line1() + ",  " + userDetailDto.getAddress1Line2() + ",  " + userDetailDto.getAddress1PostCode();
                    if (orderHis.getAddressType().equals("1")) {
                        param1.setOrderDetail(switchDeliverType(phoneNumberR, orderHis.getDeliverType()) + ",  " +
                                switchDeliverTime(orderHis.getDeliverTime()) + ", " + address1);
                    } else if (orderHis.getAddressType().equals("2")) {
                        param1.setOrderDetail(switchDeliverType(phoneNumberR, orderHis.getDeliverType()) + ",  " +
                                switchDeliverTime(orderHis.getDeliverTime()) + ", " + address2);
                    } else {
                        param1.setOrderDetail(switchDeliverType(phoneNumberR, orderHis.getDeliverType()) + ",  " +
                                switchDeliverTime(orderHis.getDeliverTime()) + ", " + address3);
                    }
                    DemoUserDto userDto = demoUserDAO.selectUserById(orderHis.getUserId());
                    param1.setUserName(userDto.getUserName());
                    ret.add(param1);
                }
                return ret;
            } catch (Exception e) {
                logger.info(" sth is wrong, maybe input is not a suitable OrderId");
                return null;
            }
        }
    }


    @Override
    public List<OperateOrderParam> searchOrder(OperateCartParam param) {
        Long userId = param.getUserId();
        String input = param.getInput();
        String select = param.getSelect();
        String inputUserName;
        Long inputOrderId;
        if (select.equals("2")) {
            inputUserName = input;
            DemoUserDto userDto = demoUserDAO.selectUserByName(inputUserName);
            //输的名字找不到
            if (userDto == null) {
                return null;
            }
            //输的名字不是自己的名字
            if (!userDto.getUserId().equals(userId)) {
                return null;
            }
            List<UserOrderHisDto> orderHisDtoList = iUserOrderDAO.selectHisByUserId(userId);
            if (!ValidateUtil.validateNotEmpty(orderHisDtoList)) {
                return null;
            }
            List<OperateOrderParam> ret = new ArrayList<>();
            for (UserOrderHisDto orderHis : orderHisDtoList) {
                OperateOrderParam param1 = new OperateOrderParam();
                param1.setOrderId(orderHis.getOrderId());
                param1.setUserId(orderHis.getUserId());
                param1.setDeliverType(orderHis.getDeliverType());
                param1.setDeliverTime(orderHis.getDeliverTime());
                param1.setAddressType(orderHis.getAddressType());
                param1.setSumPay(orderHis.getSumPay());
                param1.setTypeDesc(StateToDesc(orderHis.getState()));
                param1.setHisComment(retStateComment(orderHis));
                DemoUserDetailDto userDetailDto = demoUserDAO.selectUserDetailById(orderHis.getUserId());

                DemoUserDto userDtoR = demoUserDAO.selectUserById(orderHis.getUserId());
                Long phoneNumberR = userDtoR.getPhoneNumber();

                String address2 = userDetailDto.getAddress2Line1() + ",  " + userDetailDto.getAddress2Line2() + ",  " + userDetailDto.getAddress2PostCode();
                String address3 = userDetailDto.getAddress3Line1() + ",  " + userDetailDto.getAddress3Line2() + ",  " + userDetailDto.getAddress3PostCode();
                String address1 = userDetailDto.getAddress1Line1() + ",  " + userDetailDto.getAddress1Line2() + ",  " + userDetailDto.getAddress1PostCode();
                if (orderHis.getAddressType().equals("1")) {
                    param1.setOrderDetail(switchDeliverType(phoneNumberR, orderHis.getDeliverType()) + ",  " +
                            switchDeliverTime(orderHis.getDeliverTime()) + ", " + address1);
                } else if (orderHis.getAddressType().equals("2")) {
                    param1.setOrderDetail(switchDeliverType(phoneNumberR, orderHis.getDeliverType()) + ",  " +
                            switchDeliverTime(orderHis.getDeliverTime()) + ", " + address2);
                } else {
                    param1.setOrderDetail(switchDeliverType(phoneNumberR, orderHis.getDeliverType()) + ",  " +
                            switchDeliverTime(orderHis.getDeliverTime()) + ", " + address3);
                }
                DemoUserDto userInfoDto = demoUserDAO.selectUserById(orderHis.getUserId());
                param1.setUserName(userInfoDto.getUserName());
                ret.add(param1);
            }
            return ret;
        } else {
            try {
                inputOrderId = Long.valueOf(input);
                List<UserOrderHisDto> orderHisDtoList = iUserOrderDAO.selectHisByIds(inputOrderId, userId);
                if (!ValidateUtil.validateNotEmpty(orderHisDtoList)) {
                    return null;
                }
                List<OperateOrderParam> ret = new ArrayList<>();
                for (UserOrderHisDto orderHis : orderHisDtoList) {
                    OperateOrderParam param1 = new OperateOrderParam();
                    param1.setOrderId(orderHis.getOrderId());
                    param1.setUserId(orderHis.getUserId());
                    param1.setDeliverType(orderHis.getDeliverType());
                    param1.setDeliverTime(orderHis.getDeliverTime());
                    param1.setAddressType(orderHis.getAddressType());
                    param1.setSumPay(orderHis.getSumPay());
                    param1.setTypeDesc(StateToDesc(orderHis.getState()));
                    param1.setHisComment(retStateComment(orderHis));
                    DemoUserDetailDto userDetailDto = demoUserDAO.selectUserDetailById(orderHis.getUserId());

                    DemoUserDto userDtoR = demoUserDAO.selectUserById(orderHis.getUserId());
                    Long phoneNumberR = userDtoR.getPhoneNumber();

                    String address2 = userDetailDto.getAddress2Line1() + ",  " + userDetailDto.getAddress2Line2() + ",  " + userDetailDto.getAddress2PostCode();
                    String address3 = userDetailDto.getAddress3Line1() + ",  " + userDetailDto.getAddress3Line2() + ",  " + userDetailDto.getAddress3PostCode();
                    String address1 = userDetailDto.getAddress1Line1() + ",  " + userDetailDto.getAddress1Line2() + ",  " + userDetailDto.getAddress1PostCode();
                    if (orderHis.getAddressType().equals("1")) {
                        param1.setOrderDetail(switchDeliverType(phoneNumberR, orderHis.getDeliverType()) + ",  " +
                                switchDeliverTime(orderHis.getDeliverTime()) + ", " + address1);
                    } else if (orderHis.getAddressType().equals("2")) {
                        param1.setOrderDetail(switchDeliverType(phoneNumberR, orderHis.getDeliverType()) + ",  " +
                                switchDeliverTime(orderHis.getDeliverTime()) + ", " + address2);
                    } else {
                        param1.setOrderDetail(switchDeliverType(phoneNumberR, orderHis.getDeliverType()) + ",  " +
                                switchDeliverTime(orderHis.getDeliverTime()) + ", " + address3);
                    }
                    DemoUserDto userDto = demoUserDAO.selectUserById(orderHis.getUserId());
                    param1.setUserName(userDto.getUserName());
                    ret.add(param1);
                }
                return ret;
            } catch (Exception e) {
                logger.info(" sth is wrong, maybe input is not a suitable OrderId");
                return null;
            }
        }
    }


    private String retStateComment(UserOrderHisDto dto) {
        if (dto.getState().equals("A")) {
            return dto.getOrderComment();
        } else if (dto.getState().equals("B")) {
            return dto.getOrderComment();
        } else if (dto.getState().equals("C")) {
            return dto.getStateComment3();
        } else if (dto.getState().equals("D")) {
            return dto.getStateComment4();
        }
        return "no comment"; // 不分状态，全部
    }

    private String StateToDesc(String state) {
        if (state.equals("A")) {
            return "卖家取消";
        } else if (state.equals("B")) {
            return "买家取消";
        } else if (state.equals("C")) {
            return "买家已付款";
        } else if (state.equals("D")) {
            return "已完成";
        }
        return "no comment"; // 不分状态，全部
    }

    private String radioToState(Long radio) {
        if (radio.equals(1L)) {
            return "C";
        } else if (radio.equals(2L)) {
            return "B";
        } else if (radio.equals(3L)) {
            return "A";
        } else if (radio.equals(4L)) {
            return "D";
        }
        return "E"; // 不分状态，全部
    }


    private Long positiveLong(Long in) {
        if (in < 0L) {
            return 0L;
        }
        return in;
    }


    @Override
    public List<OperateOrderParam> queryPreOrder(OperateCartParam param) {
        // 所有這個用户下的pre_order
        List<PreOrderDto> typesList = iUserBasketDAO.selectAllTypesById(param.getUserId());
        if (!ValidateUtil.validateNotEmpty(typesList)) {
            return null;
        }
        List<OperateOrderParam> ret = new ArrayList<>();
        for (PreOrderDto type : typesList) {
            List<PreOrderDto> dtoList = iUserBasketDAO.selectPreByIdAndTypes(
                    param.getUserId(), type.getDeliverType(), type.getDeliverTime(), type.getAddressType());
            OperateOrderParam param1 = new OperateOrderParam();
            param1.setTypeDesc("待商家确认");
            param1.setUserId(param.getUserId());
            DemoUserDto userDto = demoUserDAO.selectUserById(param.getUserId());
            param1.setUserName(userDto.getUserName());
            param1.setDeliverType(type.getDeliverType());
            param1.setDeliverTime(type.getDeliverTime());
            param1.setAddressType(type.getAddressType());
            DemoUserDetailDto userDetailDto = demoUserDAO.selectUserDetailById(param.getUserId());

            DemoUserDto userDtoR = demoUserDAO.selectUserById(param.getUserId());
            Long phoneNumberR = userDtoR.getPhoneNumber();

            String address1 = userDetailDto.getAddress1Line1() + userDetailDto.getAddress1Line2() + userDetailDto.getAddress1PostCode();
            String address2 = userDetailDto.getAddress2Line1() + userDetailDto.getAddress2Line2() + userDetailDto.getAddress2PostCode();
            String address3 = userDetailDto.getAddress3Line1() + userDetailDto.getAddress3Line2() + userDetailDto.getAddress3PostCode();
            if (type.getAddressType().equals("1")) {
                param1.setOrderDetail(switchDeliverType(phoneNumberR, type.getDeliverType()) + ", " +
                        switchDeliverTime(type.getDeliverTime()) + ", " + address1);
            } else if (type.getAddressType().equals("2")) {
                param1.setOrderDetail(switchDeliverType(phoneNumberR, type.getDeliverType()) + ", " +
                        switchDeliverTime(type.getDeliverTime()) + ", " + address2);
            } else {
                param1.setOrderDetail(switchDeliverType(phoneNumberR, type.getDeliverType()) + ", " +
                        switchDeliverTime(type.getDeliverTime()) + ", " + address3);
            }
            param1.setItemsNum((long) dtoList.size());
            Long sumPrice = 0L;
            for (PreOrderDto dto : dtoList) {
                DemoResourceDto re = demoResourceDAO.queryResourceById(dto.getResourceId());
                sumPrice += (re.getPrice() * dto.getItemNum());
            }
            param1.setItemsPrice(sumPrice);
            ret.add(param1);
        }
        return ret;
    }

    private String switchDeliverType(Long phoneNum, String in) {
        if (in.equals("1")) {
            return "collect 自取, " + phoneNum;
        }
        return "deliver 派送, " + phoneNum;
    }

    private String switchDeliverTime(String in) {
        if (in.equals("4")) {
            return "ASAP";
        }
        return in;
    }

    //往没有图片的数据里插入默认图片，可以作为默认动作
    @Override
    public void insertResourcePhoto() {
        List<Long> idList = demoResourceDAO.selectAllResourceWithoutDetail();
        for (Long id : idList) {
            String filePath1 = "C:\\Users\\Kent\\Desktop\\PaChongPhoto\\" + String.valueOf(id) + "1" + ".jpg";
            String filePath2 = "C:\\Users\\Kent\\Desktop\\PaChongPhoto\\" + String.valueOf(id) + "2" + ".jpg";
            String filePath3 = "C:\\Users\\Kent\\Desktop\\PaChongPhoto\\" + String.valueOf(id) + "3" + ".jpg";
            String filePath4 = "C:\\Users\\Kent\\Desktop\\PaChongPhoto\\" + String.valueOf(id) + "4" + ".jpg";
            String filePath5 = "C:\\Users\\Kent\\Desktop\\PaChongPhoto\\" + String.valueOf(id) + "5" + ".jpg";
            String filePath6 = "C:\\Users\\Kent\\Desktop\\PaChongPhoto\\" + String.valueOf(id) + "5" + ".jpg";
            DemoResourceDetailDto detailDto = new DemoResourceDetailDto();
            detailDto.setResourceId(id);
            detailDto.setHot(1L);
            detailDto.setStock(0L);
            detailDto.setPhotoOne(getByteOfPic(filePath1));
            detailDto.setPhotoTwo(getByteOfPic(filePath2));
            detailDto.setPhotoThree(getByteOfPic(filePath3));
            detailDto.setPhotoFour(getByteOfPic(filePath4));
            detailDto.setPhotoFive(getByteOfPic(filePath5));
            detailDto.setPhotoDetail(getByteOfPic(filePath6));
            demoResourceDAO.insertDemoResourceDetail(detailDto);
        }
    }

    private byte[] getByteOfPic(String url) {
        byte[] pic = null;
        try {
            pic = FileHelper.readFileToByte(url);
        } catch (IOException e) {
            return getPicture(defaultValue);
        }
        return pic;
    }


}
