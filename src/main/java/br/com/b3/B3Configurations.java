package br.com.b3;

import br.com.b3.entity.Company;
import com.fasterxml.classmate.TypeResolver;
import io.netty.channel.ChannelOption;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class B3Configurations {

    public static final int CODEC_MAX_SIZE = 10 * 1024 * 1024; //10mb
    public static final int MAX_TIMEOUT = 5000;

    @Profile("prod")
    @Bean
    public Docket api(TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2)
                .additionalModels(typeResolver.resolve(Company.class))
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.b3.controller"))
                .paths(PathSelectors.ant("/statusinvest/**"))
                .build();
    }

    @Bean
    public WebClient bWebClient() {
        HttpClient httpClient = prepareHttpClient();

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .codecs(codecs -> codecs
                        .defaultCodecs()
                        .maxInMemorySize(CODEC_MAX_SIZE))
                .build();
    }

    private HttpClient prepareHttpClient() {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, MAX_TIMEOUT)
                .responseTimeout(Duration.ofMillis(5000))
                .wiretap(this.getClass().getCanonicalName(), LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL)
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(MAX_TIMEOUT, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(MAX_TIMEOUT, TimeUnit.MILLISECONDS)));
    }
}
