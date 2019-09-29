package com.tang.profile.demoprovider.infrastrucrute.model;

import java.util.Date;

/**
 * < acct表存在于cc库，这里写出来主要是为了进行不同数据库用户之间的一些测试 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/9/13 <br>
 */
public class acctDto {

    /**
     * < >
     */
    private Long acctId;

    /**
     * < >
     */
    private String billAddress;

    /**
     * < >
     */
    private Date createdDate;

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public String getBillAddress() {
        return billAddress;
    }

    public void setBillAddress(String billAddress) {
        this.billAddress = billAddress;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
