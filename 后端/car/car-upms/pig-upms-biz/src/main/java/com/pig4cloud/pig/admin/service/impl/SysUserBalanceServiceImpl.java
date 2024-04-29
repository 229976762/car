package com.pig4cloud.pig.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.SysUserBalanceEntity;
import com.pig4cloud.pig.admin.mapper.SysUserBalanceMapper;
import com.pig4cloud.pig.admin.service.SysUserBalanceService;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
/**
 * ${tableComment}
 *
 * @author pig
 * @date 2024-04-23 11:39:34
 */
@Service
@AllArgsConstructor
public class SysUserBalanceServiceImpl extends ServiceImpl<SysUserBalanceMapper, SysUserBalanceEntity> implements SysUserBalanceService {

	private final SysUserBalanceMapper sysUserBalanceMapper;

	@Override
	public void updateByUserId(Integer balance) {
		sysUserBalanceMapper.updateByUserId(balance, SecurityUtils.getUser().getId());
	}
}