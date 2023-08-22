package br.com.b3.controller;

import br.com.b3.service.datacharge.DataChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    private DataChargeService service;

    @GetMapping("/charge")
    public String doCharge() {

        service.processCharging();

        return "ok";
    }
}
