package com.pig4cloud.pig.admin.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 收费规则
 *
 * @author pig
 * @date 2024-04-11 17:23:30
 */
@Data
@TableName("car_fee_rule")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "收费规则")
public class CarFeeRuleEntity extends Model<CarFeeRuleEntity> {


	/**
	* 规则ID
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="规则ID")
    private Long ruleId;

	/**
	* 路段ID
	*/
    @Schema(description="路段ID")
    private Long lotId;

	/**
	* 起始时间
	*/
    @Schema(description="起始时间")
    private LocalDateTime startTime;

	/**
	* 结束时间
	*/
    @Schema(description="结束时间")
    private LocalDateTime endTime;

	/**
	* 费率
	*/
    @Schema(description="费率")
    private BigDecimal feeRate;

	/**
	* 记录状态（正常、废弃）
	*/
    @Schema(description="记录状态（正常、废弃）")
    private Integer status;
}