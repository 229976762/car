package com.pig4cloud.pig.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.dto.CarOrderDTO;
import com.pig4cloud.pig.admin.api.entity.CarOrderEntity;
import com.pig4cloud.pig.admin.api.entity.CarParkingRecordEntity;
import com.pig4cloud.pig.admin.api.entity.SysUserBalanceEntity;
import com.pig4cloud.pig.admin.mapper.CarOrderMapper;
import com.pig4cloud.pig.admin.mapper.CarParkingRecordMapper;
import com.pig4cloud.pig.admin.mapper.CarParkingSpaceMapper;
import com.pig4cloud.pig.admin.mapper.SysUserBalanceMapper;
import com.pig4cloud.pig.admin.service.CarOrderService;
import com.pig4cloud.pig.admin.service.CarParkingRecordService;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

/**
 * 订单表
 *
 * @author pig
 * @date 2024-04-11 17:27:23
 */
@Service
@AllArgsConstructor
@Slf4j
public class CarOrderServiceImpl extends ServiceImpl<CarOrderMapper, CarOrderEntity> implements CarOrderService {

	private final CarOrderMapper carOrderMapper;

	private final CarParkingSpaceMapper carParkingSpaceMapper;

	private final SysUserBalanceMapper userBalanceMapper;

	private final CarParkingRecordMapper carParkingRecordMapper;

	@Override
	public List<CarOrderDTO> selectByUserId(Long userid) {
		return carOrderMapper.selectByUserId(userid);
	}

	@Override
	public Boolean saveWxOrder(CarOrderDTO carOrder) {

		LambdaQueryWrapper<SysUserBalanceEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SysUserBalanceEntity::getUserId, SecurityUtils.getUser().getId());
		SysUserBalanceEntity one = userBalanceMapper.selectOne(wrapper);
		if(one.getBalance() - carOrder.getFee().intValueExact() < 0){
			carParkingSpaceMapper.updateStatus(carOrder.getSpaceId(),0);
			return false;
		}

		one.setBalance(one.getBalance() - carOrder.getFee().intValueExact());
		carOrder.setOrderTime(LocalDateTime.now());
		CarOrderEntity order = new CarOrderEntity();
		BeanUtils.copyProperties(carOrder, order);
		order.setUserId(SecurityUtils.getUser().getId());

		CarParkingRecordEntity record = new CarParkingRecordEntity();
		record.setStartTime(carOrder.getPaymentTime());
		record.setEndTime(carOrder.getPaymentTime().plus(Integer.valueOf(carOrder.getUseTime()), ChronoUnit.HOURS));
		record.setStatus(1);

		carParkingRecordMapper.insert(record);
		carOrderMapper.insert(order);
		carParkingSpaceMapper.updateStatus(carOrder.getSpaceId(),2);
		userBalanceMapper.updateById(one);

		return true;
	}
}