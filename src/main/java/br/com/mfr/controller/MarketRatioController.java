package br.com.mfr.controller;

import br.com.mfr.controller.dto.CompaniesResponse;
import br.com.mfr.entity.Company;
import br.com.mfr.service.MarketRatioService;
import br.com.mfr.service.SimpleMarketRatioService;
import br.com.mfr.service.ticket.TickerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.mfr.controller.dto.CompaniesConverter.convert;

@RestController
@RequestMapping("/market-ratio")
public class MarketRatioController {

	@Autowired private MarketRatioService service;
	@Autowired private SimpleMarketRatioService simpleService;

	@GetMapping("/all")
	public ResponseEntity<CompaniesResponse> getAllAvailable() {

		List<Company> companies = service.getAllCompanies();

		return ResponseEntity.ok(convert(companies));
	}

	@GetMapping
	public ResponseEntity<CompaniesResponse> getAllAvailableBy(
			@RequestParam(value = "tickers", required = false) String[] tickers,
			@RequestParam(value = "types", required = false) String[] types,
			@RequestParam(value = "ratios", required = false) String[] ratios) {

		List<Company> companies = service.getAllCompaniesBy(tickers, types);

		return ResponseEntity.ok(convert(companies, ratios));
	}
	
	@GetMapping("/etfs/{ticket}")
	public ResponseEntity<TickerResponse> getEtfTicketInfo(@PathVariable(value="ticket", required=true) String ticket){
		
		TickerResponse ticketInfo = simpleService.getEtfInfo(ticket);
		
		return ResponseEntity.ok(ticketInfo);
	}

}
