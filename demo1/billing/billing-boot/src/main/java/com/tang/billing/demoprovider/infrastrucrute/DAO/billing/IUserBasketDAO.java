package com.tang.billing.demoprovider.infrastrucrute.DAO.billing;

import com.tang.billing.demoprovider.infrastrucrute.model.DemoResourceDto;
import com.tang.billing.demoprovider.infrastrucrute.model.UserBasketDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

public interface IUserBasketDAO {

    @Insert("INSERT INTO USER_BASKET (\n" + //
            "   BASKET_ID,\n" + //
            "   USER_ID,RESOURCE_ID,ITEM_NUM\n" + //
            ")\n" + //
            "VALUES\n" + //
            "   (\n" + //
            "       #{dto.basketId},\n" + //
            "       #{dto.userId},\n" + //
            "       #{dto.resourceId},\n" + //
            "       #{dto.itemNum})")
    void insertBasket(@Param("dto") UserBasketDto dto);

    @Select("select * from USER_BASKET")
    @ResultType(UserBasketDto.class)
    DemoResourceDto selectAllBasket();
}
