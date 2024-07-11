package br.com.mfr.controller;

import br.com.mfr.service.DataChargeService;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping("/data")
public class DataController {

    private final DataChargeService service;

    public DataController(DataChargeService service) {
        this.service = service;
    }

    @GetMapping("/populate")
    public String populateData() {
        Instant start = Instant.now();
        service.populateData();
        Instant end = Instant.now();

        return STR."Time elapsed: \{getDurationOfRequest(start, end)}";
    }

    private static String getDurationOfRequest(Instant start, Instant end) {
        Duration between = Duration.between(start, end);
        return DurationFormatUtils.formatDuration(between.toMillis(), "HH:mm:ss");
    }
}
