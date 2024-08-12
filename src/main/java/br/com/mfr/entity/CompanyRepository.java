package br.com.mfr.entity;

import br.com.mfr.service.datasource.DataSourceType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {

    List<Company> findByTickerInAndSourceIn(List<String> tickers, List<String> sources);

    List<Company> findBySourceIn(List<String> sources);

    List<Company> findByTickerIn(List<String> tickers);

    void deleteAllBySource(DataSourceType source);

    long count();
}
