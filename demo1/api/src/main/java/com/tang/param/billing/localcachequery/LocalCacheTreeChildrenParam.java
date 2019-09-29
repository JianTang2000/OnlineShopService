package com.tang.param.billing.localcachequery;

import java.io.Serializable;

/**
 * < 树的节点等级，必为2 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/12/11 <br>
 */
public class LocalCacheTreeChildrenParam implements Serializable {

    // 构造函数使得level永远为2
    public LocalCacheTreeChildrenParam() {
        level = "2";
    }

    /**
     * < 缓存map所在bean的bean名,也就是root级节点名 >
     */
    private String beanMapName;

    /**
     * < 父节点的label，也就是1级节点名 >
     */
    private String parentLabel;

    /**
     * < 本节点的label，也就是本节点名 >
     */
    private String label;

    /**
     * < 树的节点等级，必为2 >
     */
    private String level;

    /**
     * < 2级节点的value >
     */
    private String cacheValue;

    public String getBeanMapName() {
        return beanMapName;
    }

    public void setBeanMapName(String beanMapName) {
        this.beanMapName = beanMapName;
    }

    public String getParentLabel() {
        return parentLabel;
    }

    public void setParentLabel(String parentLabel) {
        this.parentLabel = parentLabel;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCacheValue() {
        return cacheValue;
    }

    public void setCacheValue(String cacheValue) {
        this.cacheValue = cacheValue;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }


}
