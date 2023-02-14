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

    public CoinController(CoinRepository coinRepository){
        this.coinRepository = coinRepository;
        initializeCoin();
    }

    // [GET] coin
    @GetMapping
    Iterable<Coin> index(){
        return coinRepository.findAll();
    }

    // [GET] coin/{id}
    @GetMapping("/{id}")
    Optional<Coin> show(@PathVariable String id){
        return coinRepository.findById(id);
    }

    // [POST] coin
    @PostMapping
    Coin create(@RequestBody Coin coin){
        return coinRepository.save(coin);
    }

    // [PUT] coin/{id}
    @PutMapping("/{id}")
    ResponseEntity<Coin> update(
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
    void delete(@PathVariable String id) {
        coinRepository.deleteById(id);
    }

    private void initializeCoin() {
        ArrayList<HashMap<String, String>> src = fetchCoinDesk();
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

    private ArrayList<HashMap<String, String>> fetchCoinDesk(){
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        try {
            URL url = new URL("https://api.coindesk.com/v1/bpi/currentprice.json");
            InputStream input = url.openStream();
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder json = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                json.append((char) c);
            }
            HashMap<String, HashMap> resource = new ObjectMapper().readValue(json.toString(), HashMap.class);
            HashMap<String, HashMap> coinItem = resource.get("bpi");
            for(HashMap<String, String> item : coinItem.values()) {
                String code = item.get("code");
                String rate = item.get("rate");
                String nameEng = item.get("description");
                String nameChi = switch (code) {
                    case "EUR" -> "歐元";
                    case "GBP" -> "英鎊";
                    case "USD" -> "美元";
                    default -> "";
                };
                HashMap<String, String> itemMap = new HashMap<>();
                itemMap.put("code", code);
                itemMap.put("rate", rate);
                itemMap.put("nameEng", nameEng);
                itemMap.put("nameChi", nameChi);
                result.add(itemMap);
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        return result;
    }
}
