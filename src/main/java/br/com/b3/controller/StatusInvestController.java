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

import br.com.b3.controller.dto.AdvancedSearchDTO;
import br.com.b3.controller.dto.CompanyDTO;
import br.com.b3.service.StatusInvestAdvancedSearchService;
import br.com.b3.service.StatusInvestService;
import br.com.b3.service.dto.AdvanceSearchResponse;
import br.com.b3.service.dto.CompanyResponse;
import br.com.b3.service.ticket.TicketResponse;

@RestController
@RequestMapping("/statusinvest")
public class StatusInvestAdvancedSearchController {

	@Autowired
	private StatusInvestAdvancedSearchService service;
	
	@Autowired
	private StatusInvestService simpleService;

	@GetMapping("/all")
	public ResponseEntity<AdvancedSearchDTO> getAllAvailable() {

		AdvanceSearchResponse acoes = service.getAllAvailable();

		return ResponseEntity.ok(convert(acoes));
	}

	@GetMapping
	public ResponseEntity<AdvancedSearchDTO> getAllAvailableByTickers(
			@RequestParam(value = "tickers", required = true) String[] tickers,
			@RequestParam(value = "indicadores", required = false) String[] indicadores) {

		AdvanceSearchResponse response = service.getAllAvailable(asList(tickers));

		return ResponseEntity.ok(convert(response, indicadores));

	}

	@GetMapping("/acoes/all")
	public ResponseEntity<AdvancedSearchDTO> getAllAcoes() {

		AdvanceSearchResponse acoes = service.getAllAcoes();

		return ResponseEntity.ok(convert(acoes));
	}

	@GetMapping("/acoes/{ticket}")
	public ResponseEntity<CompanyDTO> getAcaoInfo(
			@PathVariable(value = "ticket", required = true) String ticker) {

		AdvanceSearchResponse response = service.getAcaoByTickers(ticker);

		CompanyResponse company = response.stream().findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Ticker informado inválido!"));

		return ResponseEntity.ok(convert(company));
	}

	@GetMapping("/acoes")
	public ResponseEntity<AdvancedSearchDTO> getAcoesByTickers(
			@RequestParam(value = "tickers", required = true) String[] tickers,
			@RequestParam(value = "indicadores", required = false) String[] indicadores) {

		AdvanceSearchResponse acoes = service.getAcaoByTickers(tickers);

		return ResponseEntity.ok(convert(acoes, indicadores));
	}

//---------

	@GetMapping("/fiis/all")
	public ResponseEntity<AdvancedSearchDTO> getAllFiis() {

		AdvanceSearchResponse fiis = service.getAllFiis();

		return ResponseEntity.ok(convert(fiis));
	}

	@GetMapping("/fiis/{ticket}")
	public ResponseEntity<CompanyDTO> getFiiInfo(
			@PathVariable(value = "ticket", required = true) String ticker) {

		AdvanceSearchResponse response = service.getFiiByTicker(ticker);

		CompanyResponse company = response.stream().findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Ticker informado inválido!"));

		return ResponseEntity.ok(convert(company));
	}

	@GetMapping("/fiis")
	public ResponseEntity<AdvancedSearchDTO> getFiisByTickers(
			@RequestParam(value = "tickers", required = true) String[] tickers,
			@RequestParam(value = "indicadores", required = false) String[] indicadores) {

		AdvanceSearchResponse fiis = service.getFiiByTicker(tickers);

		return ResponseEntity.ok(convert(fiis, indicadores));
	}

//---------

	@GetMapping("/stocks/all")
	public ResponseEntity<AdvancedSearchDTO> getAllStocks() {

		AdvanceSearchResponse stocks = service.getAllStocks();

		return ResponseEntity.ok(convert(stocks));
	}

	@GetMapping("/stocks/{ticket}")
	public ResponseEntity<CompanyDTO> getStocksInfo(
			@PathVariable(value = "ticket", required = true) String ticker) {

		AdvanceSearchResponse response = service.getStockByTickers(ticker);

		CompanyResponse company = response.stream().findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Ticker informado inválido!"));

		return ResponseEntity.ok(convert(company));
	}

	@GetMapping("/stocks")
	public ResponseEntity<AdvancedSearchDTO> getStocksByTickers(
			@RequestParam(value = "tickers", required = true) String[] tickers,
			@RequestParam(value = "indicadores", required = false) String[] indicadores) {

		AdvanceSearchResponse stocks = service.getStockByTickers(tickers);

		return ResponseEntity.ok(convert(stocks, indicadores));
	}

//---------

	@GetMapping("/reits/all")
	public ResponseEntity<AdvancedSearchDTO> getAllReits() {

		AdvanceSearchResponse reits = service.getAllReits();

		return ResponseEntity.ok(convert(reits));
	}

	@GetMapping("/reits/{ticket}")
	public ResponseEntity<CompanyDTO> getReitsInfo(
			@PathVariable(value = "ticket", required = true) String ticker) {

		AdvanceSearchResponse response = service.getReitByTickers(ticker);

		CompanyResponse company = response.stream().findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Ticker informado inválido!"));

		return ResponseEntity.ok(convert(company));
	}

	@GetMapping("/reits")
	public ResponseEntity<AdvancedSearchDTO> getReitsByTickers(
			@RequestParam(value = "tickers", required = true) String[] tickers,
			@RequestParam(value = "indicadores", required = false) String[] indicadores) {

		AdvanceSearchResponse reits = service.getReitByTickers(tickers);

		return ResponseEntity.ok(convert(reits, indicadores));
	}
	
	//---------
	
	@GetMapping("/etfs/{ticket}")
	public ResponseEntity<TicketResponse> getEtfTicketInfo(@PathVariable(value="ticket", required=true) String ticket){
		
		TicketResponse ticketInfo = simpleService.getEtfInfo(ticket);
		
		return ResponseEntity.ok(ticketInfo);
	}
}
