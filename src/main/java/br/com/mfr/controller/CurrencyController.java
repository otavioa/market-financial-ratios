package br.com.mfr.controller;

import br.com.mfr.service.CurrencyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    private final CurrencyService service;

    public CurrencyController(CurrencyService service){
        this.service = service;
    }

    @GetMapping("/{symbol:[A-Z]{3,6}}")
    public String getCurrencyBy(@PathVariable String symbol) {
        return service.getCurrencyBy(symbol);
    }

}
