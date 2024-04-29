package com.pig4cloud.pig.admin.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 停车位表
 *
 * @author pig
 * @date 2024-04-11 17:30:49
 */
@Data
@TableName("car_parking_space")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "停车位表")
public class CarParkingSpaceEntity extends Model<CarParkingSpaceEntity> {


	/**
	* 车位ID
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="车位ID")
    private Long spaceId;

	/**
	* 所属路段ID
	*/
    @Schema(description="所属路段ID")
    private Long lotId;

	/**
	* 车位编号
	*/
    @Schema(description="车位编号")
    private String spaceNumber;

	/**
	 * 车位状态（0空闲、1已预订、2已停车）
	 */
	@Schema(description="更新时间")
	private LocalDateTime updateTime;

	/**
	* 车位状态（0空闲、1已预订、2已停车）
	*/
    @Schema(description="车位状态（0空闲、1已预订、2已停车）")
    private Integer status;
}