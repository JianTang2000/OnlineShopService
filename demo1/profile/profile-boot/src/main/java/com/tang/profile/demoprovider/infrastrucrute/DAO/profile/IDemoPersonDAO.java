package com.tang.profile.demoprovider.infrastrucrute.DAO.profile;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.tang.profile.demoprovider.infrastrucrute.model.DemoOpusDetailDto;
import com.tang.profile.demoprovider.infrastrucrute.model.DemoPersonDto;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/10/23 <br>
 */
public interface IDemoPersonDAO {

    /**
     * < select All > <br>
     *
     * @auther: tang
     * @return < List >
     */
//    @Select("select * from DEMO_PERSON where 1=1 order by person_id")
//    @ResultType(DemoPersonDto.class)
    List<DemoPersonDto> selectAll();

    /**
     * < selectById > <br>
     *
     * @auther: tang
     * @param id < id >
     * @return < DemoPersonDto >
     */
    @Select("select * from DEMO_PERSON where person_id =  #{param1}")
    @ResultType(DemoPersonDto.class)
    DemoPersonDto selectById(Long id);

    /**
     * <tang:DemoPerson，有个字段是CLOB类型，要关注下 > <br>
     *
     * @param demoPersonDto < >
     */
    @Insert("INSERT INTO DEMO_PERSON (\n" + //
        "   PERSON_ID,\n" + //
        "   PERSON_NAME,OPUS_ID,person_Photo_Id,person_Mv_Id,desc,state,state_Date\n" + //
        ")\n" + //
        "VALUES\n" + //
        "   (\n" + //
        "       #{dto.personId},\n" + //
        "       #{dto.personName},\n" + //
        "       #{dto.opusId},\n" + //
        "       #{dto.personPhotoId},\n" + //
        "       #{dto.personMvId},\n" + //
        "       #{dto.desc},\n" + //
        "       #{dto.state},\n" + //
        "       #{dto.stateDate})")
    void createPerson(@Param("dto") DemoPersonDto demoPersonDto);

}
