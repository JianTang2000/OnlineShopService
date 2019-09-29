package com.tang.param.billing;

import java.io.Serializable;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/13 <br>
 */
public class HandleOperateParam implements Serializable {

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = -2780491730841163550L;

    /**
     * < >
     */
    private Long userId;

    /**
     * < >
     */
    private Long resourceId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
}
