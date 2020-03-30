package ch.ubique.starsdk.ws.config;

import java.util.List;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ch.ubique.starsdk.data.JDBCSTARDataServiceImpl;
import ch.ubique.starsdk.data.STARDataService;
import ch.ubique.starsdk.data.output.OutputHandler;
import ch.ubique.starsdk.ws.controller.STARController;
import ch.ubique.starsdk.ws.output.Outputter;

@Configuration
@EnableScheduling
public abstract class WSBaseConfig implements SchedulingConfigurer, WebMvcConfigurer {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public abstract DataSource dataSource();

	public abstract Flyway flyway();

	public abstract String getDbType();

	public abstract List<OutputHandler> outputHandlerList();

	@Value("${ws.app.source}")
	String appSource;

	@Bean
	public STARController starSDKController() {
		return new STARController(starSDKDataService(), appSource);
	}

	@Bean
	public STARDataService starSDKDataService() {
		return new JDBCSTARDataServiceImpl(getDbType(), dataSource());
	}

	@Bean
	public Outputter outputter() {
		return new Outputter(starSDKDataService(), outputHandlerList());
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		// output scheduled task
		taskRegistrar.addFixedDelayTask(new IntervalTask(new Runnable() {
			@Override
			public void run() {
				outputter().outputCurrentDay();
			}
		}, 60 * 1000L, 5 * 1000L));
	}
}
