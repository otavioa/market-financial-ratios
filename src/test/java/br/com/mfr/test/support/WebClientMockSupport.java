package br.com.mfr.test.support;

import br.com.mfr.external.url.RequestBody;
import br.com.mfr.external.url.ResponseBody;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

import static java.util.Objects.isNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class WebClientMockSupport {

    public static void answerForAnyRetrieve(WebClient client, Answer<?> answer) {
        answerForAnyRequest(client, null, answer);
    }

    public static void answerForAnyRequest(WebClient client, RequestBody request, Answer<?> answer) {
        var bodyUriSpecMock = Mockito.mock(WebClient.RequestBodyUriSpec.class);
        var bodySpecMock = Mockito.mock(WebClient.RequestBodySpec.class);
        var headersSpecMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
        var responseSpecMock = Mockito.mock(WebClient.ResponseSpec.class);

        when(client.method(any())).thenReturn(bodyUriSpecMock);
        when(bodyUriSpecMock.uri(any(URI.class))).thenReturn(bodySpecMock);
        when(bodySpecMock.headers(any())).thenReturn(bodySpecMock);

        if(!isNull(request)){
            when(bodySpecMock.bodyValue(request)).thenReturn(headersSpecMock);
            when(headersSpecMock.retrieve()).thenReturn(responseSpecMock);
        } else
            when(bodySpecMock.retrieve()).thenReturn(responseSpecMock);

        when(responseSpecMock.bodyToMono(ResponseBody.class)).thenAnswer(answer);
    }
}
