package br.com.mfr.service;

import br.com.mfr.service.datasource.MultiLocationSource;
import br.com.mfr.service.datasource.UsaEtfSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Semaphore;

@Service
@Transactional(readOnly = true)
public class PopulateDataService {

    private final UsaEtfSource usaEtfSource;
    private final MultiLocationSource multiLocationSource;

    private final Semaphore semaphore = new Semaphore(1);

    public PopulateDataService(MultiLocationSource multiLocationSource, UsaEtfSource usaEtfSource) {

        this.multiLocationSource = multiLocationSource;
        this.usaEtfSource = usaEtfSource;
    }

    @Transactional
    @Async("dataPopulateThread")
    public void populateData() {
        if (semaphore.tryAcquire()) {
            multiLocationSource.populate();

            semaphore.release();
        }
    }

    public void populateEtfData() {
        usaEtfSource.populate();
    }
}
