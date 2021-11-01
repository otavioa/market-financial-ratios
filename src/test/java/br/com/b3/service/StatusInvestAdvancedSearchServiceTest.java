package br.com.b3.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.b3.service.dto.AdvanceSearchResponse;

@ExtendWith(MockitoExtension.class)
class StatusInvestAdvancedSearchServiceTest {

	@InjectMocks
	private StatusInvestAdvancedSearchService subject;
	
	@Test
	void getAllAcoes() {
		
		AdvanceSearchResponse allAcoes = subject.getAllAcoes();
	}

}
