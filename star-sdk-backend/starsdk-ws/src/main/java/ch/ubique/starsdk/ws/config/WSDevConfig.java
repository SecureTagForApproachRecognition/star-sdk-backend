package ch.ubique.starsdk.ws.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import ch.ubique.starsdk.data.output.LocalFSOutputHandler;
import ch.ubique.starsdk.data.output.OutputHandler;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@Configuration
@Profile("dev")
public class WSDevConfig extends WSBaseConfig {

	@Bean
	@Override
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).build();
	}

	@Bean
	@Override
	public Flyway flyway() {
		Flyway flyWay = Flyway.configure()
				.dataSource(dataSource())
				.locations("classpath:/db/migration/hsqldb")
				.load();
		flyWay.migrate();
		return flyWay;
	}

	@Override
	public String getDbType() {
		return "hsqldb";
	}

	@Bean
	@Override
	public List<OutputHandler> outputHandlerList() {
		LocalFSOutputHandler local = new LocalFSOutputHandler("/tmp/out");
		return Arrays.asList(local);
	}
}
