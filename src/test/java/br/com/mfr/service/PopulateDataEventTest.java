package br.com.mfr.service;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PopulateDataEventTest {

    @Test
    void testToString() {
        UUID uuid = UUID.fromString("937fbde7-00b9-48d0-8442-0fb506ec5ceb");
        PopulateDataEvent event = new PopulateDataEvent(uuid, PopulateDataEvent.INITIALIZED);

        assertEquals("Event: 937fbde7-00b9-48d0-8442-0fb506ec5ceb - INITIALIZED", event.toString());
    }

    @Test
    void isComplete() {
        UUID uuid = UUID.fromString("937fbde7-00b9-48d0-8442-0fb506ec5ceb");

        PopulateDataEvent event1 = new PopulateDataEvent(uuid, PopulateDataEvent.INITIALIZED);
        assertFalse(event1.isComplete());

        PopulateDataEvent event2 = new PopulateDataEvent(uuid, PopulateDataEvent.COMPLETED);
        assertTrue(event2.isComplete());
    }
}