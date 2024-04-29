package com.pig4cloud.pig.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.CarParkingSpaceEntity;
import com.pig4cloud.pig.admin.mapper.CarParkingSpaceMapper;
import com.pig4cloud.pig.admin.service.CarParkingSpaceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 停车位表
 *
 * @author pig
 * @date 2024-04-11 17:30:49
 */
@Service
@AllArgsConstructor
public class CarParkingSpaceServiceImpl extends ServiceImpl<CarParkingSpaceMapper, CarParkingSpaceEntity> implements CarParkingSpaceService {

	private final CarParkingSpaceMapper carParkingSpaceMapper;


	@Override
	public void updateSpaceStatus(){
		LocalDateTime time = LocalDateTime.now().minusMinutes(9);
		carParkingSpaceMapper.updateBatchById(time);
	}

}