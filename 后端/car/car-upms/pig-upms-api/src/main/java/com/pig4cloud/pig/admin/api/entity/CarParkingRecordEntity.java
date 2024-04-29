package com.pig4cloud.pig.admin.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 停车记录表
 *
 * @author pig
 * @date 2024-04-11 17:29:16
 */
@Data
@TableName("car_parking_record")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "停车记录表")
public class CarParkingRecordEntity extends Model<CarParkingRecordEntity> {


	/**
	* 记录ID
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="记录ID")
    private Long recordId;

	/**
	* 用户ID
	*/
    @Schema(description="用户ID")
    private Long userId;

	/**
	* 车位ID
	*/
    @Schema(description="车位ID")
    private Long spaceId;

	/**
	* 停车开始时间
	*/
    @Schema(description="停车开始时间")
    private LocalDateTime startTime;

	/**
	* 停车结束时间
	*/
    @Schema(description="停车结束时间")
    private LocalDateTime endTime;

	/**
	* 记录状态（停车中、已完成）
	*/
    @Schema(description="记录状态（停车中、已完成）")
    private Integer status;
}