package br.com.mfr.service.datasource;

public interface DataSource {

    DataSourceType type();
    DataSourceResult populate();

}
