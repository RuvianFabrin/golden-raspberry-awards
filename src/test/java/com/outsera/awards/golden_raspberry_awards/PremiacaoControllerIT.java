package com.outsera.awards.golden_raspberry_awards;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PremiacaoControllerIT {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void retornarIntervaloEsperadoParaOCsvImportado() throws Exception {
		mockMvc.perform(get("/api/awards/intervals").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.min", hasSize(1))).andExpect(jsonPath("$.min[0].producer").value("Joel Silver"))
				.andExpect(jsonPath("$.min[0].interval").value(1))
				.andExpect(jsonPath("$.min[0].previousWin").value(1990))
				.andExpect(jsonPath("$.min[0].followingWin").value(1991)).andExpect(jsonPath("$.max", hasSize(1)))
				.andExpect(jsonPath("$.max[0].producer").value("Matthew Vaughn"))
				.andExpect(jsonPath("$.max[0].interval").value(13))
				.andExpect(jsonPath("$.max[0].previousWin").value(2002))
				.andExpect(jsonPath("$.max[0].followingWin").value(2015));
	}

	@Test
	void naoDeveTerIntervaloNegativoOuZero() throws Exception {
		mockMvc.perform(get("/api/awards/intervals")).andExpect(status().isOk()).andExpect(jsonPath("$.min").isArray())
				.andExpect(jsonPath("$.max").isArray())
				.andExpect(jsonPath("$.min[*].interval", everyItem(greaterThanOrEqualTo(1))))
				.andExpect(jsonPath("$.max[*].interval", everyItem(greaterThanOrEqualTo(1))));
	}

	@Test
	void deveRespeitarSchemaBasico() throws Exception {
		mockMvc.perform(get("/api/awards/intervals")).andExpect(status().isOk()).andExpect(jsonPath("$.min").exists())
				.andExpect(jsonPath("$.max").exists()).andExpect(jsonPath("$.min[0].producer").isString())
				.andExpect(jsonPath("$.min[0].interval").isNumber())
				.andExpect(jsonPath("$.min[0].previousWin").isNumber())
				.andExpect(jsonPath("$.min[0].followingWin").isNumber());
	}

	@Test
	void listasDevemConterItens() throws Exception {
		mockMvc.perform(get("/api/awards/intervals")).andExpect(status().isOk())
				.andExpect(jsonPath("$.min", hasSize(greaterThan(0))))
				.andExpect(jsonPath("$.max", hasSize(greaterThan(0))));
	}

	@Test
	void contentTypeDeveSerJson() throws Exception {
		mockMvc.perform(get("/api/awards/intervals")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	void caminhoErradoDeveRetornar404() throws Exception {
		mockMvc.perform(get("/api/awards/interval")).andExpect(status().isNotFound());
	}

}
