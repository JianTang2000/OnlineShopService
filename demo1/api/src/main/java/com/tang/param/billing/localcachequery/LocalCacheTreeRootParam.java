package com.tang.param.billing.localcachequery;

import java.io.Serializable;
import java.util.List;

/**
 * < 树的节点等级，必为0 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/12/13 <br>
 */
public class LocalCacheTreeRootParam implements Serializable {

    // 构造函数使得level永远为0
    public LocalCacheTreeRootParam() {
        level = "0";
    }

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = -1780419730241163550L;

    /**
     * < 就写beanMapName用以展示 >
     */
    private String label;

    /**
     * < 树的节点等级，必为0 >
     */
    private String level;

    /**
     * < 粗略大小，按照一定算法估算出来的堆内存大小，并非完全准确 >
     */
    private String mapRoughSize;

    /**
     * < 1级key数量 >
     */
    private String keyLevel1Count;

    /**
     * < 2级key数量 >
     */
    private String keyLevel2Count;

    public List<LocalCacheTreeParentParam> getChildren() {
        return children;
    }

    public void setChildren(List<LocalCacheTreeParentParam> children) {
        this.children = children;
    }

    /**
     * < Children >
     */
    private List<LocalCacheTreeParentParam> children;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMapRoughSize() {
        return mapRoughSize;
    }

    public void setMapRoughSize(String mapRoughSize) {
        this.mapRoughSize = mapRoughSize;
    }

    public String getKeyLevel1Count() {
        return keyLevel1Count;
    }

    public void setKeyLevel1Count(String keyLevel1Count) {
        this.keyLevel1Count = keyLevel1Count;
    }

    public String getKeyLevel2Count() {
        return keyLevel2Count;
    }

    public void setKeyLevel2Count(String keyLevel2Count) {
        this.keyLevel2Count = keyLevel2Count;
    }

    public String getLevel() {
        return level;
    }
}
