package com.tang.billing.demoprovider.infrastrucrute.DAO.billing;

import com.tang.billing.demoprovider.infrastrucrute.model.PreOrderDto;
import com.tang.billing.demoprovider.infrastrucrute.model.UserBasketDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IUserBasketDAO {

    @Insert("INSERT INTO USER_BASKET (\n" + //
            "   USER_ID,RESOURCE_ID,ITEM_NUM\n" + //
            ")\n" + //
            "VALUES\n" + //
            "   (\n" + //
            "       #{dto.userId},\n" + //
            "       #{dto.resourceId},\n" + //
            "       #{dto.itemNum})")
    void insertBasket(@Param("dto") UserBasketDto dto);

    @Select("select * from USER_BASKET")
    @ResultType(UserBasketDto.class)
    UserBasketDto selectAllBasket();

    @Select("select * from USER_BASKET where user_id = #{userId} and resource_id = #{resourceId} ")
    @ResultType(UserBasketDto.class)
    UserBasketDto selectByIds(@Param("userId") Long userId, @Param("resourceId") Long resourceId);

    @Select("select * from USER_BASKET where user_id = #{userId} ")
    @ResultType(UserBasketDto.class)
    List<UserBasketDto> selectByUserId(@Param("userId") Long userId);

    @Update("UPDATE USER_BASKET SET item_num = #{num} WHERE USER_ID =  #{userId} and resource_id = #{resourceId}")
    void updateUserDetail(@Param("userId") Long userId, @Param("resourceId") Long resourceId,
                          @Param("num") Long num);

    @Delete("delete USER_BASKET where USER_ID = #{userId} and RESOURCE_ID = #{resourceId}")
    void deleteByIds(@Param("userId") Long userId, @Param("resourceId") Long resourceId);

    @Delete("delete USER_BASKET where USER_ID = #{userId}")
    void deleteByUserId(@Param("userId") Long userId);



//    ==================


    @Insert("INSERT INTO PRE_ORDER (\n" + //
            "   USER_ID,RESOURCE_ID,ITEM_NUM,DELIVER_TYPE,DELIVER_TIME,ADDRESS_TYPE\n" + //
            ")\n" + //
            "VALUES\n" + //
            "   (\n" + //
            "       #{dto.userId},\n" + //
            "       #{dto.resourceId},\n" + //
            "       #{dto.itemNum},\n" + //
            "       #{dto.deliverType},\n" + //
            "       #{dto.deliverTime},\n" + //
            "       #{dto.addressType})")
    void insertPreOrder(@Param("dto") PreOrderDto dto);

    @Select("select * from PRE_ORDER where user_id = #{userId} and  RESOURCE_ID = #{resourceId} and DELIVER_TYPE = #{deliverType}" +
            "and DELIVER_TIME = #{deliverTime}" +
            "and ADDRESS_TYPE = #{addressType}")
    @ResultType(PreOrderDto.class)
    PreOrderDto selectPreByIdsAndTypes(@Param("userId") Long userId
            , @Param("resourceId") Long resourceId
            , @Param("deliverType") String deliverType
            , @Param("deliverTime") String deliverTime
            , @Param("addressType") String addressType);

    @Select("select * from PRE_ORDER where user_id = #{userId} and DELIVER_TYPE = #{deliverType}" +
            "and DELIVER_TIME = #{deliverTime}" +
            "and ADDRESS_TYPE = #{addressType}")
    @ResultType(PreOrderDto.class)
    List<PreOrderDto> selectPreByIdAndTypes(@Param("userId") Long userId, @Param("deliverType") String deliverType
            , @Param("deliverTime") String deliverTime
            , @Param("addressType") String addressType);

    @Delete("delete PRE_ORDER where USER_ID = #{userId}")
    void deletePreByUserId(@Param("userId") Long userId);

    @Delete("delete PRE_ORDER where user_id = #{userId} " +
            "and RESOURCE_ID = #{resourceId} " +
            "and DELIVER_TYPE = #{deliverType}" +
            "and DELIVER_TIME = #{deliverTime}" +
            "and ADDRESS_TYPE = #{addressType}")
    void deletePreByIdsTypes(@Param("userId") Long userId
            , @Param("resourceId") Long resourceId
            , @Param("deliverType") String deliverType
            , @Param("deliverTime") String deliverTime
            , @Param("addressType") String addressType);

    @Delete("delete PRE_ORDER where user_id = #{userId} " +
            "and DELIVER_TYPE = #{deliverType}" +
            "and DELIVER_TIME = #{deliverTime}" +
            "and ADDRESS_TYPE = #{addressType}")
    void deletePreByIdTypes(@Param("userId") Long userId
            , @Param("deliverType") String deliverType
            , @Param("deliverTime") String deliverTime
            , @Param("addressType") String addressType);

    @Update("UPDATE PRE_ORDER SET item_num = #{num} where user_id = #{userId} " +
            "and RESOURCE_ID = #{resourceId} " +
            "and DELIVER_TYPE = #{deliverType}" +
            "and DELIVER_TIME = #{deliverTime}" +
            "and ADDRESS_TYPE = #{addressType}")
    void updatePreOrder(@Param("userId") Long userId
            , @Param("resourceId") Long resourceId
            , @Param("deliverType") String deliverType
            , @Param("deliverTime") String deliverTime
            , @Param("addressType") String addressType
            , @Param("num") Long num);

    @Select("select * from PRE_ORDER where user_id = #{userId} ")
    @ResultType(PreOrderDto.class)
    List<PreOrderDto> selectPreByUserId(@Param("userId") Long userId);

    @Select("select distinct DELIVER_TYPE, DELIVER_TIME, ADDRESS_TYPE from PRE_ORDER where user_id = #{userId} ")
    @ResultType(PreOrderDto.class)
    List<PreOrderDto> selectAllTypesById(@Param("userId") Long userId);

    @Select("select distinct user_id from PRE_ORDER")
    @ResultType(PreOrderDto.class)
    List<PreOrderDto> selectAllPreUserIds();


}
