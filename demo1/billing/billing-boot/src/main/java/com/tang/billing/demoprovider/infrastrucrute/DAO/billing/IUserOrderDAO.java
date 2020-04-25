package com.tang.billing.demoprovider.infrastrucrute.DAO.billing;

import com.tang.billing.demoprovider.infrastrucrute.model.DemoResourceDto;
import com.tang.billing.demoprovider.infrastrucrute.model.PreOrderDto;
import com.tang.billing.demoprovider.infrastrucrute.model.UserOrderDto;
import com.tang.billing.demoprovider.infrastrucrute.model.UserOrderHisDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IUserOrderDAO {
    @Insert("INSERT INTO USER_ORDER (\n" + //
            "   USER_ID,RESOURCE_ID,ITEM_NUM,DELIVER_TYPE,DELIVER_TIME,ADDRESS_TYPE,EXTRA_COST,DISCOUNT,ORDER_COMMENT\n" + //
            ")\n" + //
            "VALUES\n" + //
            "   (\n" + //
            "       #{dto.userId},\n" + //
            "       #{dto.resourceId},\n" + //
            "       #{dto.itemNum},\n" + //
            "       #{dto.deliverType},\n" + //
            "       #{dto.deliverTime},\n" + //
            "       #{dto.addressType},\n" + //
            "       #{dto.extraCost},\n" + //
            "       #{dto.discount},\n" + //
            "       #{dto.orderComment})")
    void insertUserOrder(@Param("dto") UserOrderDto dto);

    @Select("select * from USER_ORDER")
    @ResultType(UserOrderDto.class)
    UserOrderDto selectAllUserOrder();

    @Select("select * from USER_ORDER  where user_id = #{userId}")
    @ResultType(UserOrderDto.class)
    UserOrderDto selectOrderById(@Param("userId") Long userId);

    @Select("select distinct DELIVER_TYPE, DELIVER_TIME, ADDRESS_TYPE from USER_ORDER where user_id = #{userId} ")
    @ResultType(UserOrderDto.class)
    List<UserOrderDto> selectAllTypesById(@Param("userId") Long userId);

    @Select("select * from USER_ORDER where user_id = #{userId} and DELIVER_TYPE = #{deliverType}" +
            "and DELIVER_TIME = #{deliverTime}" +
            "and ADDRESS_TYPE = #{addressType}")
    @ResultType(UserOrderDto.class)
    List<UserOrderDto> selectOrdByIdAndTypes(@Param("userId") Long userId
            , @Param("deliverType") String deliverType
            , @Param("deliverTime") String deliverTime
            , @Param("addressType") String addressType);

    @Delete("delete USER_ORDER where user_id = #{userId} " +
            "and DELIVER_TYPE = #{deliverType}" +
            "and DELIVER_TIME = #{deliverTime}" +
            "and ADDRESS_TYPE = #{addressType}")
    void deleteByIdTypes(@Param("userId") Long userId
            , @Param("deliverType") String deliverType
            , @Param("deliverTime") String deliverTime
            , @Param("addressType") String addressType);



    /////////////////////////////////////////////////////////////////

    @Insert("INSERT INTO USER_ORDER_HIS (\n" + //
            "   ORDER_ID, USER_ID,RESOURCE_ID,ITEM_NUM,DELIVER_TYPE,DELIVER_TIME,ADDRESS_TYPE,EXTRA_COST,DISCOUNT,ORDER_COMMENT," +
            "STATE,STATE_COMMENT_1,STATE_COMMENT_2,STATE_COMMENT_3,STATE_COMMENT_4,CREATE_DATE,SUM_PAY\n" + //
            ")\n" + //
            "VALUES\n" + //
            "   (\n" + //
            "       #{dto.orderId},\n" + //
            "       #{dto.userId},\n" + //
            "       #{dto.resourceId},\n" + //
            "       #{dto.itemNum},\n" + //
            "       #{dto.deliverType},\n" + //
            "       #{dto.deliverTime},\n" + //
            "       #{dto.addressType},\n" + //
            "       #{dto.extraCost},\n" + //
            "       #{dto.discount},\n" + //
            "       #{dto.orderComment},\n" + //
            "       #{dto.state},\n" + //
            "       #{dto.stateComment1},\n" + //
            "       #{dto.stateComment2},\n" + //
            "       #{dto.stateComment3},\n" + //
            "       #{dto.stateComment4},\n" + //
            "       #{dto.createDate},\n" + //
            "       #{dto.sumPay})")
    void insertUserOrderHis(@Param("dto") UserOrderHisDto dto);

    @Select("select distinct ORDER_ID, USER_ID, DELIVER_TYPE, DELIVER_TIME,ADDRESS_TYPE, EXTRA_COST,\n" +
            "                DISCOUNT, ORDER_COMMENT, CREATE_DATE,STATE,STATE_COMMENT_1, STATE_COMMENT_2,\n" +
            "                STATE_COMMENT_3, STATE_COMMENT_4, SUM_PAY\n" +
            "from USER_ORDER_HIS WHERE STATE = #{state} order by ORDER_ID DESC")
    @ResultType(UserOrderHisDto.class)
    List<UserOrderHisDto> selectByOrderByState(@Param("state") String state);

    @Select("select distinct ORDER_ID, USER_ID, DELIVER_TYPE, DELIVER_TIME,ADDRESS_TYPE, EXTRA_COST,\n" +
            "                DISCOUNT, ORDER_COMMENT, CREATE_DATE,STATE,STATE_COMMENT_1, STATE_COMMENT_2,\n" +
            "                STATE_COMMENT_3, STATE_COMMENT_4, SUM_PAY\n" +
            "from USER_ORDER_HIS order by ORDER_ID DESC")
    @ResultType(UserOrderHisDto.class)
    List<UserOrderHisDto> selectByOrderAllState();

    @Select("select distinct ORDER_ID, USER_ID, DELIVER_TYPE, DELIVER_TIME,ADDRESS_TYPE, EXTRA_COST,\n" +
            "                DISCOUNT, ORDER_COMMENT, CREATE_DATE,STATE,STATE_COMMENT_1, STATE_COMMENT_2,\n" +
            "                STATE_COMMENT_3, STATE_COMMENT_4, SUM_PAY\n" +
            "from USER_ORDER_HIS WHERE STATE = #{state} and USER_ID = #{userId} order by ORDER_ID DESC")
    @ResultType(UserOrderHisDto.class)
    List<UserOrderHisDto> selectByOrderByIdState(@Param("state") String state, @Param("userId") Long userId);

    @Select("select distinct ORDER_ID, USER_ID, DELIVER_TYPE, DELIVER_TIME,ADDRESS_TYPE, EXTRA_COST,\n" +
            "                DISCOUNT, ORDER_COMMENT, CREATE_DATE,STATE,STATE_COMMENT_1, STATE_COMMENT_2,\n" +
            "                STATE_COMMENT_3, STATE_COMMENT_4, SUM_PAY\n" +
            "from USER_ORDER_HIS WHERE USER_ID = #{userId} order by ORDER_ID DESC")
    @ResultType(UserOrderHisDto.class)
    List<UserOrderHisDto> selectByOrderIdAllState(@Param("userId") Long userId);



    @Select("select distinct ORDER_ID, USER_ID, DELIVER_TYPE, DELIVER_TIME,ADDRESS_TYPE, EXTRA_COST,\n" +
            "                DISCOUNT, ORDER_COMMENT, CREATE_DATE,STATE,STATE_COMMENT_1, STATE_COMMENT_2,\n" +
            "                STATE_COMMENT_3, STATE_COMMENT_4, SUM_PAY\n" +
            "from USER_ORDER_HIS WHERE ORDER_ID = #{orderId} order by ORDER_ID DESC")
    @ResultType(UserOrderHisDto.class)
    List<UserOrderHisDto> selectHisByOrderId(@Param("orderId") Long orderId);

    @Select("select distinct ORDER_ID, USER_ID, DELIVER_TYPE, DELIVER_TIME,ADDRESS_TYPE, EXTRA_COST,\n" +
            "                DISCOUNT, ORDER_COMMENT, CREATE_DATE,STATE,STATE_COMMENT_1, STATE_COMMENT_2,\n" +
            "                STATE_COMMENT_3, STATE_COMMENT_4, SUM_PAY\n" +
            "from USER_ORDER_HIS WHERE ORDER_ID = #{orderId} and USER_ID = #{userId} order by ORDER_ID DESC")
    @ResultType(UserOrderHisDto.class)
    List<UserOrderHisDto> selectHisByIds(@Param("orderId") Long orderId, @Param("userId") Long userId);

    @Select("select distinct ORDER_ID, USER_ID, DELIVER_TYPE, DELIVER_TIME,ADDRESS_TYPE, EXTRA_COST,\n" +
            "                DISCOUNT, ORDER_COMMENT, CREATE_DATE,STATE,STATE_COMMENT_1, STATE_COMMENT_2,\n" +
            "                STATE_COMMENT_3, STATE_COMMENT_4, SUM_PAY\n" +
            "from USER_ORDER_HIS WHERE USER_ID = #{userId} order by ORDER_ID DESC")
    @ResultType(UserOrderHisDto.class)
    List<UserOrderHisDto> selectHisByUserId(@Param("userId") Long userId);

    @Select("select * from USER_ORDER_HIS where ORDER_ID = #{orderId}")
    @ResultType(UserOrderHisDto.class)
    List<UserOrderHisDto> selectByOrderId(@Param("orderId") Long orderId);


    @Update("UPDATE USER_ORDER_HIS SET STATE = 'D' WHERE ORDER_ID =  #{orderId}")
    void updateStateToD(@Param("orderId") Long orderId);
}
