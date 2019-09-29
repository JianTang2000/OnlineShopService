package com.tang.profile.demoprovider.infrastrucrute.DAO.profile;

import java.util.List;

import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.tang.profile.demoprovider.infrastrucrute.model.DemoResourceDetailDto;
import com.tang.profile.demoprovider.infrastrucrute.model.DemoResourceDto;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/20 <br>
 */
public interface IDemoResourceDAO {

    /**
     * < 根据用户名（只能完全匹配或部分缺省，不能多，不支持拆分匹配）检索DEMO_RESOURCE，仅取前100记录 > <br>
     *
     * @auther: tang
     * @param inputString < 形如:"%XXX%" >
     * @return < DemoPersonDto >
     */
    @Select("SELECT * FROM (SELECT a.*, ROWID FROM DEMO_RESOURCE a WHERE USER_NAME LIKE #{param1}) WHERE ROWNUM <=100")
    @ResultType(DemoResourceDto.class)
    List<DemoResourceDto> selectResourceByUserName(String inputString);

    /**
     * < 根据资源名/描述（只能完全匹配或部分缺省，不能多，不支持拆分匹配）检索DEMO_RESOURCE，仅取前100记录 > <br>
     *
     * @auther: tang
     * @param inputString < 形如:"%XXX%" >
     * @return < DemoPersonDto >
     */
    @Select("SELECT * FROM (SELECT a.*, ROWID FROM DEMO_RESOURCE a WHERE RESOURCE_NAME like #{param1} or RESOURCE_DESC LIKE #{param1}) WHERE ROWNUM <=100")
    @ResultType(DemoResourceDto.class)
    List<DemoResourceDto> selectResourceByResourceNameOrDesc(String inputString);

    /**
     * < 检索DEMO_RESOURCE热门记录，倒序排列，仅取前100记录 > <br>
     *
     * @auther: tang
     * @return < DemoPersonDto >
     */
//    left join(左联接) 返回包括左表中的所有记录和右表中联结字段相等的记录
//　　right join(右联接) 返回包括右表中的所有记录和左表中联结字段相等的记录
//　　inner join(等值连接) 只返回两个表中联结字段相等的行
    @Select(
        "SELECT *\n" +
            "  FROM (SELECT m.RESOURCE_ID,\n" +
            "               m.RESOURCE_SIZE,\n" +
            "               m.hot,\n" +
            "               a.resource_name,\n" +
            "               a.user_name\n" +
            "          FROM DEMO_RESOURCE_DETAIL m\n" +
            "          inner join DEMO_RESOURCE a ON m.RESOURCE_ID = a.RESOURCE_ID\n" +
            "         ORDER BY HOT DESC)\n" +
            " WHERE ROWNUM <= 100"
    )
    @ResultType(DemoResourceDetailDto.class)
    List<DemoResourceDetailDto> selectResourceByHotDesc();

    /**
     * < > <br>
     *
     * @auther: tang
     * @return < DemoPersonDto >
     */
    @Select("select * from DEMO_RESOURCE_DETAIL where resource_id = #{param1}")
    @ResultType(DemoResourceDetailDto.class)
    DemoResourceDetailDto queryResourceDetailById(Long resourceId);

    /**
     * < > <br>
     *
     * @auther: tang
     * @return < DemoPersonDto >
     */
    @Select("select * from DEMO_RESOURCE where resource_id = #{param1}")
    @ResultType(DemoResourceDetailDto.class)
    DemoResourceDto queryResourceById(Long resourceId);
}
