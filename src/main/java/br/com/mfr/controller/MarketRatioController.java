package br.com.mfr.controller;

import br.com.mfr.controller.dto.CompaniesResponse;
import br.com.mfr.entity.Company;
import br.com.mfr.service.MarketRatioService;
import br.com.mfr.service.SimpleMarketRatioService;
import br.com.mfr.service.ticket.TickerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.mfr.controller.dto.CompaniesConverter.convert;

@RestController
@RequestMapping("/market-ratio")
public class MarketRatioController {

	private final MarketRatioService service;
	private final SimpleMarketRatioService simpleService;

    public MarketRatioController(MarketRatioService service, SimpleMarketRatioService simpleService) {
		this.simpleService = simpleService;
		this.service = service;
	}

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

		//TODO - Extract filter directly from DB
		List<Company> companies = service.getAllCompaniesBy(tickers, types);

		return ResponseEntity.ok(convert(companies, ratios));
	}
	
	@GetMapping("/etfs/{ticket}")
	public ResponseEntity<TickerResponse> getEtfTicketInfo(@PathVariable(value="ticket", required=true) String ticket){
		
		TickerResponse ticketInfo = simpleService.getEtfInfo(ticket);
		
		return ResponseEntity.ok(ticketInfo);
	}

}
