package com.tang.base.util.pagehelp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tang.base.util.appEngine.AppEngine;

/**
 * < 自写的SQL分页工具，查阅了部分资料，不外乎两种方式。
 * 1:全部查出来，内存里面切分,返回部分;(如RowBounds这种物理分页这仅仅为了分页而分页,其实仍然是全部查询，并不能提高效率，故这里不实现这种----------------------------------------------------------------------------------------------------
 * 2:SQL查询DB的时候就使用分页SQL，然后返回分页的结果;(这里分页有多种写法，采取了一种比较高效的，同时支持原SQL的排序或者不排序，为了提高效率把限制条件放入内层SQL，提高DB本身的查询效率))--------------------------------------------------------
 * ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/27 <br>
 */
public final class PageHelp {
    /**
     * < >
     */
    private PageHelp() {

    }

    /**
     * < 日志 >
     */
    private static Logger logger = LoggerFactory.getLogger(PageHelp.class);

    /**
     * < SQL分页的比较高效的版本(框架就是用的这种);根据入参,将SQL转成分页查询SQL。
     * (rowNumber在表数据没有被修改的情况下是不变的，所以每次查询的结果是一致的，所以分页的结果是稳定的，但分页查询的过程中发生了数据删减，修改或者新增可能会导致rowNumber改变，这个情况倒不太容易发生) > <br>
     *
     * @auther: tang
     * @param sql <原SQL,对是否Order By没有限制(不过不确定排序是否影响效率),必须是Select语句 > < 不可空 >
     * @param pageSize < 分页量,比如2000 > < 不可空 >
     * @param pageNum < 页码数,从1开始 > < 不可空 >
     * @return < 返回分页SQL，如发生异常，返回原SQL >
     */
    public static String OraclePageSQL(String sql, int pageSize, int pageNum) {
        String result;
        try {
            int endNum = pageSize * pageNum;
            int startNum = pageSize * (pageNum - 1);
            String front = "SELECT * FROM (SELECT TMP_PAGE.*, ROWNUM ROW_ID FROM (";
            String end = ")TMP_PAGE WHERE ROWNUM <=" + endNum + ") WHERE ROW_ID > " + startNum;
            result = front + sql + end;
            logger.info("OraclePageSQL ret is {}", result);
            return result;
        }
        catch (RuntimeException e) {
            logger.info("OraclePageSQL failed! ret no page sql");
            return sql;
        }
    }

    /**
     * <这个效率比较差，是最简单版本，以可用索引检索等来提高效率 > <br>
     *
     * @auther: tang
     * @param sql < >
     * @param pageSize < >
     * @param pageNum < >
     * @return < >
     */
    public static String MySQLPageSQL(String sql, int pageSize, int pageNum) {
        int endNum = pageSize * pageNum;
        int startNum = pageSize * (pageNum - 1);
        String end = "limit" + startNum + "," + endNum;
        return sql + end;
    }

}
