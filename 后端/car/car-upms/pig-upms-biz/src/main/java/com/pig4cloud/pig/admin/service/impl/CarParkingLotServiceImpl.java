package com.pig4cloud.pig.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.dto.CarParkingLotDTO;
import com.pig4cloud.pig.admin.api.entity.CarParkingLotEntity;
import com.pig4cloud.pig.admin.mapper.CarFeeRuleMapper;
import com.pig4cloud.pig.admin.mapper.CarParkingLotMapper;
import com.pig4cloud.pig.admin.mapper.CarParkingSpaceMapper;
import com.pig4cloud.pig.admin.service.CarParkingLotService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 停车路段表
 *
 * @author pig
 * @date 2024-04-11 16:43:40
 */
@Service
@AllArgsConstructor
public class CarParkingLotServiceImpl extends ServiceImpl<CarParkingLotMapper, CarParkingLotEntity> implements CarParkingLotService {

	private final CarParkingLotMapper carParkingLotMapper;

	private final CarParkingSpaceMapper carParkingSpaceMapper;

	private final CarFeeRuleMapper carFeeRuleMapper;

	@Override
	public CarParkingLotDTO getCarParkingLotPageWx(Long lotId) {

		CarParkingLotDTO lotDTO = carParkingLotMapper.getCarParkingLotPageWx(lotId);
		if (lotDTO != null) {
			// 统计当前空闲的停车位数量
			int freeSpaces = carParkingSpaceMapper.selectCapacityBylotId(lotId);
			lotDTO.setFreeSpaces(freeSpaces);

			// 获取当前时间
			LocalDateTime currentTime = LocalDateTime.now();
			// 查询对应路段的收费规则
			CarParkingLotDTO feeRate = carFeeRuleMapper.selectRate(lotId, currentTime);
			// 如果找到了对应的收费规则，则设置费率，否则设置为6
			if (feeRate != null) {
				lotDTO.setFeeRate(feeRate.getFeeRate());
				lotDTO.setStartTime(feeRate.getStartTime());
				lotDTO.setEndTime(feeRate.getEndTime());
			} else {
				lotDTO.setFeeRate(BigDecimal.valueOf(6));
			}
			carParkingSpaceMapper.updateStatus(lotDTO.getSpaceId(), 1);
		}
		return lotDTO;
	}
}