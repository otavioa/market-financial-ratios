package br.com.mfr.controller;

import br.com.mfr.exception.GenericException;
import br.com.mfr.service.DataChargeService;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping("/data")
public class DataController {

    public static final long SSE_TIMEOUT = 30_000L;

    private final DataChargeService service;
    private final ExecutorService sseThreadExecutor;


    public DataController(DataChargeService service, ExecutorService sseThreadExecutor) {
        this.sseThreadExecutor = sseThreadExecutor;
        this.service = service;
    }

    @GetMapping("/populate")
    public SseEmitter populateData() {
        SseEmitter emitter = new SseEmitter(SSE_TIMEOUT);

        sseThreadExecutor.execute(() -> {
            try {
                service.populateData(emitter);
                emitter.complete();
            } catch (GenericException e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    private static String getDurationOfRequest(Instant start, Instant end) {
        Duration between = Duration.between(start, end);
        return DurationFormatUtils.formatDuration(between.toMillis(), "HH:mm:ss");
    }
}
