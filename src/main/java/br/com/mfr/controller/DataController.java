package br.com.mfr.controller;

import br.com.mfr.service.DataChargeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
@AllArgsConstructor
public class DataController {

    private DataChargeService service;

    @GetMapping("/charge")
    public String doCharge() {

        service.processCharging();

        return "ok";
    }
}
