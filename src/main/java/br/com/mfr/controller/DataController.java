package br.com.mfr.controller;

import br.com.mfr.controller.sse.SseEmitterManager;
import br.com.mfr.service.PopulateDataEvent;
import br.com.mfr.service.PopulateDataService;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/data")
public class DataController {

    private final PopulateDataService service;
    private final SseEmitterManager sseManager;


    public DataController(PopulateDataService service, SseEmitterManager sseManager) {
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
    public void onPopulateDataEvent(PopulateDataEvent event) {
        sseManager.notifyEmitters(event);
    }
}
