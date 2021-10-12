package br.com.b3.external.url.client;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class ExternalURLInterceptor implements ClientHttpRequestInterceptor {

    final static Logger log = LoggerFactory.getLogger(ExternalURLInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        traceResponse(response);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) throws IOException {
        log.info("=========================== EXTERNAL REQUEST - START ===================");
        log.info("URI           : {}", request.getURI());
        log.info("Method        : {}", request.getMethod());
        log.info("Headers       : {}", request.getHeaders() );
        log.info("Request body  : {}", new String(body, "UTF-8"));
    }

    //TODO - Corrigir o buffer
    private void traceResponse(ClientHttpResponse response) throws IOException {
//        StringBuilder inputStringBuilder = new StringBuilder();
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
//        String line = bufferedReader.readLine();
//        while (line != null) {
//            inputStringBuilder.append(line);
//            inputStringBuilder.append('\n');
//            line = bufferedReader.readLine();
//        }
        
        log.info("Response code : {}", response.getStatusCode());
        log.info("Response text : {}", response.getStatusText());
        log.info("Headers       : {}", response.getHeaders());
        log.info("Response body : {}", "Omitido");
        log.info("============================ EXTERNAL REQUEST - END ====================");
    }
}