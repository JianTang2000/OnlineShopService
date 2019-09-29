package com.tang.profile.demoprovider.infrastrucrute.DAO.profile;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/9/13 <br>
 */
public interface IAcctDAO {

    /**
     * < 查询acct的所有记录数，由于这个表数据较大，大概有十万数据，后面可以用这个表来做些性能上面的测试 > <br>
     *
     * @auther: tang.jian
     * @return < >
     */
    Long querySizeNumberOfTableAcct() throws Exception;
}
