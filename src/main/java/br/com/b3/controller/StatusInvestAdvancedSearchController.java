package br.com.b3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.b3.service.StatusInvestAdvanceSearchService;
import br.com.b3.service.dto.AdvanceSearchResponse;

@RestController
@RequestMapping("/statusinvest-advancesearch")
public class StatusInvestAdvancedSearchController {

	@Autowired private StatusInvestAdvanceSearchService service;
	
	@GetMapping("/acoes")
	public ResponseEntity<AdvanceSearchResponse> getAcaoInfo(){
		
		AdvanceSearchResponse test = service.test();
		
		return ResponseEntity.ok(test);
	}
	
}
