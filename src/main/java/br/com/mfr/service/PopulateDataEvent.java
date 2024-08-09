package br.com.mfr.service;

import br.com.mfr.controller.sse.SseEmitterEventNotification;

import java.util.UUID;

public record PopulateDataEvent(UUID id, String name, Object data) implements SseEmitterEventNotification {

    public static final String COMPLETED = "COMPLETED";
    public static final String EXECUTED = "EXECUTED";
    public static final String ERROR = "ERROR";
    public static final String INITIALIZED = "INITIALIZED";

    public PopulateDataEvent(UUID id, String name){
        this(id, name, "");
    }

    @Override
    public String toString() {
        return String.format("Event: %s - %s. Data: %s", id, name, data);
    }

    @Override
    public boolean isComplete() {
        return this.name().equals(COMPLETED);
    }

}
