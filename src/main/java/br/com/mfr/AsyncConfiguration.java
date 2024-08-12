package br.com.mfr;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableAsync
@Configuration
@ConditionalOnProperty(name = "mfr.async.enabled", havingValue = "true")
public class AsyncConfiguration {

    @Bean
    ExecutorService dataPopulateThread() {
        return Executors.newSingleThreadExecutor(
                Thread.ofVirtual()
                        .name("data-populate")
                        .factory());
    }
}
