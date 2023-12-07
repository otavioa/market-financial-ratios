package br.com.mfr.external.url.client;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;

import br.com.mfr.external.url.ResponseBody;

@ExtendWith(MockitoExtension.class)
class ExternalURLClientExceptionTest {

	@Mock
	private HttpClientErrorException ex;
	
	private ExternalURLClientException subject;
	
	@Test
	void newWithMessage() throws ExternalURLClientException {
		subject = new ExternalURLClientException("Message");
		
		Assertions.assertThat(subject.getMessage()).isEqualTo("Message");
		Assertions.assertThat(subject.getHttpStatus()).isEqualTo(BAD_REQUEST);
		Assertions.assertThat(subject.getCode()).isNull();
		Assertions.assertThat(subject.getResponseBodyAs(FakeResponse.class).isPresent()).isFalse();
		Assertions.assertThat(subject.getResponseBodyAsString().isPresent()).isFalse();
	}
	
	@Test
	void newWithMessageAndCode() throws ExternalURLClientException {
		subject = new ExternalURLClientException("Message2", "#1009");
		
		Assertions.assertThat(subject.getMessage()).isEqualTo("Message2");
		Assertions.assertThat(subject.getHttpStatus()).isEqualTo(BAD_REQUEST);
		Assertions.assertThat(subject.getCode()).isEqualTo("#1009");
		Assertions.assertThat(subject.getResponseBodyAs(FakeResponse.class).isPresent()).isFalse();
		Assertions.assertThat(subject.getResponseBodyAsString().isPresent()).isFalse();
	}
	
	@Test
	void newWithException() throws ExternalURLClientException {
		Mockito.when(ex.getMessage()).thenReturn("exception");
		Mockito.when(ex.getStatusCode()).thenReturn(BAD_GATEWAY);
		Mockito.when(ex.getResponseBodyAsString()).thenReturn("{\"attribute\": \"broke\"}");
		
		subject = new ExternalURLClientException(ex);
		
		Assertions.assertThat(subject.getMessage()).isEqualTo("exception");
		Assertions.assertThat(subject.getHttpStatus()).isEqualTo(BAD_GATEWAY);
		Assertions.assertThat(subject.getCode()).isNull();
		Assertions.assertThat(subject.getResponseBodyAsString().isPresent()).isTrue();
		Assertions.assertThat(subject.getResponseBodyAsString().get()).isEqualTo("{\"attribute\": \"broke\"}");
		
		Optional<FakeResponse> response = subject.getResponseBodyAs(FakeResponse.class);
		Assertions.assertThat(response.isPresent()).isTrue();
		Assertions.assertThat(response.get().getAttribute()).isEqualTo("broke");
		
	}
	
	@Test
	void newWithException_breakParse() throws ExternalURLClientException {
		Mockito.when(ex.getMessage()).thenReturn("exception");
		Mockito.when(ex.getResponseBodyAsString()).thenReturn("{attribute\": \"broke\"}");
		
		subject = new ExternalURLClientException(ex);
		
		ExternalURLClientException thrown = assertThrows(ExternalURLClientException.class, () -> {
			
			subject.getResponseBodyAs(FakeResponse.class);
			
		}, "ExternalURLClientException was expected");
		
		Assertions.assertThat(thrown.getMessage()).isEqualTo("Attempt to convert message into object fail. Message: exception");
	}

	public static class FakeResponse implements ResponseBody {
			
		private String attribute;
		
		public FakeResponse() {}
		
		public String getAttribute() {
			return attribute;
		}

		public void setAttribute(String attribute) {
			this.attribute = attribute;
		}
		
	}
}
