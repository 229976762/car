package com.pig4cloud.pig.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pig4cloud.pig.admin.api.entity.SysUserBalanceEntity;
import com.pig4cloud.pig.admin.service.SysUserBalanceService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.annotation.Inner;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * ${tableComment}
 *
 * @author pig
 * @date 2024-04-23 11:39:34
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sysUserBalance" )
@Tag(description = "sysUserBalance" , name = "${tableComment}管理" )
public class SysUserBalanceController {

    private final SysUserBalanceService sysUserBalanceService;

    /**
     * 通过id查询${tableComment}
     * @param id id
     * @return R
     */
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Long id) {
		LambdaQueryWrapper<SysUserBalanceEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(SysUserBalanceEntity::getUserId,id);

		return R.ok(sysUserBalanceService.getOne(wrapper));
    }

    /**
     * 新增${tableComment}
     * @param sysUserBalance ${tableComment}
     * @return R
     */
	@Inner
    @PostMapping
    public R save(@RequestBody SysUserBalanceEntity sysUserBalance) {
        return R.ok(sysUserBalanceService.save(sysUserBalance));
    }

    /**
     * 修改${tableComment}
     * @return R
     */
    @Operation(summary = "修改${tableComment}" , description = "修改${tableComment}" )
    @PutMapping
    public void updateById(@RequestBody Integer balance) {
		sysUserBalanceService.updateByUserId(balance);
    }


}