package com.shimai.spring.coindesk;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface CoinRepository extends CrudRepository<Coin, String> {
    List<Coin> findByCode(String code);
}
