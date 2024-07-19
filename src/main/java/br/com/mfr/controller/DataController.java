package br.com.mfr.controller;

import br.com.mfr.service.DataChargeEvent;
import br.com.mfr.service.DataChargeService;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping("/data")
public class DataController {

    private final DataChargeService service;
    private final ExecutorService sseThreadExecutor;
    private final SseEmitterManager sseManager;


    public DataController(DataChargeService service,
                          ExecutorService sseThreadExecutor, SseEmitterManager sseManager) {

        this.sseThreadExecutor = sseThreadExecutor;
        this.sseManager = sseManager;
        this.service = service;
    }

    @GetMapping("/populate")
    public SseEmitter populateData() {
        SseEmitter emitter = sseManager.newEmitter();
        sseManager.addEmitter(emitter);

        service.populateData();

        return emitter;
    }

    @EventListener
    public void onPopulateDataEvent(DataChargeEvent event) {
        sseManager.notifyEmitters(event);
    }
}
