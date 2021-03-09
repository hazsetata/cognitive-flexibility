package fi.ttl.cognitive.cognitiveflexibilitytstest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DataSourceConfiguration {
    public static final String ENV_DATABASE_URL = "DATABASE_URL";
    public static final String ENV_HEROKU_MARIADB_DATABASE_URL = "JAWSDB_MARIA_URL";
    public static final String ENV_HEROKU_MYSQL_DATABASE_URL = "JAWSDB_URL";

    private static final Logger log = LoggerFactory.getLogger(DataSourceConfiguration.class);

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.mariadb.jdbc.Driver");

        String connectionUrl = getConnectionUrlFromEnvironment();
        if (StringUtils.hasText(connectionUrl)) {
            try {
                URI jdbUri = new URI(connectionUrl);

                String username = jdbUri.getUserInfo().split(":")[0];
                String password = jdbUri.getUserInfo().split(":")[1];
                String jdbcUrl = "jdbc:mariadb://" + jdbUri.getHost() + ":" + jdbUri.getPort() + jdbUri.getPath() + "?zeroDateTimeBehavior=convertToNull";

                log.debug("JDBC URL: " + jdbcUrl);

                dataSourceBuilder.url(jdbcUrl);
                dataSourceBuilder.username(username);
                dataSourceBuilder.password(password);
            }
            catch (URISyntaxException e) {
                throw new IllegalArgumentException("Database URL has wrong format.", e);
            }
        }
        else {
            throw new IllegalStateException("Database configuration URL not specified.");
        }

        return dataSourceBuilder.build();
    }

    protected String getConnectionUrlFromEnvironment() {
        return getFirstDefined(ENV_DATABASE_URL, ENV_HEROKU_MARIADB_DATABASE_URL, ENV_HEROKU_MYSQL_DATABASE_URL);
    }

    protected String getFirstDefined(String... environmentVariableNames) {
        for (String envVarName : environmentVariableNames) {
            log.info("Checking database configuration URL from environment variable: " + envVarName);
            String retValue = environment.getProperty(envVarName);

            if (StringUtils.hasText(retValue)) {
                log.info("Found database configuration URL in environment variable: " + envVarName);

                return retValue;
            }
        }

        return null;
    }
}
