package br.com.mfr.controller;

import br.com.mfr.controller.dto.AdvancedSearchDTO;
import br.com.mfr.entity.Company;
import br.com.mfr.service.StatusInvestAdvancedSearchService;
import br.com.mfr.service.StatusInvestService;
import br.com.mfr.service.ticket.TickerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.mfr.controller.dto.AdvanceSearchConverter.convert;

@RestController
@RequestMapping("/statusinvest")
public class StatusInvestController {

	@Autowired private StatusInvestAdvancedSearchService service;
	@Autowired private StatusInvestService simpleService;

	@GetMapping("/all")
	public ResponseEntity<AdvancedSearchDTO> getAllAvailable() {

		List<Company> companies = service.getAllCompanies();

		return ResponseEntity.ok(convert(companies));
	}

	@GetMapping
	public ResponseEntity<AdvancedSearchDTO> getAllAvailableBy(
			@RequestParam(value = "tickers", required = false) String[] tickers,
			@RequestParam(value = "types", required = false) String[] types,
			@RequestParam(value = "indicators", required = false) String[] indicators) {

		List<Company> response = service.getAllCompaniesBy(tickers, types);

		return ResponseEntity.ok(convert(response, indicators));
	}
	
	@GetMapping("/etfs/{ticket}")
	public ResponseEntity<TickerResponse> getEtfTicketInfo(@PathVariable(value="ticket", required=true) String ticket){
		
		TickerResponse ticketInfo = simpleService.getEtfInfo(ticket);
		
		return ResponseEntity.ok(ticketInfo);
	}

}
