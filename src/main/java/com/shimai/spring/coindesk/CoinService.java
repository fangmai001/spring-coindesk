package com.shimai.spring.coindesk;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class CoinService {
    CoinService() {}

    public ArrayList<HashMap<String, String>> fetchCoinDesk(){
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
