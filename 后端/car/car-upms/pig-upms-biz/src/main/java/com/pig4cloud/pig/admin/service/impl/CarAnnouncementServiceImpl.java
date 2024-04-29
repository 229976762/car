package com.pig4cloud.pig.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.CarAnnouncementEntity;
import com.pig4cloud.pig.admin.mapper.CarAnnouncementMapper;
import com.pig4cloud.pig.admin.service.CarAnnouncementService;
import org.springframework.stereotype.Service;
/**
 * 公告表
 *
 * @author pig
 * @date 2024-04-11 14:54:40
 */
@Service
public class CarAnnouncementServiceImpl extends ServiceImpl<CarAnnouncementMapper, CarAnnouncementEntity> implements CarAnnouncementService {
}