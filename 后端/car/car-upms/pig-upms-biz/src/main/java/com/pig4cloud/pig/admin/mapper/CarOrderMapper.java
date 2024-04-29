package com.pig4cloud.pig.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pig.admin.api.dto.CarOrderDTO;
import com.pig4cloud.pig.admin.api.entity.CarOrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CarOrderMapper extends BaseMapper<CarOrderEntity> {


	List<CarOrderDTO> selectByUserId(@Param("userId") Long userid);
}