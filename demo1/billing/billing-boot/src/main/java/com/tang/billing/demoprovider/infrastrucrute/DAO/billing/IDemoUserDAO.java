package com.tang.billing.demoprovider.infrastrucrute.DAO.billing;

import com.tang.billing.demoprovider.infrastrucrute.DAO.billing.sqlProvider.DemoUserDaoSqlProvider;
import com.tang.billing.demoprovider.infrastrucrute.model.DemoUserDetailDto;
import com.tang.billing.demoprovider.infrastrucrute.model.DemoUserDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IDemoUserDAO {

    /**
     * < 常见的Insert，没什么特殊字段 > <br>
     *
     * @param dto < >
     */
    @Insert("INSERT INTO DEMO_USER (\n" + //
            "   USER_ID,\n" + //
            "   USER_NAME,STATE,STATE_DATE,CREATE_DATE,EMAIL,LOST_FOUND_KEY,PHONE_NUMBER,PASS_WORD\n" + //
            ")\n" + //
            "VALUES\n" + //
            "   (\n" + //
            "       #{dto.userId},\n" + //
            "       #{dto.userName},\n" + //
            "       #{dto.state},\n" + //
            "       #{dto.stateDate},\n" + //
            "       #{dto.createDate},\n" + //
            "       #{dto.email},\n" + //
            "       #{dto.lostFoundKey},\n" + //
            "       #{dto.phoneNumber},\n" + //
            "       #{dto.passWord})")
    void insertUser(@Param("dto") DemoUserDto dto);

    @Update("UPDATE DEMO_USER SET PASS_WORD = #{password} WHERE USER_ID =  #{id}")
    void updateUserPassword(@Param("id") Long id, @Param("password") String password);

    @Update("UPDATE DEMO_USER SET EMAIL = #{email} WHERE USER_ID =  #{id}")
    void updateUserEmail(@Param("id") Long id, @Param("email") String email);

    @Update("UPDATE DEMO_USER SET PHONE_NUMBER = #{phoneNum} WHERE USER_ID =  #{id}")
    void updateUserPhoneNum(@Param("id") Long id, @Param("phoneNum") Long phoneNum);

    @Select("SELECT * FROM DEMO_USER ")
    @ResultType(DemoUserDto.class)
    List<DemoUserDto> selectAllUser();

    @Select("SELECT user_name FROM DEMO_USER")
    @ResultType(String.class)
    List<String> selectAllUserNames();

    @Select("SELECT * FROM DEMO_USER WHERE USER_ID = #{param1}")
    @ResultType(DemoUserDto.class)
    DemoUserDto selectUserById(Long userId);

    /**
     * < 指定用户状态全查 > <br>
     *
     * @param state < "A","X" >
     * @return < DemoPersonDto >
     * @auther: tang
     */
    @Select("SELECT * FROM DEMO_USER WHERE STATE = #{param1}")
    @ResultType(DemoUserDto.class)
    List<DemoUserDto> selectAllUserByState(String state);

    @Select("SELECT * FROM DEMO_USER WHERE USER_NAME = #{param1}")
    @ResultType(DemoUserDto.class)
    DemoUserDto selectUserByName(String name);

    @Select("SELECT * FROM DEMO_USER WHERE EMAIL = #{param1}")
    @ResultType(DemoUserDto.class)
    DemoUserDto selectUserByeEmail(String email);

    @Select("SELECT * FROM DEMO_USER WHERE PHONE_NUMBER = #{phoneNumber}")
    @ResultType(DemoUserDto.class)
    DemoUserDto selectUserByPhone(@Param("phoneNumber") Long phoneNumber);

    @Select("select * from DEMO_USER where user_name = #{name} and lost_found_key = #{key} ")
    @ResultType(DemoUserDto.class)
    DemoUserDto selectUserByNameAndKey(@Param("name") String name, @Param("key") Long key);

    @Select("select * from DEMO_USER where email = #{email} and lost_found_key = #{key}")
    @ResultType(DemoUserDto.class)
    DemoUserDto selectUserByEmailAndKey(@Param("email") String email, @Param("key") Long key);

    /////////////////////////////////////////////////////////////////

    /**
     * < Insert，有个BLOB字段 > <br>
     *
     * @param dto < >
     */
    @Insert("INSERT INTO DEMO_USER_DETAIL (\n" + //
            "   USER_ID,\n" + //
            "   USER_DETAIL,\n" + //
            "   ADDRESS_1_LINE_1,\n" + //
            "   ADDRESS_1_LINE_2,\n" + //
            "   ADDRESS_1_POST_CODE,\n" + //
            "   ADDRESS_2_LINE_1,\n" + //
            "   ADDRESS_2_LINE_2,\n" + //
            "   ADDRESS_2_POST_CODE,\n" + //
            "   ADDRESS_3_LINE_1,\n" + //
            "   ADDRESS_3_LINE_2,\n" + //
            "   ADDRESS_3_POST_CODE\n" + //
            ")\n" + //
            "VALUES\n" + //
            "   (\n" + //
            "       #{dto.userId},\n" + //
            "       #{dto.userDetail},\n" + //
            "       #{dto.address1Line1},\n" + //
            "       #{dto.address1Line2},\n" + //
            "       #{dto.address1PostCode},\n" + //
            "       #{dto.address2Line1},\n" + //
            "       #{dto.address2Line2},\n" + //
            "       #{dto.address2PostCode},\n" + //
            "       #{dto.address3Line1},\n" + //
            "       #{dto.address3Line2},\n" + //
            "       #{dto.address3PostCode}" +
            ")")
    void createUserDetail(@Param("dto") DemoUserDetailDto dto);

    @Update("UPDATE DEMO_USER_DETAIL SET USER_DETAIL = #{detail} WHERE USER_ID =  #{id}")
    void updateUserDetail(@Param("id") Long id, @Param("detail") byte[] detail);

    @Select("SELECT * FROM DEMO_USER_DETAIL WHERE USER_ID = #{param1}")
    @ResultType(DemoUserDetailDto.class)
    DemoUserDetailDto selectUserDetailById(Long userId);

    /**
     * < 分页查询，查找所有没有头像的用户 > <br>
     *
     * @param pageSize < feikong >
     * @param pageNum  < feikong >
     * @return < DemoPersonDto >
     * @auther: tang
     */
    @SelectProvider(type = DemoUserDaoSqlProvider.class, method = "selectUserWithoutDetail")
    @ResultType(Long.class)
    List<Long> selectUserWithoutDetail(@Param("pageSize") int pageSize, @Param("pageNum") int pageNum);
}
