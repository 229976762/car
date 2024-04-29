package com.pig4cloud.pig.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.admin.api.entity.SysUserBalanceEntity;

public interface SysUserBalanceService extends IService<SysUserBalanceEntity> {

    void updateByUserId(Integer balance);
}