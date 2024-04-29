package com.pig4cloud.pig.admin.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.dto.CarParkingLotDTO;
import com.pig4cloud.pig.admin.api.entity.CarParkingLotEntity;
import com.pig4cloud.pig.admin.service.CarParkingLotService;
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
 * 停车路段表
 *
 * @author pig
 * @date 2024-04-11 16:43:40
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/carParkingLot" )
@Tag(description = "carParkingLot" , name = "停车路段表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CarParkingLotController {

    private final CarParkingLotService carParkingLotService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param carParkingLot 停车路段表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('carParkingLot_carParkingLot_view')" )
    public R getCarParkingLotPage(@ParameterObject Page page, @ParameterObject CarParkingLotEntity carParkingLot) {
        LambdaQueryWrapper<CarParkingLotEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.like(CarParkingLotEntity::getLotName, "%" + carParkingLot.getLotName() + "%");

        return R.ok(carParkingLotService.page(page, wrapper));
    }

	/**
	 * 分页查询
	 * @param lotId 停车路段id
	 * @return
	 */
	@Operation(summary = "分页查询" , description = "分页查询" )
	@GetMapping("/wx/getLost" )
	@PreAuthorize("@pms.hasPermission('carParkingLot_carParkingLot_view')" )
	public R getCarParkingLotPageWx(@RequestParam("lotId") Long lotId) {

		CarParkingLotDTO parkingLotDTO = carParkingLotService.getCarParkingLotPageWx(lotId);
		return R.ok(parkingLotDTO);
	}

    /**
     * 通过id查询停车路段表
     * @param lotId id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{lotId}" )
    @PreAuthorize("@pms.hasPermission('carParkingLot_carParkingLot_view')" )
    public R getById(@PathVariable("lotId" ) Long lotId) {
        return R.ok(carParkingLotService.getById(lotId));
    }

    /**
     * 新增停车路段表
     * @param carParkingLot 停车路段表
     * @return R
     */
    @Operation(summary = "新增停车路段表" , description = "新增停车路段表" )
    @SysLog("新增停车路段表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('carParkingLot_carParkingLot_add')" )
    public R save(@RequestBody CarParkingLotEntity carParkingLot) {
        return R.ok(carParkingLotService.save(carParkingLot));
    }

    /**
     * 修改停车路段表
     * @param carParkingLot 停车路段表
     * @return R
     */
    @Operation(summary = "修改停车路段表" , description = "修改停车路段表" )
    @SysLog("修改停车路段表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('carParkingLot_carParkingLot_edit')" )
    public R updateById(@RequestBody CarParkingLotEntity carParkingLot) {
        return R.ok(carParkingLotService.updateById(carParkingLot));
    }

    /**
     * 通过id删除停车路段表
     * @param ids lotId列表
     * @return R
     */
    @Operation(summary = "通过id删除停车路段表" , description = "通过id删除停车路段表" )
    @SysLog("通过id删除停车路段表" )
    @DeleteMapping
    @PreAuthorize("@pms.hasPermission('carParkingLot_carParkingLot_del')" )
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(carParkingLotService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param carParkingLot 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('carParkingLot_carParkingLot_export')" )
    public List<CarParkingLotEntity> export(CarParkingLotEntity carParkingLot,Long[] ids) {
        return carParkingLotService.list(Wrappers.lambdaQuery(carParkingLot).in(ArrayUtil.isNotEmpty(ids), CarParkingLotEntity::getLotId, ids));
    }
}