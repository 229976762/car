package com.pig4cloud.pig.admin.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 停车路段表
 *
 * @author pig
 * @date 2024-04-11 16:43:40
 */
@Data
@TableName("car_parking_lot")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "停车路段表")
@AllArgsConstructor
@NoArgsConstructor
public class CarParkingLotEntity extends Model<CarParkingLotEntity> {


	/**
	* 路段ID
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="路段ID")
    private Long lotId;

	/**
	* 路段名称
	*/
    @Schema(description="路段名称")
    private String lotName;

	/**
	* 路段x位置
	*/
    @Schema(description="路段x位置")
    private Double x;

	/**
	* 路段Y位置
	*/
    @Schema(description="路段Y位置")
    private Double y;

	/**
	* 路段容量
	*/
    @Schema(description="路段容量")
    private Integer capacity;

	/**
	* 状态（0正常 1停用）
	*/
    @Schema(description="状态（0正常 1停用）")
    private Integer state;
}