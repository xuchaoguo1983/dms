package cn.zmvision.ccm.bank.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HouseKeepingTask {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Scheduled(cron = "0 0 1 * * ?")
	public void run() {
		logger.info("start house keeping...");
	}

	@Scheduled(fixedRate = 1000L * 300)
	public void heartbeat() {
		logger.info("I am alive.");
	}
}
