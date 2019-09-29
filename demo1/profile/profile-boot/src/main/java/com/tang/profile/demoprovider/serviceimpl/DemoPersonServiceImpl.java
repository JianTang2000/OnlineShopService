package com.tang.profile.demoprovider.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tang.api.billing.DemoPersonService;
import com.tang.param.billing.GetDataResultParam;
import com.tang.param.billing.DemoPersonParam;
import com.tang.profile.demoprovider.infrastrucrute.DAO.profile.IDemoPersonDAO;
import com.tang.profile.demoprovider.infrastrucrute.model.DemoPersonDto;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/10/26 <br>
 */
@Service("DemoPersonService")
public class DemoPersonServiceImpl implements DemoPersonService {

    /**
     * < 日志 >
     */
    private Logger logger = LoggerFactory.getLogger(DemoPersonServiceImpl.class);

    @Autowired
    IDemoPersonDAO demoPersonDAO;

    @Override
    public void createPerson(DemoPersonParam param) {
        DemoPersonDto dto = new DemoPersonDto();
        BeanUtils.copyProperties(param, dto, DemoPersonDto.class);
        demoPersonDAO.createPerson(dto);
    }

    @Override
    public DemoPersonParam queryPersonById(Long id) {
        DemoPersonParam param = new DemoPersonParam();
        DemoPersonDto dto;
        dto = demoPersonDAO.selectById(id);
        BeanUtils.copyProperties(dto, param, DemoPersonParam.class);
        return param;
    }

    @Override
    public List<DemoPersonParam> queryAllPerson() {
        List<DemoPersonParam> params = new ArrayList<>();
        List<DemoPersonDto> dtos = demoPersonDAO.selectAll();
        for (DemoPersonDto dto : dtos) {
            DemoPersonParam param = new DemoPersonParam();
            BeanUtils.copyProperties(dto, param, DemoPersonParam.class);
            params.add(param);
        }
        return params;
    }

    @Override
    public void likeThisSong(Long userId, Long opusId) {
        logger.info("....likeThisSong start, userId is {}, opusId is {}", userId, opusId);

    }

    @Override
    public List<GetDataResultParam> queryByAbstractInput(String input) {
        List<GetDataResultParam> result = new ArrayList<>();
        return result;
    }
}
