package cn.zmvision.ccm.bank;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class Application extends SpringBootServletInitializer implements
		WebApplicationInitializer, SchedulingConfigurer {
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(new Class[] { Application.class });
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskExecutor());
	}

	@Bean(destroyMethod = "shutdown")
	public Executor taskExecutor() {
		return Executors.newScheduledThreadPool(3);
	}
}