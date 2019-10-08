package com.tang.billing.demoprovider.infrastrucrute.DAO.billing;

import com.tang.billing.demoprovider.infrastrucrute.model.DemoResourceDto;
import com.tang.billing.demoprovider.infrastrucrute.model.UserOrderDto;
import com.tang.billing.demoprovider.infrastrucrute.model.UserOrderHisDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

public interface IUserOrderDAO {

    @Insert("INSERT INTO USER_ORDER (\n" + //
            "   ORDER_ID,\n" + //
            "   USER_ID,RESOURCE_ID,CREATE_DATE,DELIVER_INFO,ADDRESS_INFO\n" + //
            ")\n" + //
            "VALUES\n" + //
            "   (\n" + //
            "       #{dto.orderId},\n" + //
            "       #{dto.userId},\n" + //
            "       #{dto.resourceId},\n" + //
            "       #{dto.createDate},\n" + //
            "       #{dto.deliverInfo},\n" + //
            "       #{dto.addressInfo})")
    void insertUserOrder(@Param("dto") UserOrderDto dto);

    @Select("select * from USER_ORDER")
    @ResultType(UserOrderDto.class)
    DemoResourceDto selectAllUserOrder();

    /////////////////////////////////////////////////////////////////

    @Insert("INSERT INTO USER_ORDER_HIS (\n" + //
            "   ORDER_ID,\n" + //
            "   USER_ID,RESOURCE_ID,CREATE_DATE,FINISH_DATE,DELIVER_INFO,ADDRESS_INFO\n" + //
            ")\n" + //
            "VALUES\n" + //
            "   (\n" + //
            "       #{dto.orderId},\n" + //
            "       #{dto.userId},\n" + //
            "       #{dto.resourceId},\n" + //
            "       #{dto.createDate},\n" + //
            "       #{dto.finishDate},\n" + //
            "       #{dto.deliverInfo},\n" + //
            "       #{dto.addressInfo})")
    void insertUserOrderHis(@Param("dto") UserOrderHisDto dto);

    @Select("select * from USER_ORDER_HIS")
    @ResultType(UserOrderHisDto.class)
    DemoResourceDto selectAllUserOrderHis();
}
