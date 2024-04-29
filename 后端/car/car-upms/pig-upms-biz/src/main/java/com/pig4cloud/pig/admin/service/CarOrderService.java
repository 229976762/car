package com.pig4cloud.pig.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.admin.api.dto.CarOrderDTO;
import com.pig4cloud.pig.admin.api.entity.CarOrderEntity;
import java.util.List;

public interface CarOrderService extends IService<CarOrderEntity> {

    List<CarOrderDTO> selectByUserId(Long userid);

	Boolean saveWxOrder(CarOrderDTO carOrder);
}