package com.pig4cloud.pig.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pig.admin.api.dto.CarParkingLotDTO;
import com.pig4cloud.pig.admin.api.entity.CarFeeRuleEntity;
import org.apache.ibatis.annotations.Mapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Mapper
public interface CarFeeRuleMapper extends BaseMapper<CarFeeRuleEntity> {


    CarParkingLotDTO selectRate(Long lotId, LocalDateTime currentTime);
}