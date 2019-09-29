package com.tang.profile.demoprovider.infrastrucrute.DAO.profile;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.tang.profile.demoprovider.infrastrucrute.DAO.profile.sqlProvider.DemoUserDaoSqlProvider;
import com.tang.profile.demoprovider.infrastrucrute.model.DemoPersonDto;
import com.tang.profile.demoprovider.infrastrucrute.model.DemoResourceDto;
import com.tang.profile.demoprovider.infrastrucrute.model.DemoUserDetailDto;
import com.tang.profile.demoprovider.infrastrucrute.model.DemoUserDto;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/20 <br>
 */
public interface IDemoUserDAO {

    /**
     * < 全查，不论用户状态 > <br>
     *
     * @auther: tang
     * @return < DemoPersonDto >
     */
    @Select("SELECT * FROM DEMO_USER")
    @ResultType(DemoUserDto.class)
    List<DemoUserDto> selectAllUser();

    /**
     * < 指定用户状态全查 > <br>
     *
     * @auther: tang
     * @param state < "0","1","2" >
     * @return < DemoPersonDto >
     */
    @Select("SELECT * FROM DEMO_USER WHERE STATE = #{param1}")
    @ResultType(DemoUserDto.class)
    List<DemoUserDto> selectAllUserByState(String state);

    /**
     * < 指定用户Id查询寻DemoUserDto > <br>
     *
     * @auther: tang
     * @param userId < >
     * @return < DemoPersonDto >
     */
    @Select("SELECT * FROM DEMO_USER WHERE USER_ID = #{param1}")
    @ResultType(DemoUserDto.class)
    DemoUserDto selectUserById(Long userId);

    /**
     * < 指定用户Name查询寻DemoUserDto > <br>
     *
     * @auther: tang
     * @param name < >
     * @return < DemoPersonDto >
     */
    @Select("SELECT * FROM DEMO_USER WHERE USER_NAME = #{param1}")
    @ResultType(DemoUserDto.class)
    DemoUserDto selectUserByName(String name);

    /**
     * < 指定用户Email查询寻DemoUserDto > <br>
     *
     * @auther: tang
     * @param email < >
     * @return < DemoPersonDto >
     */
    @Select("SELECT * FROM DEMO_USER WHERE EMAIL = #{param1}")
    @ResultType(DemoUserDto.class)
    DemoUserDto selectUserByeEmail(String email);

    /**
     * < selectUserByNameAndKey > <br>
     *
     * @auther: tang
     * @param name < >
     * @param key < >
     * @return < DemoPersonDto >
     */
    @Select("select * from DEMO_USER where user_name = #{name} and lost_found_key = #{key} ")
    @ResultType(DemoUserDto.class)
    DemoUserDto selectUserByNameAndKey(@Param("name") String name, @Param("key") Long key);

    /**
     * < selectUserByEmailAndKey > <br>
     *
     * @auther: tang
     * @param email < >
     * @param key < >
     * @return < DemoPersonDto >
     */
    @Select("select * from DEMO_USER where email = #{email} and lost_found_key = #{key}")
    @ResultType(DemoUserDto.class)
    DemoUserDto selectUserByEmailAndKey(@Param("email") String email, @Param("key") Long key);

    /**
     * < 指定用户Id查询寻DemoUserDetailDto > <br>
     *
     * @auther: tang
     * @param userId < >
     * @return < DemoPersonDto >
     */
    @Select("SELECT * FROM DEMO_USER_DETAIL WHERE USER_ID = #{param1}")
    @ResultType(DemoUserDetailDto.class)
    DemoUserDetailDto selectUserDetailById(Long userId);

    /**
     * < 常见的Insert，没什么特殊字段 > <br>
     *
     * @param dto < >
     */
    @Insert("INSERT INTO DEMO_USER (\n" + //
        "   USER_ID,\n" + //
        "   USER_NAME,STATE,STATE_DATE,CREATE_DATE,EMAIL,LOST_FOUND_KEY\n" + //
        ")\n" + //
        "VALUES\n" + //
        "   (\n" + //
        "       #{dto.userId},\n" + //
        "       #{dto.userName},\n" + //
        "       #{dto.state},\n" + //
        "       #{dto.stateDate},\n" + //
        "       #{dto.createDate},\n" + //
        "       #{dto.email},\n" + //
        "       #{dto.lostFoundKey})")
    void createUser(@Param("dto") DemoUserDto dto);

    /**
     * < Insert，有个BLOB字段 > <br>
     *
     * @param dto < >
     */
    @Insert("INSERT INTO DEMO_USER_DETAIL (\n" + //
        "   USER_ID,\n" + //
        "   USER_DETAIL\n" + //
        ")\n" + //
        "VALUES\n" + //
        "   (\n" + //
        "       #{dto.userId},\n" + //
        "       #{dto.userDetail})")
    void createUserDetail(@Param("dto") DemoUserDetailDto dto);

    /**
     * < update > <br>
     *
     * @param id < >
     * @param password < >
     */
    @Update("UPDATE DEMO_USER SET PASS_WORD = #{password} WHERE USER_ID =  #{id}")
    void updateUserPassword(@Param("id") Long id, @Param("password") String password);

    /**
     * < 全查，不论用户状态 > <br>
     *
     * @param pageSize < feikong >
     * @param pageNum < feikong >
     * @auther: tang
     * @return < DemoPersonDto >
     */
    // @Select("select user_id from DEMO_USER where user_id not in (select user_id from DEMO_USER_DETAIL)")
    @SelectProvider(type = DemoUserDaoSqlProvider.class, method = "selectUserWithoutDetail")
    @ResultType(Long.class)
    List<Long> selectUserWithoutDetail(@Param("pageSize") int pageSize, @Param("pageNum") int pageNum);

}
