package br.com.evaluation.car.rent.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.evaluation.car.rent.domain.SESEmail;
import br.com.evaluation.car.rent.main.App;
@RunWith(SpringRunner.class)
@SpringBootTest(classes= App.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("default")
public class AwsFeaturesTests {
	
	
	private static final String BASE_URL = "/rent/";
	
	@Autowired
    private MockMvc mvc;
	
	private ObjectMapper mapper = new ObjectMapper();
	/**
	 * Test field email missing
	 * @throws Exception
	 */
	@Test
	public void test1FieldEmailToMissing() throws Exception {
		SESEmail sesEmail = getSESEmail();
		sesEmail.setEmail(null);
	    String requestJson = getRequestJson(sesEmail);
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		mvc.perform(post(BASE_URL + "sendEmail").contentType(MediaType.APPLICATION_JSON)
		        .content(requestJson))
				.andExpect(status().isBadRequest());
	}
	
	/**
	 * Test field content missing
	 * @throws Exception
	 */
	@Test
	public void test1FieldContentMissing() throws Exception {
		SESEmail sesEmail = getSESEmail();
		sesEmail.setContent(null);
	    String requestJson = getRequestJson(sesEmail);
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		mvc.perform(post(BASE_URL + "sendEmail").contentType(MediaType.APPLICATION_JSON)
		        .content(requestJson))
				.andExpect(status().isBadRequest());
	}
	
	/**
	 * Test field subject missing
	 * @throws Exception
	 */
	@Test
	public void test1FieldSubjectMissing() throws Exception {
		SESEmail sesEmail = getSESEmail();
		sesEmail.setSubject(null);
	    String requestJson = getRequestJson(sesEmail);
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		mvc.perform(post(BASE_URL + "sendEmail").contentType(MediaType.APPLICATION_JSON)
		        .content(requestJson))
				.andExpect(status().isBadRequest());
	}
	
	/**
	 * Test field subject missing
	 * @throws Exception
	 */
	@Test
	public void test2SendEmail() throws Exception {
		SESEmail sesEmail = getSESEmail();
	    String requestJson = getRequestJson(sesEmail);
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		mvc.perform(post(BASE_URL + "sendEmail").contentType(MediaType.APPLICATION_JSON)
		        .content(requestJson))
				.andExpect(status().isOk());
	}
	
	/**
	 * Transform object in JSON
	 * @param sesEmail
	 * @return
	 * @throws JsonProcessingException
	 */
	private String getRequestJson(SESEmail sesEmail) throws JsonProcessingException {
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson =ow.writeValueAsString(sesEmail);
		return requestJson;
	}
	
	/**
	 * Load SESEmail object
	 * @return
	 */
	private SESEmail getSESEmail() {
		SESEmail sesEmail = new SESEmail();
		sesEmail.setEmail("gabrielsaantos02@gmail.com");
		sesEmail.setContent("Test email");
		sesEmail.setSubject("AWS SES TEST");
		return sesEmail;
	}
}
