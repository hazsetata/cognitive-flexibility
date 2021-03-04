package fi.ttl.cognitive.cognitiveflexibilitytstest;

import fi.ttl.cognitive.cognitiveflexibilitytstest.config.DataSourceConfiguration;
import fi.ttl.cognitive.cognitiveflexibilitytstest.util.DisabledIfNoDocker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Testcontainers
@DisabledIfNoDocker
class AppStartsWithHerokuMySqlEnv {
	@SuppressWarnings("rawtypes")
	@Container
	static MariaDBContainer mariaDB = new MariaDBContainer("mariadb:10.3.6")
			.withDatabaseName("cognitive")
			.withUsername("cog")
			.withPassword("passme");

	@SuppressWarnings("unused")
	@DynamicPropertySource
	static void databaseProperties(DynamicPropertyRegistry registry) {
		String dbUrl = "mysql://" + mariaDB.getUsername() + ":" + mariaDB.getPassword() + "@" +
				mariaDB.getHost() + ":" + mariaDB.getMappedPort(3306) + "/" + mariaDB.getDatabaseName();
		registry.add(DataSourceConfiguration.ENV_HEROKU_MYSQL_DATABASE_URL, () -> dbUrl);
	}

	@Test
	void contextLoads() {
	}
}
