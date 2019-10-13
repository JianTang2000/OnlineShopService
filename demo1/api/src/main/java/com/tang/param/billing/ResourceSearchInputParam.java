package com.tang.param.billing;

import java.io.Serializable;

/**
 * < 前台请求“查询资源文件列表”时传到后台的包装类，不论是精确/模糊搜索，指定/不指定 类型/搜索范围，都可以用这个类传递 ><br>
 *
 * @author tang.jian<br>
 */
public class ResourceSearchInputParam implements Serializable {

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = -1780401730241163550L;

    /**
     * < 用户名，不可空 >
     */
    private Long userId;

    /**
     * < 界面录入的搜索关键字，可以为空 >
     */
    private String input;

    /**
     * < 搜索范围，分商品，用戶兩種，不可空 >
     */
    private String searchType;


    /**
     * < 粗略匹配，隨緣搜索，不可空 >
     */
    private boolean RoughMatch;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public boolean isRoughMatch() {
        return RoughMatch;
    }

    public void setRoughMatch(boolean roughMatch) {
        RoughMatch = roughMatch;
    }
}
