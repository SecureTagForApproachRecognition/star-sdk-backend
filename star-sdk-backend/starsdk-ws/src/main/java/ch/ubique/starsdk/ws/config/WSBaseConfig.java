package ch.ubique.starsdk.ws.config;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ch.ubique.starsdk.data.JDBCSTARDataServiceImpl;
import ch.ubique.starsdk.data.STARDataService;
import ch.ubique.starsdk.ws.controller.STARController;

@Configuration
@EnableScheduling
public abstract class WSBaseConfig implements SchedulingConfigurer, WebMvcConfigurer {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public abstract DataSource dataSource();

	public abstract Flyway flyway();

	public abstract String getDbType();

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

}
