package br.com.mfr.service;

import org.springframework.context.ApplicationEventPublisher;

import java.util.UUID;

public class PopulateDataEventHelper {

    private PopulateDataEventHelper(){}

    public static void sendInitialized(ApplicationEventPublisher publisher) {
        publisher.publishEvent(new PopulateDataEvent(UUID.randomUUID(), PopulateDataEvent.INITIALIZED));
    }

    public static void sendCompleted(ApplicationEventPublisher publisher) {
        publisher.publishEvent(
                new PopulateDataEvent(UUID.randomUUID(), PopulateDataEvent.COMPLETED));
    }

    public static void sendExecuted(ApplicationEventPublisher publisher, Object data) {
        publisher.publishEvent(
                new PopulateDataEvent(UUID.randomUUID(), PopulateDataEvent.EXECUTED, data));
    }

    public static void sendError(ApplicationEventPublisher publisher, Object data) {
        publisher.publishEvent(
                new PopulateDataEvent(UUID.randomUUID(), PopulateDataEvent.ERROR, data));
    }
}
