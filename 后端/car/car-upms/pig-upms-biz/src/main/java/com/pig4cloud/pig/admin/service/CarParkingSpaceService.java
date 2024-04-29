package com.pig4cloud.pig.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.admin.api.entity.CarParkingSpaceEntity;

public interface CarParkingSpaceService extends IService<CarParkingSpaceEntity> {

	void updateSpaceStatus();
}