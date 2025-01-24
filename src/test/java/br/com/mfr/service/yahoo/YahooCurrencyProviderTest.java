package br.com.mfr.service.yahoo;

import br.com.mfr.exception.GenericException;
import br.com.mfr.external.url.ExternalURLException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mock.Strictness.LENIENT;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class YahooCurrencyProviderTest {

    @Mock(strictness = LENIENT)
    private YahooHttpAgent agent;

    private YahooCurrencyProvider subject;

    @BeforeEach
    public void setUp() throws ExternalURLException {
        String cookie = "cookie";
        String crumb = "crumb";

        when(agent.retrieveCookies()).thenReturn(cookie);
        when(agent.retrieveCrumb(cookie)).thenReturn(crumb);

        when(agent.retrieveQuote(eq(cookie), eq(crumb), anyString()))
                .thenReturn(new YahooQuoteResponse("BRL=X", "CURRENCY", "US", "CCY", 6.00, 15));

        this.subject = new YahooCurrencyProvider(agent);
    }

    @Test
    void retrieveHappyDay() {
        String currencyPrice = subject.retrieve("BRL");
        Assertions.assertEquals("6.0", currencyPrice);
    }

    @Test
    void retrieveNullSymbol() {
        Assertions.assertThrows(NullPointerException.class,
                () -> subject.retrieve(null),
                "symbol cannot be null");
    }

    @Test
    void emptyResult() throws ExternalURLException {
        when(agent.retrieveQuote(eq("cookie"), eq("crumb"), anyString())).thenReturn(emptyResponse());

        String currencyPrice = subject.retrieve("BRL");
        Assertions.assertEquals("0.0", currencyPrice);
    }

    @Test
    void withError() throws ExternalURLException {
        Mockito.doThrow(new ExternalURLException("Exception occurred")).when(agent).retrieveCookies();

        Assertions.assertThrows(GenericException.class,
                () -> subject.retrieve("BRL"),
                "Currency retrieval from Yahoo! failed. Message: Exception occurred");
    }

    @Test
    void assertYahooSymbol() throws ExternalURLException {
        String currencyPrice = subject.retrieve("BRL");
        Assertions.assertEquals("6.0", currencyPrice);

        Mockito.verify(agent).retrieveQuote("cookie", "crumb", "BRL=X");
    }

    @Test
    void assertYahooAgentOrder() throws ExternalURLException {
        subject.retrieve("BRL");

        InOrder inOrder = inOrder(agent);
        inOrder.verify(agent).retrieveCookies();
        inOrder.verify(agent).retrieveCrumb("cookie");
        inOrder.verify(agent).retrieveQuote("cookie", "crumb", "BRL=X");
    }

    private static YahooQuoteResponse emptyResponse() {
        return new YahooQuoteResponse(new QuoteResponse(List.of(), null));
    }

}