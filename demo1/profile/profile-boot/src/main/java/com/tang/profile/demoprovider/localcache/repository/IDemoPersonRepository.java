package com.tang.profile.demoprovider.localcache.repository;

import java.util.List;

import com.tang.param.billing.DemoPersonParam;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/10/23 <br>
 */
public interface IDemoPersonRepository {

    /**
     * < getByPersonId > <br>
     *
     * @auther: tang
     * @param personId < personId >
     * @return < >
     */
    DemoPersonParam getByPersonId(Long personId);

    /**
     * < getAllPerson > <br>
     *
     * @auther: tang
     * @return < List >
     */
    List<DemoPersonParam> getAllPerson();

}
