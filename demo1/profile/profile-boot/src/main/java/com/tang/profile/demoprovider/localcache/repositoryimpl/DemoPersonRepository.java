package com.tang.profile.demoprovider.localcache.repositoryimpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tang.base.util.BaseCommonUtil;
import com.tang.base.util.ValidateUtil;
import com.tang.base.util.localcache.AbstractFullLoad;
import com.tang.base.util.localcache.LocalCacheMapImpl;
import com.tang.constant.LocalCacheRepositoryBeanNameDef;
import com.tang.profile.demoprovider.constant.localCacheConstant.LocalCacheKeyLevel1Def;
import com.tang.param.billing.DemoPersonParam;
import com.tang.profile.demoprovider.constant.localCacheConstant.LocalCacheKeyLevel2Def;
import com.tang.profile.demoprovider.infrastrucrute.DAO.profile.IDemoPersonDAO;
import com.tang.profile.demoprovider.infrastrucrute.model.DemoPersonDto;
import com.tang.profile.demoprovider.localcache.repository.IDemoPersonRepository;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/10/23 <br>
 */
@Repository(LocalCacheRepositoryBeanNameDef.demoPersonRepository)
public class DemoPersonRepository extends AbstractFullLoad<DemoPersonParam> implements IDemoPersonRepository {

    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(DemoPersonRepository.class);

    @Autowired
    IDemoPersonDAO demoPersonDAO;

    @Override
    public DemoPersonParam getByPersonId(Long personId) {
        logger.info("-----ready to getByPersonId({}) from cacheMap----", personId);
        DemoPersonParam demoPersonParam = cacheMap.getOne(LocalCacheKeyLevel1Def.all_person_with_id, personId);
        if (null == demoPersonParam && !isFullLoaded) {
            logger.debug("-----getByPersonId from cacheMap ret is null, try to run reload-----");
            reload();
            demoPersonParam = cacheMap.getOne(LocalCacheKeyLevel1Def.all_person_with_id, personId);
        }
        return demoPersonParam;
    }

    @Override
    public List<DemoPersonParam> getAllPerson() {
        logger.info("-----ready to getAllPerson() from cacheMap----");
        List<DemoPersonParam> demoPersonParamList = cacheMap.get(LocalCacheKeyLevel1Def.all_person, LocalCacheKeyLevel2Def.all_in_one);
        if (!ValidateUtil.validateNotEmpty(demoPersonParamList) && !isFullLoaded) {
            logger.debug("-----getAllPerson() from cacheMap ret is null, try to run reload-----");
            reload();
            demoPersonParamList = cacheMap.get(LocalCacheKeyLevel1Def.all_person, LocalCacheKeyLevel2Def.all_in_one);
        }

        return demoPersonParamList;
    }

    /**
     * < 数据加载抽象方法，业务侧提供方法的实现（比如根据某个/些/无条件去查寻，然后把结果放到缓存） > <br>
     *
     * @auther: tang
     */
    @Override
    public void loadAll() {
        logger.info("*******Run DemoPersonRepository.loadAll()*****");
        logger.info("*******Run IDemoPersonDAO.getByPersonId()/getAllPerson() and put to cacheMap*****");
        List<DemoPersonParam> demoPersonParamList = new ArrayList<>();
        List<DemoPersonDto> demoPersonDtoList;
        demoPersonDtoList = demoPersonDAO.selectAll();
        if (ValidateUtil.validateNotEmpty(demoPersonDtoList)) {
            for (DemoPersonDto demoPersonDto : demoPersonDtoList) {
                DemoPersonParam demoPersonParam = new DemoPersonParam();
                BeanUtils.copyProperties(demoPersonDto, demoPersonParam, DemoPersonParam.class);
                demoPersonParamList.add(demoPersonParam);

            }
        }
        logger.info("demoPersonDtoList query from DB will put into localCache,which is {}",
            BaseCommonUtil.objectToJsonString(demoPersonParamList));
        cacheMap.put(LocalCacheKeyLevel1Def.all_person, LocalCacheKeyLevel2Def.all_in_one, demoPersonParamList);
        for (DemoPersonParam demoPersonParam : demoPersonParamList) {
            logger.info("demoPersonParam will put into localCache,which PersonId is {}", demoPersonParam.getPersonId());
            cacheMap.put(LocalCacheKeyLevel1Def.all_person_with_id, demoPersonParam.getPersonId(), demoPersonParam);
        }

    }

    /**
     * < 本地缓存数据的初始化,在业务调用侧实现该方法，同时构造函数里调用该方法以初始化实际的缓存数据结构（也就是LocalCacheMap ）> <br>
     *
     * @auther: tang
     */
    @Override
    public void init() {
        cacheMap = new LocalCacheMapImpl<>();
        cacheMap.addKey(LocalCacheKeyLevel1Def.all_person_with_id);
        cacheMap.addKey(LocalCacheKeyLevel1Def.all_person);
    }
}
