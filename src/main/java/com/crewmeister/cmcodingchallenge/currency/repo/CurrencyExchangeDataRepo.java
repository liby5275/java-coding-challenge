package com.crewmeister.cmcodingchallenge.currency.repo;

import com.crewmeister.cmcodingchallenge.currency.entity.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CurrencyExchangeDataRepo extends JpaRepository<CurrencyExchange, Integer> {

    public Optional<List<CurrencyExchange>> findByDate(Date date);

    public Optional<CurrencyExchange> findByCurrencyAndDate(String currency, Date date);

    public Optional<List<CurrencyExchange>> findAllByOrderByDateAsc();

    @Query(value = "Select DISTINCT r.currency from CurrencyExchange r", nativeQuery = true)
    public Optional<List<String>> fetchCurrencies();

}
