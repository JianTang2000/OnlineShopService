package com.tang.api.billing;

import java.io.IOException;
import java.util.List;

import com.tang.param.billing.DemoOpusDetailParam;

/**
 * < Opus_Detail表: 目前设计的仅仅用来存放音频/图片等BLOB字段，单独成表，后续可能查询起来会快些><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/9/29 <br>
 */
public interface OpusDetailService {
    /**
     * <tang:新增 > <br>
     *
     * @param filePath < 被传的文件的文件全路径，包括路径和文件名 >
     */
    void newOpusDetail(String filePath) throws IOException;

    /**
     * <tang: 全查所有的detail，这个接口，后面看怎么做到让如此大的返回结果，合理的在网络间传输， 目前表里就几首，一首5M大概20M以内 > <br>
     *
     * @return < detail立标，不出意外的话，这个数据结构占用的内存会非常的大…… >
     */
    List<DemoOpusDetailParam> queryAllOpusDetail();



}
