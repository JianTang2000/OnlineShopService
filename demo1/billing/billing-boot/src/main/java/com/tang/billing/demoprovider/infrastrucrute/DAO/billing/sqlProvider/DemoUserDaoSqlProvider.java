package com.tang.billing.demoprovider.infrastrucrute.DAO.billing.sqlProvider;

import com.tang.base.util.pagehelp.PageHelp;

import java.util.Map;

public class DemoUserDaoSqlProvider {

    /**
     * < 返回SQL> <br>
     *
     * @auther: tang.jian
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
