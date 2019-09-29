package com.tang.billing.demoprovider.infrastrucrute.DAO.billing;

import com.tang.billing.demoprovider.infrastrucrute.model.DemoResourceDetailDto;
import com.tang.billing.demoprovider.infrastrucrute.model.DemoResourceDto;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

public interface IDemoResourceDAO {

    @Select("select * from DEMO_RESOURCE")
    @ResultType(DemoResourceDetailDto.class)
    DemoResourceDto queryResource();

    @Select("select * from DEMO_RESOURCE where resource_id = #{param1}")
    @ResultType(DemoResourceDetailDto.class)
    DemoResourceDto queryResourceById(Long resourceId);

    @Select("select * from DEMO_RESOURCE_DETAIL where resource_id = #{param1}")
    @ResultType(DemoResourceDetailDto.class)
    DemoResourceDetailDto queryResourceDetailById(Long resourceId);
}
