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
    private static final long serialVersionUID = -2358091730841162550L;

    /**
     * < 用户名，非空则 指定搜索 该用户发布的资源，可空>
     */
    private Long userId;

    /**
     * < 界面录入的搜索关键字，可以为空 >
     */
    private String input;

    /**
     * < 搜索范围，分商品1 ，用戶3 兩種，可空，空默认全部 >
     */
    private String searchType;


    /**
     * < 随便匹配是否选中，可空，前台传空会默认为false >
     */
    private boolean roughMatch;

    /**
     * 物品类别，从1--12，可空，空则不指定类别
     */
    private Long item;

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

    public boolean getRoughMatch() {
        return roughMatch;
    }

    public void setRoughMatch(boolean roughMatch) {
        this.roughMatch = roughMatch;
    }

    public Long getItem() {
        return item;
    }

    public void setItem(Long item) {
        this.item = item;
    }
}
