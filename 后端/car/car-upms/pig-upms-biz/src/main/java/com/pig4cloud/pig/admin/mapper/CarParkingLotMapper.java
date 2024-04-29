package com.pig4cloud.pig.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pig.admin.api.dto.CarParkingLotDTO;
import com.pig4cloud.pig.admin.api.entity.CarParkingLotEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CarParkingLotMapper extends BaseMapper<CarParkingLotEntity> {


    CarParkingLotDTO getCarParkingLotPageWx(Long lotId);
}