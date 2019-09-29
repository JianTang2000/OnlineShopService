package com.tang.profile.demoprovider.serviceimpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tang.api.billing.OpusDetailService;
import com.tang.param.billing.DemoOpusDetailParam;
import com.tang.profile.demoprovider.infrastrucrute.DAO.profile.IOpusDetailDAO;
import com.tang.profile.demoprovider.infrastrucrute.model.DemoOpusDetailDto;
import com.tang.base.util.FileHelper;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/9/29 <br>
 */
@Service("opusDetailService")
public class OpusDetailServiceImpl implements OpusDetailService {

    /**
     * < >
     */
    @Autowired
    IOpusDetailDAO opusDetailDAO;

    @Override
    public void newOpusDetail(String filePath) throws IOException {
        byte[] pic = FileHelper.readFileToByte(filePath);
        DemoOpusDetailDto dto = new DemoOpusDetailDto();
        dto.setOpusDetailId(100002L); // 后面改成取序列
        dto.setOpusDetail(pic);
        opusDetailDAO.newOpusDetail(dto);
    }

    /**
     * <tang: 全查所有的detail，这个接口，后面看怎么做到让如此大的返回结果，合理的在网络间传输， 目前表里就几首，一首5M大概20M以内 > <br>
     *
     * @return < detail立标，不出意外的话，这个数据结构占用的内存会非常的大…… >
     */
    @Override
    public List<DemoOpusDetailParam> queryAllOpusDetail() {
        List<DemoOpusDetailParam> result = new ArrayList<>();
        List<DemoOpusDetailDto> ret = opusDetailDAO.selectAllOpusDetail();
        for (DemoOpusDetailDto dto : ret) {
            DemoOpusDetailParam param = new DemoOpusDetailParam();
            BeanUtils.copyProperties(dto, param, DemoOpusDetailParam.class);
            result.add(param);
        }
        return result;
    }
}
