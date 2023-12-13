package br.com.mfr.entity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {

    Company findByNome(String nome);

    List<Company> findByTickerInAndTypeIn(List<String> tickers, List<String> types);

    List<Company> findByTypeIn(List<String> types);

    List<Company> findByTickerIn(List<String> tickers);

    public long count();
}
