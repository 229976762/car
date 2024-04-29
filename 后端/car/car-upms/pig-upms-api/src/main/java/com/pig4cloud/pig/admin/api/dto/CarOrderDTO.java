package com.pig4cloud.pig.admin.api.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@AllArgsConstructor
@NoArgsConstructor
public class CarOrderDTO extends Model<CarOrderDTO> {


	/**
	* 订单ID
	*/
    private Long orderId;

	/**
	* 用户ID
	*/
    private Long userId;

	/**
	* 车位ID
	*/
    private Long spaceId;

	/**
	 * 车位编号
	 */
	private String spaceNumber;


	/**
	 * 停车路段名
	 */
	private String lotName;

	/**
	* 下单时间
	*/
    private LocalDateTime orderTime;

	/**
	* 占用时间
	*/
    private String useTime;

	/**
	* 订单状态（0待支付、1已支付、2已取消）
	*/
    private Integer status;

	/**
	 * 车牌号
	 */
	private String plateNumber;

	/**
	* 支付时间
	*/
    private LocalDateTime paymentTime;

	/**
	* 停车费用
	*/
    private BigDecimal fee;
}