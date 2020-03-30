package ch.ubique.starsdk.ws.config;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlobDirectory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ch.ubique.starsdk.data.output.AzureBlobOutputHandler;
import ch.ubique.starsdk.data.output.OutputHandler;

@Configuration
@Profile("prod")
public class WSProdConfig extends WSBaseConfig {

	@Value("${datasource.username}")
	String dataSourceUser;

	@Value("${datasource.password}")
	String dataSourcePassword;

	@Value("${datasource.url}")
	String dataSourceUrl;

	@Value("${datasource.driverClassName}")
	String dataSourceDriver;

	@Value("${datasource.failFast}")
	String dataSourceFailFast;

	@Value("${datasource.maximumPoolSize}")
	String dataSourceMaximumPoolSize;

	@Value("${datasource.maxLifetime}")
	String dataSourceMaxLifetime;

	@Value("${datasource.idleTimeout}")
	String dataSourceIdleTimeout;

	@Value("${datasource.connectionTimeout}")
	String dataSourceConnectionTimeout;

	@Value("${blobstorage.connectionString}")
	String storageConnectionString;
	@Value("${blobstorage.container}")
	String blobContainer;

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		HikariConfig config = new HikariConfig();
		Properties props = new Properties();
		props.put("url", dataSourceUrl);
		props.put("user", dataSourceUser);
		props.put("password", dataSourcePassword);
		config.setDataSourceProperties(props);
		config.setDataSourceClassName(dataSourceDriver);
		config.setMaximumPoolSize(Integer.parseInt(dataSourceMaximumPoolSize));
		config.setMaxLifetime(Integer.parseInt(dataSourceMaxLifetime));
		config.setIdleTimeout(Integer.parseInt(dataSourceIdleTimeout));
		config.setConnectionTimeout(Integer.parseInt(dataSourceConnectionTimeout));
		return new HikariDataSource(config);
	}

	@Bean
	@Override
	public Flyway flyway() {
		Flyway flyWay = Flyway.configure()
				.dataSource(dataSource())
				.locations("classpath:/db/migration/pgsql")
				.baselineOnMigrate(false)
				.validateOnMigrate(false)
				.load();
		flyWay.migrate();
		return flyWay;
	}

	@Override
	public String getDbType() {
		return "pgsql";
	}

	@Bean
	@Override
	public List<OutputHandler> outputHandlerList() {
		return Collections.singletonList(azureBlobOutputHandler());
	}
	@Bean
	public CloudBlobDirectory cloudBlobDirectory() {
		try {
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
			CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
			CloudBlobContainer container = blobClient.getContainerReference(blobContainer);

			// Create the container if it does not exist.
			container.createIfNotExists();
			CloudBlobDirectory directory = container.getDirectoryReference("");
			return directory;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Bean
	public OutputHandler azureBlobOutputHandler() {
		return new AzureBlobOutputHandler(cloudBlobDirectory());
	}
}
