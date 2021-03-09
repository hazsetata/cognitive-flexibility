package fi.ttl.cognitive.cognitiveflexibilitytstest;

import fi.ttl.cognitive.cognitiveflexibilitytstest.config.DataSourceConfiguration;
import fi.ttl.cognitive.cognitiveflexibilitytstest.util.DisabledIfNoDocker;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = {
				"ttl.cognitive.client.clientId=testClientId"
		}
)
@Testcontainers
@DisabledIfNoDocker
class AppLoginAndCallbackTest {
	@SuppressWarnings("rawtypes")
	@Container
	static MariaDBContainer mariaDB = new MariaDBContainer("mariadb:10.3.6")
			.withDatabaseName("cognitive")
			.withUsername("cog")
			.withPassword("passme");


	@LocalServerPort
	private int port;

	private static MockWebServer mockServer;

	@SuppressWarnings("unused")
	@DynamicPropertySource
	static void databaseProperties(DynamicPropertyRegistry registry) throws IOException {
		String dbUrl = "mysql://" + mariaDB.getUsername() + ":" + mariaDB.getPassword() + "@" +
				mariaDB.getHost() + ":" + mariaDB.getMappedPort(3306) + "/" + mariaDB.getDatabaseName();
		registry.add(DataSourceConfiguration.ENV_HEROKU_MARIADB_DATABASE_URL, () -> dbUrl);

		mockServer = new MockWebServer();
		mockServer.start();
		HttpUrl callbackUrl = mockServer.url("/callbackPath");
		registry.add("ttl.cognitive.callback.callbackUrl", callbackUrl::toString);
	}

	@AfterAll
	public static void stopMockCallbackServer() throws IOException {
		mockServer.shutdown();
	}

	@Test
	void loginAndCallbackWorks() throws InterruptedException {
		SessionFilter sessionFilter = new SessionFilter();
		Response response =
				given()
						.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), sessionFilter)
						.baseUri("http://localhost")
						.port(port)
						.queryParam("callbackToken", "testToken1234")
						.queryParam("clientId", "testClientId")
				.when()
						.redirects().follow(false)
						.get("app/auth/testuser");

		assertEquals(302, response.statusCode());
		assertNotNull(response.getHeader("Location"));
		assertTrue(response.getHeader("Location").endsWith("/game.html"));
		assertTrue(response.getCookies().containsKey("JSESSIONID"));

		mockServer.enqueue(new MockResponse());

		response =
				given()
						.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), sessionFilter)
						.baseUri("http://localhost")
						.port(port)
						.contentType("application/json")
						.body("{\"testType\":\"TASKSWITCHING\",\"info\":\"game\"}")
				.when()
						.redirects().follow(false)
						.post("app/result");

		assertEquals(200, response.statusCode());

		RecordedRequest request = mockServer.takeRequest(20, TimeUnit.SECONDS);
		assertEquals("/callbackPath", request.getPath());
		assertEquals("POST", request.getMethod());
		assertEquals("{\"session\": \"testToken1234\"}", request.getBody().readUtf8());
	}

	@Test
	void loginFailsWithBadClientId() {
		SessionFilter sessionFilter = new SessionFilter();
		Response response =
				given()
						.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), sessionFilter)
						.baseUri("http://localhost")
						.port(port)
						.queryParam("callbackToken", "testToken1234")
						.queryParam("clientId", "wrongClientId")
						.when()
						.redirects().follow(false)
						.get("app/auth/testuser");

		assertEquals(302, response.statusCode());
		assertNotNull(response.getHeader("Location"));
		assertTrue(response.getHeader("Location").endsWith("/index.html?error"));
		assertFalse(response.getCookies().containsKey("JSESSIONID"));
	}
}
