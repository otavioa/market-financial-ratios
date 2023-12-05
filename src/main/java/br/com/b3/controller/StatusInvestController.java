package br.com.b3.controller;

import br.com.b3.controller.dto.AdvancedSearchDTO;
import br.com.b3.entity.Company;
import br.com.b3.service.StatusInvestAdvancedSearchService;
import br.com.b3.service.StatusInvestService;
import br.com.b3.service.ticket.TickerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.b3.controller.dto.AdvanceSearchConverter.convert;
import static org.apache.commons.lang3.ArrayUtils.isEmpty;

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
