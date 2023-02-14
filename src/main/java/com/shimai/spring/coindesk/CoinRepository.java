package com.shimai.spring.coindesk;

import org.springframework.data.repository.CrudRepository;

interface CoinRepository extends CrudRepository<Coin, String> {}
