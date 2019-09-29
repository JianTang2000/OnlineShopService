package com.tang.profile.demoprovider.infrastrucrute.DAO.profile.sqlProvider;

import java.util.Map;

import com.tang.base.util.pagehelp.PageHelp;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/27 <br>
 */
public class DemoUserDaoSqlProvider {

    /**
     * < 返回SQL> <br>
     *
     * @auther: tang.jian
     * @taskId: < ><br>
     * @param param < >
     * @return < >
     */
    public String selectUserWithoutDetail(Map<String, Object> param) {
        String sql = "select user_id from DEMO_USER where user_id not in (select user_id from DEMO_USER_DETAIL)";
        int pageSize = (int) param.get("pageSize");
        int pageNum = (int) param.get("pageNum");
        return PageHelp.OraclePageSQL(sql, pageSize, pageNum);
    }
}
