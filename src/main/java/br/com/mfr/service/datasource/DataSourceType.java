package br.com.mfr.service.datasource;

public enum DataSourceType {

    BRL_STOCK(BrazilStockSource.class), BRL_FII(BrazilFiiSource.class), BRL_ETF(BrazilEtfSource.class),
    USA_STOCK(UsaStockSource.class), USA_REIT(UsaReitSource.class), USA_ETF(UsaEtfSource.class);

    private final Class<? extends DataSource> dataSource;

    DataSourceType(Class<? extends DataSource> dataSource) {
        this.dataSource = dataSource;
    }

}
