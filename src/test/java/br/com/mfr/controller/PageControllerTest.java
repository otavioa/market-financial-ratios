package br.com.mfr.controller;

import br.com.mfr.MockMvcApp;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcApp
class PageControllerTest {

	private MockMvc mvc;

	public PageControllerTest(MockMvc mvc){
        this.mvc = mvc;
    }

	@Test
	void homePage() throws Exception {
		mvc.perform(get("/"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("MFR Application")));
	}

}
