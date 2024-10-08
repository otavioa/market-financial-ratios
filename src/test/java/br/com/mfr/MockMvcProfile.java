package br.com.mfr;

import br.com.mfr.entity.CompanyRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Profile(MockMvcProfile.MOCK_MVC_PROFILE)
@Configuration
public class MockMvcProfile {

	public static final String MOCK_MVC_PROFILE = "mock-mvc-profile";
	public static final int WIRE_MOCK_PORT = 5050;

	@MockBean private CompanyRepository repository;

}

