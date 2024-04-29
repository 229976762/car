package com.pig4cloud.pig.admin.api.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 停车路段表
 *
 * @author pig
 * @date 2024-04-11 16:43:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarParkingLotDTO extends Model<CarParkingLotDTO> {


	/**
	* 路段ID
	*/
    private Long lotId;

	/**
	* 路段名称
	*/
    private String lotName;


	/**
	* 空闲容量
	*/
    private Integer freeSpaces;

	/**
	 * 车位ID
	 */
	private Long spaceId;

	/**
	 * 车位编号
	 */
	private String spaceNumber;

	/**
	 * 起始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private LocalDateTime startTime;

	/**
	 * 结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private LocalDateTime endTime;

	/**
	 * 费率
	 */
	private BigDecimal feeRate;

	/**
	* 状态（0正常 1停用）
	*/
    private Integer state;
}