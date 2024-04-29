package com.pig4cloud.pig.admin.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.dto.CarOrderDTO;
import com.pig4cloud.pig.admin.api.entity.CarOrderEntity;
import com.pig4cloud.pig.admin.service.CarOrderService;
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
 * 订单表
 *
 * @author pig
 * @date 2024-04-11 17:27:23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/carOrder" )
@Tag(description = "carOrder" , name = "订单表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CarOrderController {

    private final CarOrderService carOrderService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param carOrder 订单表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('carOrder_carOrder_view')" )
    public R getCarOrderPage(@ParameterObject Page page, @ParameterObject CarOrderEntity carOrder) {
        LambdaQueryWrapper<CarOrderEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(Objects.nonNull(carOrder.getUserId()) && carOrder.getUserId() != 0,CarOrderEntity::getUserId,carOrder.getUserId())
				.and(Objects.nonNull(carOrder.getStatus()),vm -> vm.eq(CarOrderEntity::getStatus,carOrder.getStatus()))
				.and(Objects.nonNull(carOrder.getSpaceId()) && carOrder.getSpaceId() != 0,vm -> vm.eq(CarOrderEntity::getSpaceId,carOrder.getSpaceId()));
        return R.ok(carOrderService.page(page, wrapper));
    }


    /**
     * 通过id查询订单表
     * @param orderId id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{orderId}" )
    @PreAuthorize("@pms.hasPermission('carOrder_carOrder_view')" )
    public R getById(@PathVariable("orderId" ) Long orderId) {
        return R.ok(carOrderService.getById(orderId));
    }


	/**
	 * 通过userid查询订单表
	 * @param userid id
	 * @return R
	 */
	@Operation(summary = "通过userid查询" , description = "通过userid查询" )
	@GetMapping("/wx/{userid}" )
	@PreAuthorize("@pms.hasPermission('carOrder_carOrder_view')")
	public R getWxById(@PathVariable("userid" ) Long userid) {
		List<CarOrderDTO> dto = carOrderService.selectByUserId(userid);
		return R.ok(dto);
	}



	/**
     * 新增订单表
     * @param carOrder 订单表
     * @return R
     */
    @Operation(summary = "新增订单表" , description = "新增订单表" )
    @SysLog("新增订单表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('carOrder_carOrder_add')" )
    public R save(@RequestBody CarOrderEntity carOrder) {
        return R.ok(carOrderService.save(carOrder));
    }

	/**
	 * 新增订单表
	 * @param carOrder 订单表
	 * @return R
	 */
	@Operation(summary = "新增订单表" , description = "新增订单表" )
	@SysLog("新增订单表" )
	@PostMapping("/wx")
	@PreAuthorize("@pms.hasPermission('carOrder_carOrder_add')" )
	public R saveWxOrder(@RequestBody CarOrderDTO carOrder) {
		Boolean flag = carOrderService.saveWxOrder(carOrder);
		if (!flag)
			return R.failed("余额不足");
		return R.ok();
	}

    /**
     * 修改订单表
     * @param carOrder 订单表
     * @return R
     */
    @Operation(summary = "修改订单表" , description = "修改订单表" )
    @SysLog("修改订单表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('carOrder_carOrder_edit')" )
    public R updateById(@RequestBody CarOrderEntity carOrder) {
        return R.ok(carOrderService.updateById(carOrder));
    }

    /**
     * 通过id删除订单表
     * @param ids orderId列表
     * @return R
     */
    @Operation(summary = "通过id删除订单表" , description = "通过id删除订单表" )
    @SysLog("通过id删除订单表" )
    @DeleteMapping
    @PreAuthorize("@pms.hasPermission('carOrder_carOrder_del')" )
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(carOrderService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param carOrder 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('carOrder_carOrder_export')" )
    public List<CarOrderEntity> export(CarOrderEntity carOrder,Long[] ids) {
        return carOrderService.list(Wrappers.lambdaQuery(carOrder).in(ArrayUtil.isNotEmpty(ids), CarOrderEntity::getOrderId, ids));
    }
}