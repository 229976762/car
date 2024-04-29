package com.pig4cloud.pig.admin.task;


import com.pig4cloud.pig.admin.service.CarParkingSpaceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class SchedulingTask {

	private final CarParkingSpaceService carParkingSpaceService;

	// 这个cron表达式代表从0秒开始，每过3分钟执行一次。
	@Scheduled(cron = "0 0/3 * * * ? ")
	public void test() {
		log.info("检查订单有效期 3分钟");
		carParkingSpaceService.updateSpaceStatus();
	}
}