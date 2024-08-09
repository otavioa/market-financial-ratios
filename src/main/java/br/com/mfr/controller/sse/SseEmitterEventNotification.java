package br.com.mfr.controller.sse;

import java.util.UUID;

public interface SseEmitterEventNotification {

    UUID id();
    String name();

    Object data();

    boolean isComplete();
}
