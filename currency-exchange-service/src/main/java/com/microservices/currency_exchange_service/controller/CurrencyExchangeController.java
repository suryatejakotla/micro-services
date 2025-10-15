package com.microservices.currency_exchange_service.controller;

import com.microservices.currency_exchange_service.model.CurrencyExchange;
import com.microservices.currency_exchange_service.repo.CurrencyExchangeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
    private final Environment environment;

    public CurrencyExchangeController(Environment environment) {
        this.environment = environment;
    }
    @Autowired
    private CurrencyExchangeRepo repository;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retriveExchangeValue(@PathVariable String from, @PathVariable String to) {
//        CurrencyExchange currencyExchange =     new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(65),"");
          CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
          if(currencyExchange == null) {
                throw new RuntimeException("Unable to find data for " + from + " to " + to);
          }
        String port=environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);
        return currencyExchange;

    }
}
