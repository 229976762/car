package com.pig4cloud.pig.admin.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * ${tableComment}
 *
 * @author pig
 * @date 2024-04-23 11:39:34
 */
@Data
@TableName("sys_user_balance")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "余额表")
@AllArgsConstructor
@NoArgsConstructor
public class SysUserBalanceEntity extends Model<SysUserBalanceEntity> {

 
	/**
	* id
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="id")
    private Long id;
 
	/**
	* userId
	*/
    @Schema(description="userId")
    private Long userId;
 
	/**
	* balance
	*/
    @Schema(description="balance")
    private Integer balance;

	/**
	 * plateNumber
	 */
	@Schema(description="plateNumber")
	private String plateNumber;
}