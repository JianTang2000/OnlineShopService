package com.tang.api.billing;

import java.util.List;

import com.tang.param.billing.GetDataResultParam;
import com.tang.param.billing.DemoPersonParam;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/10/26 <br>
 */
public interface DemoPersonService {



    /**
     * < 目前没有用户一说，所以暂时没用到该字段 > <br>
     *
     * @auther: tang
     * @param userId < >
     * @param opusId < >
     */
    void likeThisSong(Long userId, Long opusId);

    /**
     * < 模糊查询接口，根据这个输入去模糊查询，查询的范围包括 名称，曲名等; 这个必然是查本地缓存的，而且需要很高的效率。 > <br>
     *
     * @auther: tang
     * @param input < 入参是一个前台输入的字符串，由用户输入 >
     * @return < >
     */
    List<GetDataResultParam> queryByAbstractInput(String input);

    /**
     * < 新增 > <br>
     *
     * @auther: tang
     * @param param < >
     */
    void createPerson(DemoPersonParam param);

    /**
     * < queryPersonById > <br>
     *
     * @auther: tang
     * @param id < >
     * @return < >
     */
    DemoPersonParam queryPersonById(Long id);

    /**
     * < queryAllPerson > <br>
     *
     * @auther: tang
     * @return < >
     */
    List<DemoPersonParam> queryAllPerson();

}
