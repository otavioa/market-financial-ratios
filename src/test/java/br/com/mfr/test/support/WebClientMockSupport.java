package br.com.mfr.test.support;

import br.com.mfr.external.url.RequestBody;
import br.com.mfr.external.url.ResponseBody;
import br.com.mfr.external.url.UrilUtils;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.mockito.verification.VerificationMode;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Consumer;

import static java.util.Objects.isNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WebClientMockSupport {

    public static MockVerifier answerForAnyRetrieve(WebClient client, Answer<?> answer) {
        return answerForAnyRequest(client, null, answer);
    }

    public static MockVerifier answerForAnyRequest(WebClient client, RequestBody request, Answer<?> answer) {
        var bodyUriSpecMock = mock(WebClient.RequestBodyUriSpec.class);
        var bodySpecMock = mock(WebClient.RequestBodySpec.class);
        var headersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        var responseSpecMock = mock(WebClient.ResponseSpec.class);
        var mono = mock(Mono.class);

        when(client.method(any())).thenReturn(bodyUriSpecMock);
        when(bodyUriSpecMock.uri(any(URI.class))).thenReturn(bodySpecMock);
        when(bodySpecMock.headers(any())).thenReturn(bodySpecMock);

        if(!isNull(request)){
            when(bodySpecMock.bodyValue(request)).thenReturn(headersSpecMock);
            when(headersSpecMock.retrieve()).thenReturn(responseSpecMock);
        } else
            when(bodySpecMock.retrieve()).thenReturn(responseSpecMock);

        Class<ResponseBody> any = any();
        when(responseSpecMock.toEntity(any)).thenAnswer(a -> {
            Object response = answer.answer(a);
            when(mono.block()).thenReturn(ResponseEntity.ofNullable(response));

            return mono;
        });

        return new MockVerifier(client, bodyUriSpecMock, 
                bodySpecMock, headersSpecMock, responseSpecMock, mono);
    }

    public static class MockVerifier {

        private final WebClient client;
        private final WebClient.RequestBodyUriSpec bodyUriSpecMock;
        private final WebClient.RequestBodySpec bodySpecMock;
        private final WebClient.RequestHeadersSpec headersSpecMock;
        private final WebClient.ResponseSpec responseSpecMock;
        private final Mono mono;

        private MockVerifier(WebClient client, WebClient.RequestBodyUriSpec bodyUriSpecMock,
                             WebClient.RequestBodySpec bodySpecMock, WebClient.RequestHeadersSpec headersSpecMock,
                             WebClient.ResponseSpec responseSpecMock, Mono mono){

            this.mono = mono;
            this.client = client;
            this.bodyUriSpecMock = bodyUriSpecMock;
            this.bodySpecMock = bodySpecMock;
            this.headersSpecMock = headersSpecMock;
            this.responseSpecMock = responseSpecMock;
        }

        public void verifyMethod(VerificationMode mode, HttpMethod method) {
            verify(client, mode).method(method);
        }

        public void verifyUrl(VerificationMode mode, String url) {
            Mockito.verify(bodyUriSpecMock, mode).uri(UrilUtils.getURI(url));
        }

        public void verifyHeader(VerificationMode times) {
            Mockito.verify(bodySpecMock, times).headers(any(Consumer.class));

        }

        public void verifyResponse(Class<?> responseClass) {
            Mockito.verify(responseSpecMock, Mockito.times(1)).toEntity(responseClass);
            Mockito.verify(mono, Mockito.times(1)).block();
        }

        public void verifyRequestBody(VerificationMode times, Class<RequestBody> bodyClass) {
            Mockito.verify(bodySpecMock, Mockito.times(1)).bodyValue(Mockito.any(bodyClass));
        }
    }
}
