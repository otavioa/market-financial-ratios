package br.com.b3.entity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {

    Company findByNome(String nome);

    Company findByTicker(String ticker);
    public long count();
}
