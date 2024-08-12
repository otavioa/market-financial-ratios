package br.com.mfr.controller.sse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SseEmitterManager {

    private static final long SSE_TIMEOUT = 0L; //TODO - Future property

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public void addEmitter(SseEmitter emitter) {
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(t -> emitters.remove(emitter));

        emitters.add(emitter);
    }

    public void notifyEmitters(SseEmitterEventNotification eventNotification) {
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .id(eventNotification.id().toString())
                        .name(eventNotification.name())
                        .data(eventNotification.data()));

                if(eventNotification.isComplete()){
                    emitter.complete();
                    emitters.remove(emitter);
                }
            } catch (IOException e) {
                emitter.completeWithError(e);
                emitters.remove(emitter);
            }
        });
    }

    public SseEmitter newEmitter() {
        return new SseEmitter(SSE_TIMEOUT);
    }

    public List<SseEmitter> getEmitters() {
        return emitters;
    }
}
