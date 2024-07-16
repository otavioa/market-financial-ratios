package br.com.mfr.service;

import java.util.UUID;

public record DataChargeEvent(UUID id, String name, String description) {

    public static final String COMPLETED = "COMPLETED";
    public static final String START_PROCESSING = "START_PROCESSING";
    public static final String REMOVED = "REMOVED";

    public DataChargeEvent(UUID id, String name){
        this(id, name, "");
    }

    @Override
    public String toString() {
        return String.format("Event: %s - %s", id, name);
    }
}
