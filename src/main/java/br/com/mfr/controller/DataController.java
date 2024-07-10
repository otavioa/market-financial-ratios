package br.com.mfr.controller;

import br.com.mfr.service.DataChargeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DataController {

    private final DataChargeService service;

    public DataController(DataChargeService service) {
        this.service = service;
    }

    @GetMapping("/populate")
    public String doCharge() {

        service.processCharging();

        return "ok";
    }
}
