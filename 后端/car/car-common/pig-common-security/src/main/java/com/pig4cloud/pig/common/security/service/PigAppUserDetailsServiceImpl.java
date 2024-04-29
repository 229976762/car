/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pig4cloud.pig.common.security.service;

import com.alibaba.fastjson2.JSON;
import com.pig4cloud.pig.admin.api.dto.UserDTO;
import com.pig4cloud.pig.admin.api.dto.UserInfo;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.admin.api.entity.SysUserBalanceEntity;
import com.pig4cloud.pig.admin.api.feign.RemoteUserService;
import com.pig4cloud.pig.common.core.constant.CacheConstants;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.HttpServer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 用户详细信息
 *
 * @author lengleng hccake
 */
@Slf4j
@RequiredArgsConstructor
public class PigAppUserDetailsServiceImpl implements PigUserDetailsService {

	private final RemoteUserService remoteUserService;

	private final CacheManager cacheManager;

	/**
	 * 手机号登录
	 * @param openid 手机号
	 * @return
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String openid) {
		//查询系统用户
		Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
		if (cache != null && cache.get(openid) != null) {
			return (PigUser) cache.get(openid).get();
		}
		//数据库用户
		UserDTO userDTO = new UserDTO();
		userDTO.setWxOpenid(openid);
		R<UserInfo> result = remoteUserService.info(userDTO, SecurityConstants.FROM_IN);
		if(Objects.isNull(result.getData())) {
			//获取微信code
			Map<String, String> params = new HashMap<>();
			params.put("appid", "wx75776b1b623a685b");
			params.put("secret", "3324a7c420d9cc94e191fbc8ef83228f");
			params.put("js_code", openid);
			params.put("grant_type", "authorization_code");
			// 用户验证
			String res = HttpServer.doGet("https://api.weixin.qq.com/sns/jscode2session", params);
			Map<String, String> weChatSession = JSON.parseObject(res, Map.class);
			openid = weChatSession.get("openid");

			//查询系统用户
			if (cache != null && cache.get(openid) != null) {
				return (PigUser) cache.get(openid).get();
			}

			//如果数据库也没有
			if (Objects.isNull(result.getData())) {
				//插入用户信息
				UserDTO newUser = new UserDTO(Arrays.asList(2L), 1782313397677895682L, Arrays.asList(1782313715555807234L), null);
				newUser.setUsername("小程序用户");
				newUser.setPassword("888888");
				newUser.setCreateTime(LocalDateTime.now());
				newUser.setCreateBy("admin");
				newUser.setUpdateBy("admin");
				newUser.setDelFlag("0");
				newUser.setLockFlag("0");
				newUser.setWxOpenid(openid);
				newUser.setNickname("小程序用户");
				newUser.setName("小程序用户");

				remoteUserService.saveUser(newUser, SecurityConstants.FROM_IN);
				result = remoteUserService.info(newUser, SecurityConstants.FROM_IN);
			}
		}

		UserDetails userDetails = getUserDetails(result);
		if (cache != null) {
			cache.put(openid, userDetails);
		}
		return userDetails;
	}

	/**
	 * check-token 使用
	 * @param pigUser user
	 * @return
	 */
	@Override
	public UserDetails loadUserByUser(PigUser pigUser) {
		return this.loadUserByUsername(pigUser.getWxOpenid());
	}

	/**
	 * 是否支持此客户端校验
	 * @param clientId 目标客户端
	 * @return true/false
	 */
	@Override
	public boolean support(String clientId, String grantType) {
		return SecurityConstants.MOBILE.equals(grantType);
	}

}
