package fi.ttl.cognitive.cognitiveflexibilitytstest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DataSourceConfiguration {
    private static final String ENV_DATABASE_URL = "DATABASE_URL";
    private static final String ENV_HEROKU_MARIADB_DATABASE_URL = "JAWSDB_MARIA_URL";
    private static final String ENV_HEROKU_MYSQL_DATABASE_URL = "JAWSDB_URL";

    private static final Logger log = LoggerFactory.getLogger(DataSourceConfiguration.class);

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.mariadb.jdbc.Driver");

        String connectionUrl = getConnectionUrlFromEnvironment();
        if (!StringUtils.hasText(connectionUrl)) {
            // Connecting to local (test) database
            log.info("Database configuration URL not found in the environment, connecting to localhost.");

            dataSourceBuilder.url("jdbc:mariadb://localhost:3306/cognitivedb?zeroDateTimeBehavior=convertToNull");
            dataSourceBuilder.username("cognitiveuser");
            dataSourceBuilder.password("changeme");
        }
        else {
            try {
                URI jdbUri = new URI(connectionUrl);

                String username = jdbUri.getUserInfo().split(":")[0];
                String password = jdbUri.getUserInfo().split(":")[1];
                String jdbUrl = "jdbc:mariadb://" + jdbUri.getHost() + ":" + jdbUri.getPort() + jdbUri.getPath() + "?zeroDateTimeBehavior=convertToNull";

                dataSourceBuilder.url(jdbUrl);
                dataSourceBuilder.username(username);
                dataSourceBuilder.password(password);
            }
            catch (URISyntaxException e) {
                throw new IllegalArgumentException("Database URL has wrong format.", e);
            }
        }

        return dataSourceBuilder.build();
    }

    protected static String getConnectionUrlFromEnvironment() {
        String retValue = System.getenv(ENV_DATABASE_URL);

        if (!StringUtils.hasText(retValue)) {
            // Support for Heroku's MySQL compatible databases
            retValue = System.getenv(ENV_HEROKU_MARIADB_DATABASE_URL);

            if (!StringUtils.hasText(retValue)) {
                retValue = System.getenv(ENV_HEROKU_MYSQL_DATABASE_URL);
            }
        }

        return retValue;
    }
}
