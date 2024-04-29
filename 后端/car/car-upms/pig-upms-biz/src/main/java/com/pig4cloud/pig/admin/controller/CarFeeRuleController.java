package com.pig4cloud.pig.admin.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.CarFeeRuleEntity;
import com.pig4cloud.pig.admin.api.entity.CarFeeRuleEntity;
import com.pig4cloud.pig.admin.service.CarFeeRuleService;
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
 * 收费规则
 *
 * @author pig
 * @date 2024-04-11 17:23:30
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/carFeeRule" )
@Tag(description = "carFeeRule" , name = "收费规则管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CarFeeRuleController {

    private final CarFeeRuleService carFeeRuleService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param carFeeRule 收费规则
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('carFeeRule_carFeeRule_view')" )
    public R getCarFeeRulePage(@ParameterObject Page page, @ParameterObject CarFeeRuleEntity carFeeRule) {
        LambdaQueryWrapper<CarFeeRuleEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(Objects.nonNull(carFeeRule.getLotId()) && carFeeRule.getLotId() != 0, CarFeeRuleEntity::getLotId,carFeeRule.getLotId())
				.and(Objects.nonNull(carFeeRule.getStatus()),vm -> vm.eq(CarFeeRuleEntity::getStatus,carFeeRule.getStatus()));

		return R.ok(carFeeRuleService.page(page, wrapper));
    }


    /**
     * 通过id查询收费规则
     * @param ruleId id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{ruleId}" )
    @PreAuthorize("@pms.hasPermission('carFeeRule_carFeeRule_view')" )
    public R getById(@PathVariable("ruleId" ) Long ruleId) {
        return R.ok(carFeeRuleService.getById(ruleId));
    }

    /**
     * 新增收费规则
     * @param carFeeRule 收费规则
     * @return R
     */
    @Operation(summary = "新增收费规则" , description = "新增收费规则" )
    @SysLog("新增收费规则" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('carFeeRule_carFeeRule_add')" )
    public R save(@RequestBody CarFeeRuleEntity carFeeRule) {
        return R.ok(carFeeRuleService.save(carFeeRule));
    }

    /**
     * 修改收费规则
     * @param carFeeRule 收费规则
     * @return R
     */
    @Operation(summary = "修改收费规则" , description = "修改收费规则" )
    @SysLog("修改收费规则" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('carFeeRule_carFeeRule_edit')" )
    public R updateById(@RequestBody CarFeeRuleEntity carFeeRule) {
        return R.ok(carFeeRuleService.updateById(carFeeRule));
    }

    /**
     * 通过id删除收费规则
     * @param ids ruleId列表
     * @return R
     */
    @Operation(summary = "通过id删除收费规则" , description = "通过id删除收费规则" )
    @SysLog("通过id删除收费规则" )
    @DeleteMapping
    @PreAuthorize("@pms.hasPermission('carFeeRule_carFeeRule_del')" )
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(carFeeRuleService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param carFeeRule 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('carFeeRule_carFeeRule_export')" )
    public List<CarFeeRuleEntity> export(CarFeeRuleEntity carFeeRule,Long[] ids) {
        return carFeeRuleService.list(Wrappers.lambdaQuery(carFeeRule).in(ArrayUtil.isNotEmpty(ids), CarFeeRuleEntity::getRuleId, ids));
    }
}