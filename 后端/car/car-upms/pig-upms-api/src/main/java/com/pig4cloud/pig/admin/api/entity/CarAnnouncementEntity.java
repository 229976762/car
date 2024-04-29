package com.pig4cloud.pig.admin.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 公告表
 *
 * @author pig
 * @date 2024-04-11 14:54:40
 */
@Data
@TableName("car_announcement")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "公告表")
public class CarAnnouncementEntity extends Model<CarAnnouncementEntity> {


	/**
	* 公告ID
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="公告ID")
    private Long announcementId;

	/**
	* 管理员Id
	*/
    @Schema(description="管理员Id")
    private Long userId;

	/**
	* 公告标题
	*/
    @Schema(description="公告标题")
    private String title;

	/**
	* 公告内容
	*/
    @Schema(description="公告内容")
    private String content;

	/**
	* 发布时间
	*/
    @Schema(description="发布时间")
    private LocalDateTime publishTime;
}