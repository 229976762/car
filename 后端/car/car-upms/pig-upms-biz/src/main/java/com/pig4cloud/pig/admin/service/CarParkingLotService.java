package com.pig4cloud.pig.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.admin.api.dto.CarParkingLotDTO;
import com.pig4cloud.pig.admin.api.entity.CarParkingLotEntity;

public interface CarParkingLotService extends IService<CarParkingLotEntity> {

    CarParkingLotDTO getCarParkingLotPageWx(Long lotId);
}