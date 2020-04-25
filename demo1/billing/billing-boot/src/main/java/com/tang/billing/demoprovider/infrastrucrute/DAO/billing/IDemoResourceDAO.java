package com.tang.billing.demoprovider.infrastrucrute.DAO.billing;

import com.tang.billing.demoprovider.infrastrucrute.model.DemoResourceDetailDto;
import com.tang.billing.demoprovider.infrastrucrute.model.DemoResourceDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IDemoResourceDAO {

    @Insert("INSERT INTO DEMO_RESOURCE (\n" + //
            "   RESOURCE_ID,\n" + //
            "   RESOURCE_NAME,USER_ID,USER_NAME,RESOURCE_DESC,CREATE_DATE,DELIVER_TYPE,DELIVER_PRICE,PRICE,CLASSIFICATION\n" + //
            ")\n" + //
            "VALUES\n" + //
            "   (\n" + //
            "       #{dto.resourceId},\n" + //
            "       #{dto.resourceName},\n" + //
            "       #{dto.userId},\n" + //
            "       #{dto.userName},\n" + //
            "       #{dto.resourceDesc},\n" + //
            "       #{dto.createDate},\n" + //
            "       #{dto.deliverType},\n" + //
            "       #{dto.deliverPrice},\n" + //
            "       #{dto.price},\n" + //
            "       #{dto.classification})")
    void insertDemoResource(@Param("dto") DemoResourceDto dto);

    @Select("select * from DEMO_RESOURCE")
    @ResultType(DemoResourceDto.class)
    List<DemoResourceDto> selectAllResource();

    @Select("SELECT distinct user_name FROM DEMO_RESOURCE")
    @ResultType(String.class)
    List<String> selectAllUserNames();

    @Select("select resource_id from DEMO_RESOURCE where resource_id not in (select resource_id from DEMO_RESOURCE_DETAIL)")
    @ResultType(Long.class)
    List<Long> selectAllResourceWithoutDetail();

    @Select("select * from DEMO_RESOURCE where resource_id = #{param1}")
    @ResultType(DemoResourceDetailDto.class)
    DemoResourceDto queryResourceById(Long resourceId);

    @Select("select * from DEMO_RESOURCE where classification = #{param1}")
    @ResultType(DemoResourceDto.class)
    List<DemoResourceDto> selectByItem(Long itemId);

    @Select("select * from DEMO_RESOURCE where user_id = #{param1}")
    @ResultType(DemoResourceDto.class)
    List<DemoResourceDto> selectByUser(Long userId);

    @Select("select * from DEMO_RESOURCE where user_name = #{param1}")
    @ResultType(DemoResourceDto.class)
    List<DemoResourceDto> selectByUserName(String userName);

    @Select("select * from DEMO_RESOURCE where RESOURCE_NAME = #{param1}")
    @ResultType(DemoResourceDto.class)
    List<DemoResourceDto> selectByResourceName(String name);

    @Select("select * from DEMO_RESOURCE where RESOURCE_NAME = #{resourceName}")
    @ResultType(DemoResourceDto.class)
    DemoResourceDto selectByReName(@Param("resourceName") String resourceName);

    /**
     * @param userName 正则表达式匹配不区分大小写，空格是参数x,
     *                 但答案有空格也会省去所以空格不支持，除非拆成分词再匹配。
     * @return data
     */
    @Select("SELECT * FROM DEMO_RESOURCE WHERE REGEXP_LIKE(USER_NAME, #{param1}, 'i')")
    @ResultType(DemoResourceDto.class)
    List<DemoResourceDto> selectBySomeUserName(String userName);

    /**
     * @param input 正则表达式匹配不区分大小写,且union  两个字段的结果，不保留重复结果
     * @return data
     */
    @Select("SELECT * FROM DEMO_RESOURCE WHERE REGEXP_LIKE(RESOURCE_DESC, #{param1}, 'i')\n" +
            "UNION \n" +
            "SELECT * FROM DEMO_RESOURCE WHERE REGEXP_LIKE(RESOURCE_NAME, #{param1}, 'i')")
    @ResultType(DemoResourceDto.class)
    List<DemoResourceDto> selectBySomeResourceNameOrDesc(String input);


    @Update("UPDATE DEMO_RESOURCE SET price = #{price}, RESOURCE_DESC = #{resourceDesc} WHERE resource_id = #{resourceId}")
    void updatePriceAndDetail(@Param("resourceId") Long resourceId, @Param("price") Long price,
                              @Param("resourceDesc") String resourceDesc);


    /////////////////////////////////////////////////////////////////

    @Insert("INSERT INTO DEMO_RESOURCE_DETAIL (\n" + //
            "   RESOURCE_ID,\n" + //
            "   STOCK,HOT,photo_one,photo_two,photo_three,photo_four,photo_five, PHOTO_DETAIL\n" + //
            ")\n" + //
            "VALUES\n" + //
            "   (\n" + //
            "       #{dto.resourceId},\n" + //
            "       #{dto.stock},\n" + //
            "       #{dto.hot},\n" + //
            "       #{dto.photoOne},\n" + //
            "       #{dto.photoTwo},\n" + //
            "       #{dto.photoThree},\n" + //
            "       #{dto.photoFour},\n" + //
            "       #{dto.photoFive},\n" + //
            "       #{dto.photoDetail})")
    void insertDemoResourceDetail(@Param("dto") DemoResourceDetailDto dto);

    @Select("select * from DEMO_RESOURCE_DETAIL where resource_id = #{param1}")
    @ResultType(DemoResourceDetailDto.class)
    DemoResourceDetailDto queryResourceDetailById(Long resourceId);


    @Update("UPDATE DEMO_RESOURCE_DETAIL SET STOCK = #{stock} WHERE resource_id = #{resourceId}")
    void updateStock(@Param("resourceId") Long resourceId, @Param("stock") Long stock);

    @Update("UPDATE DEMO_RESOURCE_DETAIL SET photo_one = #{photo} WHERE resource_id = #{resourceId}")
    void updatePhoto1(@Param("resourceId") Long resourceId, @Param("photo") byte[] photo);

    @Update("UPDATE DEMO_RESOURCE_DETAIL SET photo_two = #{photo} WHERE resource_id = #{resourceId}")
    void updatePhoto2(@Param("resourceId") Long resourceId, @Param("photo") byte[] photo);

    @Update("UPDATE DEMO_RESOURCE_DETAIL SET photo_three = #{photo} WHERE resource_id = #{resourceId}")
    void updatePhoto3(@Param("resourceId") Long resourceId, @Param("photo") byte[] photo);

    @Update("UPDATE DEMO_RESOURCE_DETAIL SET photo_four = #{photo} WHERE resource_id = #{resourceId}")
    void updatePhoto4(@Param("resourceId") Long resourceId, @Param("photo") byte[] photo);

    @Update("UPDATE DEMO_RESOURCE_DETAIL SET photo_five = #{photo} WHERE resource_id = #{resourceId}")
    void updatePhoto5(@Param("resourceId") Long resourceId, @Param("photo") byte[] photo);



}
