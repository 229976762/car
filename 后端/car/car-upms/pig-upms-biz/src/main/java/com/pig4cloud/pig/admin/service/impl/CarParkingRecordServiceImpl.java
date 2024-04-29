package com.pig4cloud.pig.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.CarParkingRecordEntity;
import com.pig4cloud.pig.admin.mapper.CarParkingRecordMapper;
import com.pig4cloud.pig.admin.service.CarParkingRecordService;
import org.springframework.stereotype.Service;
/**
 * 停车记录表
 *
 * @author pig
 * @date 2024-04-11 17:29:16
 */
@Service
public class CarParkingRecordServiceImpl extends ServiceImpl<CarParkingRecordMapper, CarParkingRecordEntity> implements CarParkingRecordService {
}