package com.pig4cloud.pig.admin.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.CarParkingRecordEntity;
import com.pig4cloud.pig.admin.api.entity.CarParkingRecordEntity;
import com.pig4cloud.pig.admin.service.CarParkingRecordService;
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
 * 停车记录表
 *
 * @author pig
 * @date 2024-04-11 17:29:16
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/carParkingRecord" )
@Tag(description = "carParkingRecord" , name = "停车记录表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CarParkingRecordController {

    private final CarParkingRecordService carParkingRecordService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param carParkingRecord 停车记录表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('carParkingRecord_carParkingRecord_view')" )
    public R getCarParkingRecordPage(@ParameterObject Page page, @ParameterObject CarParkingRecordEntity carParkingRecord) {
        LambdaQueryWrapper<CarParkingRecordEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(Objects.nonNull(carParkingRecord.getUserId()) && carParkingRecord.getUserId() != 0, CarParkingRecordEntity::getUserId,carParkingRecord.getUserId())
				.and(Objects.nonNull(carParkingRecord.getStatus()),vm -> vm.eq(CarParkingRecordEntity::getStatus,carParkingRecord.getStatus()))
				.and(Objects.nonNull(carParkingRecord.getSpaceId()) && carParkingRecord.getSpaceId() != 0,vm -> vm.eq(CarParkingRecordEntity::getSpaceId,carParkingRecord.getSpaceId()));

		return R.ok(carParkingRecordService.page(page, wrapper));
    }


    /**
     * 通过id查询停车记录表
     * @param recordId id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{recordId}" )
    @PreAuthorize("@pms.hasPermission('carParkingRecord_carParkingRecord_view')" )
    public R getById(@PathVariable("recordId" ) Long recordId) {
        return R.ok(carParkingRecordService.getById(recordId));
    }

    /**
     * 新增停车记录表
     * @param carParkingRecord 停车记录表
     * @return R
     */
    @Operation(summary = "新增停车记录表" , description = "新增停车记录表" )
    @SysLog("新增停车记录表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('carParkingRecord_carParkingRecord_add')" )
    public R save(@RequestBody CarParkingRecordEntity carParkingRecord) {
        return R.ok(carParkingRecordService.save(carParkingRecord));
    }

    /**
     * 修改停车记录表
     * @param carParkingRecord 停车记录表
     * @return R
     */
    @Operation(summary = "修改停车记录表" , description = "修改停车记录表" )
    @SysLog("修改停车记录表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('carParkingRecord_carParkingRecord_edit')" )
    public R updateById(@RequestBody CarParkingRecordEntity carParkingRecord) {
        return R.ok(carParkingRecordService.updateById(carParkingRecord));
    }

    /**
     * 通过id删除停车记录表
     * @param ids recordId列表
     * @return R
     */
    @Operation(summary = "通过id删除停车记录表" , description = "通过id删除停车记录表" )
    @SysLog("通过id删除停车记录表" )
    @DeleteMapping
    @PreAuthorize("@pms.hasPermission('carParkingRecord_carParkingRecord_del')" )
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(carParkingRecordService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param carParkingRecord 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('carParkingRecord_carParkingRecord_export')" )
    public List<CarParkingRecordEntity> export(CarParkingRecordEntity carParkingRecord,Long[] ids) {
        return carParkingRecordService.list(Wrappers.lambdaQuery(carParkingRecord).in(ArrayUtil.isNotEmpty(ids), CarParkingRecordEntity::getRecordId, ids));
    }
}