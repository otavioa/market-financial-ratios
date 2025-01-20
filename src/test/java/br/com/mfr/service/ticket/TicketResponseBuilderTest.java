package br.com.mfr.service.ticket;

import br.com.mfr.test.support.ReaderServiceMockSupport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TicketResponseBuilderTest {

    private static Document document;

    @BeforeAll
    static void setEnvironment() throws IOException {
        document = ReaderServiceMockSupport.getDocumentFrom("testdata/wege3_page.html", "https://teste.com.br/acoes/WEGE3");
    }

    @Test
    void simpleResponse() throws IOException {
        TickerResponse response = TickerResponse.builder()
                .setDocument(document)
                .setTicker("WEGE3")
                .withValue()
                .build();

        assertResponse("""
                {
                   "ticker":"WEGE3",
                   "value":"35,65"
                }
                """, response);
    }

    @Test
    void simpleResponse_withZeros() throws IOException {
        TickerResponse response = TickerResponse.builder()
                .setDocument(document)
                .setTicker("WEGE3")
                .setUseZero()
                .withValue()
                .build();

        assertResponse("""
                {
                   "dy":"0,00",
                   "lpa":"0,00",
                   "pl":"0,00",
                   "pvp":"0,00",
                   "roe":"0,00",
                   "ticker":"WEGE3",
                   "value":"35,65",
                   "vpa":"0,00"
                }
                """, response);
    }

    @Test
    void response_withAllIndicators() throws IOException {
        TickerResponse response = TickerResponse.builder()
                .setDocument(document)
                .setTicker("WEGE3")
                .setUseZero()
                .withValue()
                .withDY()
                .withLPA()
                .withPL()
                .withPVP()
                .withROE()
                .withVPA()
                .build();

        assertResponse("""
                {
                    "dy":"1,15",
                    "lpa":"0,82",
                    "pl":"43,32",
                    "pvp":"0,00",
                    "roe":"27,09",
                    "ticker":"WEGE3",
                    "value":"35,65",
                    "vpa":"3,04"
                 }
                """, response);
    }

    @Test
    void response_withSpecificRatio() throws IOException {
        TickerResponse response = TickerResponse.builder()
                .setDocument(document)
                .setTicker("WEGE3")
                .withValue()
                .withRatio("ROE")
                .build();

        assertResponse("""
                {
                    "ratio":"ROE",
                    "ticker":"WEGE3",
                    "value":"27,09"
                 }
                """, response);
    }

    private void assertResponse(String expected, TickerResponse response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode expectedNode = objectMapper.readTree(expected);
        JsonNode actualNode = objectMapper.readTree(response.toString());

        assertEquals(expectedNode, actualNode);
    }
}