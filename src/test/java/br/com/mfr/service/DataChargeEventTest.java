package br.com.mfr.service;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DataChargeEventTest {

    @Test
    void testToString() {
        UUID uuid = UUID.fromString("937fbde7-00b9-48d0-8442-0fb506ec5ceb");
        DataChargeEvent event = new DataChargeEvent(uuid, DataChargeEvent.START_PROCESSING);

        assertEquals("Event: 937fbde7-00b9-48d0-8442-0fb506ec5ceb - START_PROCESSING", event.toString());
    }

    @Test
    void isComplete() {
        UUID uuid = UUID.fromString("937fbde7-00b9-48d0-8442-0fb506ec5ceb");

        DataChargeEvent event1 = new DataChargeEvent(uuid, DataChargeEvent.START_PROCESSING);
        assertFalse(event1.isComplete());

        DataChargeEvent event2 = new DataChargeEvent(uuid, DataChargeEvent.COMPLETED);
        assertTrue(event2.isComplete());
    }
}