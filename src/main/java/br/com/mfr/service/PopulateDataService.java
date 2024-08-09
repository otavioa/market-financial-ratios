package br.com.mfr.service;

import br.com.mfr.service.datasource.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Semaphore;
import java.util.stream.Stream;

import static br.com.mfr.service.PopulateDataEventHelper.*;

@Service
@Transactional(readOnly = true)
public class PopulateDataService {

    private final ApplicationEventPublisher publisher;

    private final BrazilStockSource brlStockSource;
    private final BrazilFiiSource brlFiiSource;
    private final BrazilEtfSource brlEtfSource;
    private final UsaStockSource usaStockSource;
    private final UsaReitSource usaReitSource;
    private final UsaEtfSource usaEtfSource;

    private final Semaphore semaphore = new Semaphore(1);

    public PopulateDataService(
            ApplicationEventPublisher publisher, BrazilStockSource brlStockSource, BrazilFiiSource brlFiiSource,
            BrazilEtfSource brlEtfSource, UsaStockSource usaStockSource, UsaReitSource usaReitSource,
            UsaEtfSource usaEtfSource) {

        this.publisher = publisher;
        this.brlStockSource = brlStockSource;
        this.brlFiiSource = brlFiiSource;
        this.brlEtfSource = brlEtfSource;
        this.usaStockSource = usaStockSource;
        this.usaReitSource = usaReitSource;
        this.usaEtfSource = usaEtfSource;
    }

    @Transactional
    @Async("dataPopulateThread")
    public void populateData() {
        if (semaphore.tryAcquire()) {
            sendInitialized(publisher);

            getDataSources()
                    .parallel()
                    .forEach(e -> populateDataSource(e));

            sendCompleted(publisher);
            semaphore.release();
        }
    }

    private void populateDataSource(DataSource e) {
        try {
            var result = e.populate();
            sendExecuted(publisher, result);
        } catch (Exception ex) {
            sendError(publisher, new DataSourceResult(e.type(), ex.getMessage()));
        }
    }

    private Stream<DataSource> getDataSources() {
        return Stream.of(brlStockSource, brlFiiSource, brlEtfSource,
                usaStockSource, usaReitSource, usaEtfSource);
    }
}
