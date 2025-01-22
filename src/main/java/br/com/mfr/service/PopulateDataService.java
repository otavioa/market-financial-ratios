package br.com.mfr.service;

import br.com.mfr.service.datasource.DataSource;
import br.com.mfr.service.datasource.DataSourceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.Semaphore;

import static br.com.mfr.service.PopulateDataEventHelper.*;

@Service
@Transactional(readOnly = true)
public class PopulateDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PopulateDataService.class);

    private final ApplicationEventPublisher publisher;
    private final List<DataSource> dataSources;

    private final Semaphore semaphore = new Semaphore(1);

    public PopulateDataService(ApplicationEventPublisher publisher, List<DataSource> dataSources) {
        this.publisher = publisher;
        this.dataSources = dataSources;
    }

    @Transactional
    @Async("dataPopulateThread")
    public void populateData() {
        if (semaphore.tryAcquire()) {
            sendInitialized(publisher);

            dataSources
                    .stream()
                    .parallel()
                    .forEach(this::populateDataSource);

            sendCompleted(publisher);
            semaphore.release();
        }
    }

    private void populateDataSource(DataSource e) {
        try {
            var result = e.populate();
            sendExecuted(publisher, result);
            LOGGER.info("DataSource executed: Source: {}, Result: {}", result.type(), result.result());
        } catch (Exception ex) {
            sendError(publisher, new DataSourceResult(e.type(), ex.getMessage()));
        }
    }
}
