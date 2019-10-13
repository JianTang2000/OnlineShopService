package com.tang.billing.demoprovider.infrastrucrute.DAO.billing;

import com.tang.billing.demoprovider.infrastrucrute.model.DemoResourceDetailDto;
import com.tang.billing.demoprovider.infrastrucrute.model.DemoResourceDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

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

    @Select("select * from DEMO_RESOURCE where resource_id = #{param1}")
    @ResultType(DemoResourceDetailDto.class)
    DemoResourceDto queryResourceById(Long resourceId);

    /////////////////////////////////////////////////////////////////

    @Insert("INSERT INTO DEMO_RESOURCE_DETAIL (\n" + //
            "   RESOURCE_ID,\n" + //
            "   STOCK,HOT,PHOTO1,PHOTO2,PHOTO3,PHOTO4,PHOTO5,PHOTO_DETAIL\n" + //
            ")\n" + //
            "VALUES\n" + //
            "   (\n" + //
            "       #{dto.resourceId},\n" + //
            "       #{dto.stock},\n" + //
            "       #{dto.hot},\n" + //
            "       #{dto.photo1},\n" + //
            "       #{dto.photo2},\n" + //
            "       #{dto.photo3},\n" + //
            "       #{dto.photo4},\n" + //
            "       #{dto.photo5},\n" + //
            "       #{dto.photoDetail})")
    void insertDemoResourceDetail(@Param("dto") DemoResourceDetailDto dto);

    @Select("select * from DEMO_RESOURCE_DETAIL where resource_id = #{param1}")
    @ResultType(DemoResourceDetailDto.class)
    DemoResourceDetailDto queryResourceDetailById(Long resourceId);
}
