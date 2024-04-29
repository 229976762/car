package com.pig4cloud.pig.admin.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单表
 *
 * @author pig
 * @date 2024-04-11 17:27:23
 */
@Data
@TableName("car_order")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "订单表")
@AllArgsConstructor
@NoArgsConstructor
public class CarOrderEntity extends Model<CarOrderEntity> {


	/**
	* 订单ID
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="订单ID")
    private Long orderId;

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
	* 下单时间
	*/
    @Schema(description="下单时间")
    private LocalDateTime orderTime;

	/**
	* 占用时间
	*/
    @Schema(description="占用时间")
    private String useTime;

	/**
	* 订单状态（0待支付、1已支付、2已取消）
	*/
    @Schema(description="订单状态（0待支付、1已支付、2已取消）")
    private Integer status;

	/**
	* 支付时间
	*/
    @Schema(description="支付时间")
    private LocalDateTime paymentTime;

	/**
	* 停车费用
	*/
    @Schema(description="停车费用")
    private BigDecimal fee;

	/**
	 * 车牌号
	 */
	@Schema(description="车牌号")
	private String plateNumber;
}