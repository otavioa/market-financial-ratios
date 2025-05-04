package br.com.mfr.service.htmlreader;

import br.com.mfr.PlaywrightConfiguration.PlaywrightProvider;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.ObjectProvider;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaywrightReaderServiceTest {

    @Mock
    private ObjectProvider<PlaywrightProvider> objectProviderMock;

    @Mock
    private PlaywrightProvider playwrightProviderMock;

    @Mock
    private Page pageMock;

    @Mock
    private Response responseMock;

    private PlaywrightReaderService service;

    @BeforeEach
    void setUp() {
        when(objectProviderMock.getObject()).thenReturn(playwrightProviderMock);
        when(playwrightProviderMock.newPage()).thenReturn(pageMock);
        when(pageMock.navigate(anyString())).thenReturn(responseMock);

        service = new PlaywrightReaderService(objectProviderMock);
    }

    @Test
    void shouldReturnDocumentWhenResponseIs200() throws IOException {
        String expectedHtml = "<html><body><h1>Test</h1></body></html>";
        when(responseMock.status()).thenReturn(200);
        when(pageMock.content()).thenReturn(expectedHtml);

        Document doc = service.getHTMLDocument("https://example.com");

        assertNotNull(doc);
        assertEquals("Test", doc.body().text());
        verify(playwrightProviderMock).close();
    }

    @Test
    void shouldThrowHttpStatusExceptionWhenResponseIs404() {
        when(responseMock.status()).thenReturn(404);

        HttpStatusException exception = assertThrows(
                HttpStatusException.class,
                () -> service.getHTMLDocument("https://invalid.com")
        );

        assertEquals(404, exception.getStatusCode());
        verify(playwrightProviderMock).close();
    }

    @Test
    void shouldAlwaysClosePlaywrightProviderEvenOnException() {
        when(pageMock.navigate(anyString())).thenThrow(new RuntimeException("Failed to access page"));

        assertThrows(RuntimeException.class, () -> service.getHTMLDocument("https://example.com"));

        verify(playwrightProviderMock).close();
    }
}
