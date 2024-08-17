package br.com.mfr.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PopulateDataEventHelperTest {

    @Mock
    private ApplicationEventPublisher publisher;

    @Captor
    ArgumentCaptor<PopulateDataEvent> eventCaptor;

    @BeforeEach
    void setUp() {
        Mockito.doNothing().when(publisher).publishEvent(any(PopulateDataEvent.class));
    }

    @Test
    void sendInitialized() {
        PopulateDataEventHelper.sendInitialized(publisher);

        Mockito.verify(publisher, Mockito.times(1)).publishEvent(eventCaptor.capture());

        PopulateDataEvent event = eventCaptor.getValue();
        assertEquals(PopulateDataEvent.INITIALIZED, event.name());
        assertEquals("", event.data());
        assertNotNull(event.id());
    }

    @Test
    void sendCompleted() {
        PopulateDataEventHelper.sendCompleted(publisher);

        Mockito.verify(publisher, Mockito.times(1)).publishEvent(eventCaptor.capture());

        PopulateDataEvent event = eventCaptor.getValue();
        assertEquals(PopulateDataEvent.COMPLETED, event.name());
        assertEquals("", event.data());
        assertNotNull(event.id());
    }

    @Test
    void sendExecuted() {
        PopulateDataEventHelper.sendExecuted(publisher, "{\"data\": \"test\"}");

        Mockito.verify(publisher, Mockito.times(1)).publishEvent(eventCaptor.capture());

        PopulateDataEvent event = eventCaptor.getValue();
        assertEquals(PopulateDataEvent.EXECUTED, event.name());
        assertEquals("{\"data\": \"test\"}", event.data());
        assertNotNull(event.id());
    }

    @Test
    void sendError() {
        PopulateDataEventHelper.sendError(publisher, "Message Error");

        Mockito.verify(publisher, Mockito.times(1)).publishEvent(eventCaptor.capture());

        PopulateDataEvent event = eventCaptor.getValue();
        assertEquals(PopulateDataEvent.ERROR, event.name());
        assertEquals("Message Error", event.data());
        assertNotNull(event.id());
    }
}