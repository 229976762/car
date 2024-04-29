package com.pig4cloud.pig.admin.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.CarAnnouncementEntity;
import com.pig4cloud.pig.admin.api.entity.CarParkingLotEntity;
import com.pig4cloud.pig.admin.service.CarAnnouncementService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import org.springframework.security.access.prepost.PreAuthorize;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 公告表
 *
 * @author pig
 * @date 2024-04-11 14:54:40
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/carAnnouncement" )
@Tag(description = "carAnnouncement" , name = "公告表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CarAnnouncementController {

    private final CarAnnouncementService carAnnouncementService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param carAnnouncement 公告表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('carAnnouncement_carAnnouncement_view')" )
    public R getCarAnnouncementPage(@ParameterObject Page page, @ParameterObject CarAnnouncementEntity carAnnouncement) {
        LambdaQueryWrapper<CarAnnouncementEntity> wrapper = Wrappers.lambdaQuery();

		wrapper.like(CarAnnouncementEntity::getTitle, "%" + carAnnouncement.getTitle() + "%")
				.and(vm -> vm.like(CarAnnouncementEntity::getContent, "%" + carAnnouncement.getContent() + "%"));
        return R.ok(carAnnouncementService.page(page, wrapper));
    }


    /**
     * 通过id查询公告表
     * @param announcementId id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{announcementId}" )
    @PreAuthorize("@pms.hasPermission('carAnnouncement_carAnnouncement_view')" )
    public R getById(@PathVariable("announcementId" ) Long announcementId) {
        return R.ok(carAnnouncementService.getById(announcementId));
    }

    /**
     * 新增公告表
     * @param carAnnouncement 公告表
     * @return R
     */
    @Operation(summary = "新增公告表" , description = "新增公告表" )
    @SysLog("新增公告表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('carAnnouncement_carAnnouncement_add')" )
    public R save(@RequestBody CarAnnouncementEntity carAnnouncement) {
        return R.ok(carAnnouncementService.save(carAnnouncement));
    }

    /**
     * 修改公告表
     * @param carAnnouncement 公告表
     * @return R
     */
    @Operation(summary = "修改公告表" , description = "修改公告表" )
    @SysLog("修改公告表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('carAnnouncement_carAnnouncement_edit')" )
    public R updateById(@RequestBody CarAnnouncementEntity carAnnouncement) {
        return R.ok(carAnnouncementService.updateById(carAnnouncement));
    }

    /**
     * 通过id删除公告表
     * @param ids announcementId列表
     * @return R
     */
    @Operation(summary = "通过id删除公告表" , description = "通过id删除公告表" )
    @SysLog("通过id删除公告表" )
    @DeleteMapping
    @PreAuthorize("@pms.hasPermission('carAnnouncement_carAnnouncement_del')" )
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(carAnnouncementService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param carAnnouncement 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('carAnnouncement_carAnnouncement_export')" )
    public List<CarAnnouncementEntity> export(CarAnnouncementEntity carAnnouncement,Long[] ids) {
        return carAnnouncementService.list(Wrappers.lambdaQuery(carAnnouncement).in(ArrayUtil.isNotEmpty(ids), CarAnnouncementEntity::getAnnouncementId, ids));
    }
}