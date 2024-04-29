package com.pig4cloud.pig.admin.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.CarParkingSpaceEntity;
import com.pig4cloud.pig.admin.api.entity.CarParkingSpaceEntity;
import com.pig4cloud.pig.admin.service.CarParkingSpaceService;
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
 * 停车位表
 *
 * @author pig
 * @date 2024-04-11 17:30:49
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/carParkingSpace" )
@Tag(description = "carParkingSpace" , name = "停车位表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CarParkingSpaceController {

    private final CarParkingSpaceService carParkingSpaceService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param carParkingSpace 停车位表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('carParkingSpace_carParkingSpace_view')" )
    public R getCarParkingSpacePage(@ParameterObject Page page, @ParameterObject CarParkingSpaceEntity carParkingSpace) {
        LambdaQueryWrapper<CarParkingSpaceEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(Objects.nonNull(carParkingSpace.getLotId()) && carParkingSpace.getLotId() != 0, CarParkingSpaceEntity::getLotId,carParkingSpace.getLotId())
				.and(Objects.nonNull(carParkingSpace.getStatus()),vm -> vm.eq(CarParkingSpaceEntity::getStatus,carParkingSpace.getStatus()))
				.and(Objects.nonNull(carParkingSpace.getSpaceNumber()) && !carParkingSpace.getSpaceNumber().equals(""),vm -> vm.eq(CarParkingSpaceEntity::getSpaceNumber,carParkingSpace.getSpaceNumber()));

		return R.ok(carParkingSpaceService.page(page, wrapper));
    }


    /**
     * 通过id查询停车位表
     * @param spaceId id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{spaceId}" )
    @PreAuthorize("@pms.hasPermission('carParkingSpace_carParkingSpace_view')" )
    public R getById(@PathVariable("spaceId" ) Long spaceId) {
        return R.ok(carParkingSpaceService.getById(spaceId));
    }

    /**
     * 新增停车位表
     * @param carParkingSpace 停车位表
     * @return R
     */
    @Operation(summary = "新增停车位表" , description = "新增停车位表" )
    @SysLog("新增停车位表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('carParkingSpace_carParkingSpace_add')" )
    public R save(@RequestBody CarParkingSpaceEntity carParkingSpace) {
        return R.ok(carParkingSpaceService.save(carParkingSpace));
    }

    /**
     * 修改停车位表
     * @param carParkingSpace 停车位表
     * @return R
     */
    @Operation(summary = "修改停车位表" , description = "修改停车位表" )
    @SysLog("修改停车位表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('carParkingSpace_carParkingSpace_edit')" )
    public R updateById(@RequestBody CarParkingSpaceEntity carParkingSpace) {
        return R.ok(carParkingSpaceService.updateById(carParkingSpace));
    }

    /**
     * 通过id删除停车位表
     * @param ids spaceId列表
     * @return R
     */
    @Operation(summary = "通过id删除停车位表" , description = "通过id删除停车位表" )
    @SysLog("通过id删除停车位表" )
    @DeleteMapping
    @PreAuthorize("@pms.hasPermission('carParkingSpace_carParkingSpace_del')" )
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(carParkingSpaceService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param carParkingSpace 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('carParkingSpace_carParkingSpace_export')" )
    public List<CarParkingSpaceEntity> export(CarParkingSpaceEntity carParkingSpace,Long[] ids) {
        return carParkingSpaceService.list(Wrappers.lambdaQuery(carParkingSpace).in(ArrayUtil.isNotEmpty(ids), CarParkingSpaceEntity::getSpaceId, ids));
    }
}