package com.pig4cloud.pig.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pig.admin.api.entity.CarParkingSpaceEntity;
import org.apache.ibatis.annotations.Mapper;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CarParkingSpaceMapper extends BaseMapper<CarParkingSpaceEntity> {


    int selectCapacityBylotId(Long lotId);

	void updateStatus(Long spaceId, Integer status);


	void updateBatchById(LocalDateTime time);
}