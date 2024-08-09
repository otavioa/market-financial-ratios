package br.com.mfr.controller.sse;

import br.com.mfr.service.PopulateDataEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SseEmitterManagerTest {

    @Mock
    private SseEmitter emitter;
    @Mock
    private SseEmitter emitter2;

    private SseEmitterManager subject;

    @BeforeEach
    void setUp() {
        subject = new SseEmitterManager();
    }

    @Test
    void newEmitter() {
        SseEmitter emitter1 = subject.newEmitter();
        assertEquals(0L, emitter1.getTimeout());
    }

    @Test
    void addEmitter() {
        subject.addEmitter(emitter);

        Mockito.verify(emitter, Mockito.never()).onCompletion(ArgumentMatchers.any());
        Mockito.verify(emitter, Mockito.times(1)).onTimeout(ArgumentMatchers.any());
        Mockito.verify(emitter, Mockito.times(1)).onError(ArgumentMatchers.any());

        assertEquals(1, subject.getEmitters().size());
        assertTrue(subject.getEmitters().contains(emitter));
    }

    @Test
    void addMultiplesEmitters() {
        subject.addEmitter(emitter);
        subject.addEmitter(emitter2);

        List<SseEmitter> emitters = subject.getEmitters();
        assertEquals(2, emitters.size());

        assertInstanceOf(CopyOnWriteArrayList.class, emitters);
        assertTrue(emitters.contains(emitter));
        assertTrue(emitters.contains(emitter2));
    }

    @Test
    void notifyEmitters() throws IOException {
        UUID uuid = UUID.fromString("937fbde7-00b9-48d0-8442-0fb506ec5ceb");
        PopulateDataEvent event = new PopulateDataEvent(uuid, PopulateDataEvent.INITIALIZED);

        subject.addEmitter(emitter);
        subject.addEmitter(emitter2);

        subject.notifyEmitters(event);

        Mockito.verify(emitter, Mockito.times(1)).send(ArgumentMatchers.any(SseEmitter.SseEventBuilder.class));
        Mockito.verify(emitter2, Mockito.times(1)).send(ArgumentMatchers.any(SseEmitter.SseEventBuilder.class));

        Mockito.verify(emitter, Mockito.never()).complete();
        Mockito.verify(emitter2, Mockito.never()).complete();

        Mockito.verify(emitter, Mockito.never()).completeWithError(ArgumentMatchers.any());
        Mockito.verify(emitter2, Mockito.never()).completeWithError(ArgumentMatchers.any());

        assertEquals(2, subject.getEmitters().size());
    }

    @Test
    void notifyEmittersCompleted() throws IOException {
        UUID uuid = UUID.fromString("30f85bcf-585b-4e8c-863a-8a13bb0f24ee");
        PopulateDataEvent event = new PopulateDataEvent(uuid, PopulateDataEvent.COMPLETED);

        subject.addEmitter(emitter);
        subject.addEmitter(emitter2);

        subject.notifyEmitters(event);

        Mockito.verify(emitter, Mockito.times(1)).send(ArgumentMatchers.any(SseEmitter.SseEventBuilder.class));
        Mockito.verify(emitter2, Mockito.times(1)).send(ArgumentMatchers.any(SseEmitter.SseEventBuilder.class));

        Mockito.verify(emitter, Mockito.times(1)).complete();
        Mockito.verify(emitter2, Mockito.times(1)).complete();

        assertTrue(subject.getEmitters().isEmpty());
    }

    @Test
    void notifyEmittersError() throws IOException {
        UUID uuid = UUID.fromString("30f85bcf-585b-4e8c-863a-8a13bb0f24ee");
        PopulateDataEvent event = new PopulateDataEvent(uuid, PopulateDataEvent.COMPLETED);

        IOException exception = new IOException("Mock failure");
        Mockito.doThrow(exception).when(emitter).send(ArgumentMatchers.any(SseEmitter.SseEventBuilder.class));

        subject.addEmitter(emitter);

        try {
            subject.notifyEmitters(event);
        } catch (Exception e) {
            fail();
        }

        Mockito.verify(emitter, Mockito.times(1)).completeWithError(exception);
        Mockito.verify(emitter, Mockito.never()).complete();

        assertTrue(subject.getEmitters().isEmpty());
    }
}