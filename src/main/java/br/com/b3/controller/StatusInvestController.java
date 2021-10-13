package br.com.b3.controller;

import static java.util.Arrays.asList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.b3.service.StatusInvestService;
import br.com.b3.service.ticket.TicketResponse;

//@RestController
//@RequestMapping("/statusinvest")
public class StatusInvestController {

	@Autowired private StatusInvestService service;
	
	@GetMapping("/acoes/{ticket}")
	public ResponseEntity<TicketResponse> getAcaoInfo(@PathVariable(value="ticket", required=true) String ticket){
		
		TicketResponse acao = service.getAcaoInfo(ticket);
		
		return ResponseEntity.ok(acao);
	}
	
	@GetMapping("/acoes/{ticket}/{indicador}")
	public ResponseEntity<TicketResponse> getAcaoInfo(
			@PathVariable(value="ticket", required=true) String ticket, 
			@PathVariable(value="indicador", required=true) String indicador){
		
		TicketResponse indicadorDaAcao = service.getAcaoInfo(ticket, indicador);
		
		return ResponseEntity.ok(indicadorDaAcao);
	}
	
	@GetMapping("/fundos-imobiliarios/{ticket}")
	public ResponseEntity<TicketResponse> getFiiInfo(@PathVariable(value="ticket", required=true) String ticket){
		
		TicketResponse acao = service.getFiiInfo(ticket);
		
		return ResponseEntity.ok(acao);
	}
	
	@GetMapping("/fundos-imobiliarios/{ticket}/{indicador}")
	public ResponseEntity<TicketResponse> getFiiInfo(
			@PathVariable(value="ticket", required=true) String ticket, 
			@PathVariable(value="indicador", required=true) String indicador){
		
		TicketResponse indicadorDaAcao = service.getFiiInfo(ticket, indicador);
		
		return ResponseEntity.ok(indicadorDaAcao);
	}
	
	@GetMapping("/etfs/{ticket}")
	public ResponseEntity<TicketResponse> getEtfTicketInfo(@PathVariable(value="ticket", required=true) String ticket){
		
		TicketResponse ticketInfo = service.getEtfInfo(ticket);
		
		return ResponseEntity.ok(ticketInfo);
	}
	
	@GetMapping
	public ResponseEntity<List<TicketResponse>> getAllTickersInfo(@RequestParam(value="tickers", required=true) String[] tickers){
		
		List<TicketResponse> allTickers = service.getAllTickersInfo(asList(tickers));
		
		return ResponseEntity.ok(allTickers);
	}
	
}
