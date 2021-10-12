package br.com.b3.controller;

import static br.com.b3.controller.dto.AdvanceSearchConverter.convert;
import static java.util.Arrays.asList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.b3.controller.dto.AdvanceSearchConverter;
import br.com.b3.controller.dto.AdvancedSearchDTO;
import br.com.b3.controller.dto.CompanyDTO;
import br.com.b3.service.StatusInvestAdvancedSearchService;
import br.com.b3.service.dto.AdvanceSearchResponse;
import br.com.b3.service.dto.CompanyResponse;

@RestController
@RequestMapping("/statusinvest-advancedsearch")
public class StatusInvestAdvancedSearchController {

	@Autowired private StatusInvestAdvancedSearchService service;
	
	@GetMapping("/all")
	public ResponseEntity<AdvancedSearchDTO> getAllAvailable(){
		
		AdvanceSearchResponse acoes = service.getAllAvailable();
		
		return ResponseEntity.ok(convert(acoes));
		
	}
	
	@GetMapping("")
	public ResponseEntity<AdvancedSearchDTO> getAllAvailableByTickers(
			@RequestParam(value="tickers", required=true) String[] tickers,
			@RequestParam(value="indicadores", required=false) String[] indicadores){
		
		AdvanceSearchResponse acoes = service.getAllAvailable(asList(tickers));
		
		return ResponseEntity.ok(AdvanceSearchConverter.convert(acoes, asList(indicadores)));
		
	}
	
	@GetMapping("/acoes/all")
	public ResponseEntity<AdvancedSearchDTO> getAllAcoes(){
		
		AdvanceSearchResponse acoes = service.getTodasAcoes();
		
		return ResponseEntity.ok(convert(acoes));
	}
	
	@GetMapping("/acoes/{ticket}")
	public ResponseEntity<CompanyDTO> getAcaoInfo(@PathVariable(value="ticket", required=true) String ticker){
		
		CompanyResponse acao = service.getAcaoByTicker(ticker);
		
		return ResponseEntity.ok(convert(acao));
	}
	
	@GetMapping("/acoes")
	public ResponseEntity<AdvancedSearchDTO> getAcoesByTickers(
			@RequestParam(value="tickers", required=true) String[] tickers,
			@RequestParam(value="indicadores", required=false) String[] indicadores){
		
		AdvanceSearchResponse acoes = service.getAcaoByTickers(asList(tickers));
		
		return ResponseEntity.ok(convert(acoes));
		
	}
	
}
