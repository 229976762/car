package com.pig4cloud.pig.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pig.admin.api.entity.SysUserBalanceEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserBalanceMapper extends BaseMapper<SysUserBalanceEntity> {


    void updateByUserId(Integer balance, Long id);
}