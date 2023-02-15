package com.shimai.spring.coindesk;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

@RestController
@RequestMapping("/coin")
public class CoinController {

    private final CoinRepository coinRepository;
    private final CoinService coinService;

    public CoinController(CoinRepository coinRepository){
        this.coinRepository = coinRepository;
        this.coinService = new CoinService();
        initializeCoin();
    }

    private void initializeCoin() {
        ArrayList<HashMap<String, String>> src = this.coinService.fetchCoinDesk();
        for(HashMap<String, String> item : src) {
            String code = item.get("code");
            String rate = item.get("rate");
            String nameEng = item.get("nameEng");
            String nameChi = item.get("nameChi");
            Coin coin = new Coin(code);
            coin.setRate(rate);
            coin.setNameEng(nameEng);
            coin.setNameChi(nameChi);
            coinRepository.save(coin);
        }
    }

    private void fetchCoin() {
        ArrayList<HashMap<String, String>> src = this.coinService.fetchCoinDesk();
        for(HashMap<String, String> item : src) {
            String code = item.get("code");
            String rate = item.get("rate");
            String nameEng = item.get("nameEng");
            String nameChi = item.get("nameChi");
            List<Coin> coins = coinRepository.findByCode(code);
            Coin coin = coins.get(0);
            coin.setRate(rate);
            coin.setNameEng(nameEng);
            coin.setNameChi(nameChi);
            coinRepository.save(coin);
        }
    }

    // [GET] coin/updateToLatest
    @GetMapping("/updateToLatest")
    public Iterable<Coin> updateToLatest(){
        fetchCoin();
        return coinRepository.findAll();
    }

    // [GET] coin
    @GetMapping
    public Iterable<Coin> index(){
        return coinRepository.findAll();
    }

    // [GET] coin/{id}
    @GetMapping("/{id}")
    public Optional<Coin> show(@PathVariable String id){
        return coinRepository.findById(id);
    }

    // [POST] coin
    @PostMapping
    public Coin create(@RequestBody Coin coin){
        return coinRepository.save(coin);
    }

    // [PUT] coin/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Coin> update(
            @PathVariable String id,
            @RequestBody Coin coin
    ){
        if (coinRepository.existsById(id)) {
            return new ResponseEntity<>(coinRepository.save(coin), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(coinRepository.save(coin), HttpStatus.OK);
        }
    }

    // [DELETE] coin/{id}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        coinRepository.deleteById(id);
    }

}
