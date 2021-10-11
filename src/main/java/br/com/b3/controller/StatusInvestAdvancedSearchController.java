package br.com.b3.controller;

import static java.util.Arrays.asList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.b3.service.StatusInvestAdvancedSearchService;
import br.com.b3.service.dto.AdvanceSearchResponse;
import br.com.b3.service.dto.CompanyResponse;

@RestController
@RequestMapping("/statusinvest-advancedsearch")
public class StatusInvestAdvancedSearchController {

	@Autowired private StatusInvestAdvancedSearchService service;
	
	@GetMapping("/acoes/all")
	public ResponseEntity<AdvanceSearchResponse> getAllAcoes(){
		
		AdvanceSearchResponse acoes = service.getAllAcoes();
		
		return ResponseEntity.ok(acoes);
	}
	
	@GetMapping("/acoes/{ticket}")
	public ResponseEntity<CompanyResponse> getAcaoInfo(@PathVariable(value="ticket", required=true) String ticker){
		
		CompanyResponse acao = service.getAcaoByTicker(ticker);
		
		return ResponseEntity.ok(acao);
	}
	
	@GetMapping("/acoes")
	public ResponseEntity<AdvanceSearchResponse> getAcoesByTickers(
			@RequestParam(value="tickers", required=true) String[] tickers,
			@RequestParam(value="indicadores", required=false) String[] indicadores){
		
		AdvanceSearchResponse acoes = service.getAcaoByTicker(asList(tickers));
		
		return ResponseEntity.ok(acoes);
		
	}
	
}
