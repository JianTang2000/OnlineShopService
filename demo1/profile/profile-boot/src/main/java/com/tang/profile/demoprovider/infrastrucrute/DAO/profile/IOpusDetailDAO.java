package com.tang.profile.demoprovider.infrastrucrute.DAO.profile;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.tang.profile.demoprovider.infrastrucrute.model.DemoOpusDetailDto;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/9/29 <br>
 */
public interface IOpusDetailDAO {

    /**
     * <tang:新增一个OpusDetail，重点是有个字段是BLOB类型，要关注下 > <br>
     *
     * @param demoOpusDetailDto < >
     */
    @Insert("INSERT INTO OPUS_DETAIL (\n" + //
        "   OPUS_DETAIL_ID,\n" + //
        "   OPUS_DETAIL\n" + //
        ")\n" + //
        "VALUES\n" + //
        "   (\n" + //
        "       #{dto.opusDetailId},\n" + //
        "       #{dto.opusDetail})")
    void newOpusDetail(@Param("dto") DemoOpusDetailDto demoOpusDetailDto);

    /**
     * <tang: 全查生效的数据，升序排列 > <br>
     *
     * @return < >
     */
    @Select("SELECT\n" + //
        "   OPUS_DETAIL_ID,\n" + //
        "   OPUS_DETAIL\n" + //
        "FROM\n" + //
        "   OPUS_DETAIL\n" + //
        "WHERE\n" + //
        "   1=1 order by OPUS_DETAIL_ID ASC")
    @ResultType(DemoOpusDetailDto.class)
    List<DemoOpusDetailDto> selectAllOpusDetail();
}
