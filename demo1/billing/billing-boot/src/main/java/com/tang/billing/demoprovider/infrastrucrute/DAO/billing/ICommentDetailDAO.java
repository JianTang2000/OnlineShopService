package com.tang.billing.demoprovider.infrastrucrute.DAO.billing;

import com.tang.billing.demoprovider.infrastrucrute.model.CommentDetailDto;
import com.tang.billing.demoprovider.infrastrucrute.model.DemoResourceDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

public interface ICommentDetailDAO {

    @Insert("INSERT INTO COMMENT_DETAIL (\n" + //
            "   COMMENT_ID,\n" + //
            "   RESOURCE_ID,USER_ID,COMMENT_LEVEL,COMMENT_CONTENT\n" + //
            ")\n" + //
            "VALUES\n" + //
            "   (\n" + //
            "       #{dto.commentId},\n" + //
            "       #{dto.resourceId},\n" + //
            "       #{dto.userId},\n" + //
            "       #{dto.commentLevel},\n" + //
            "       #{dto.commentContent})")
    void insertComment(@Param("dto") CommentDetailDto dto);

    @Select("select * from COMMENT_DETAIL")
    @ResultType(CommentDetailDto.class)
    DemoResourceDto selectAllComment();
}
