package com.tang.param.billing.localcachequery;

import java.io.Serializable;
import java.util.List;

/**
 * < 树的节点等级，必为1 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/12/11 <br>
 */
public class LocalCacheTreeParentParam implements Serializable {

    // 构造函数使得level永远为1
    public LocalCacheTreeParentParam() {
        level = "1";
    }

    /**
     * < 本节点的label >
     */
    private String label;

    /**
     * < 树的节点等级，必为1 >
     */
    private String level;

    /**
     * < Children >
     */
    private List<LocalCacheTreeChildrenParam> children;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<LocalCacheTreeChildrenParam> getChildren() {
        return children;
    }

    public void setChildren(List<LocalCacheTreeChildrenParam> children) {
        this.children = children;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
