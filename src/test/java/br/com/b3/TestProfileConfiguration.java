package br.com.b3;

import br.com.b3.entity.CompanyRepository;
import okhttp3.mockwebserver.MockWebServer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile(TestProfileConfiguration.TEST_PROFILE)
@Configuration
public class TestProfileConfiguration {

	public static final String TEST_PROFILE = "test-profile";

	@MockBean private CompanyRepository repository;

	@Bean
	public MockWebServer webServer() {
		return new MockWebServer();
	}

}
