package com.pitang.desafio.mv;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.pitang.desafio.mv.dto.UserAuthDTO;

public class AuthControllerTests extends AbstractTest {

	private String signinUri = "/signin";
	private String validateUri = "/validate";
	
	//Token expirado
	private String expiredToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aS5wbGluaW9AZ21haWwuY29tIiwiZXhwIjoxNTU3MDQ2NTExfQ.6rz8kC3ZreOzfS6EBiz29HoWMGVXlcdgERUBV4A6GtdbN7AI3VOUrgCIGvkXf19nD83jN1zBVF2JuQsLMhWj_A";
	private String invalidToken = "Bearer 12wqbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aS5wbGluaW9AZ21haWwuY29tIiwiZXhwIjoxNTU3MDQ2NTExfQ.6rz8kC3ZreOzfS6EBiz29HoWMGVXlcdgERUBV4A6GtdbN7AI3VOUrgCIGvkXf19nD83jN1zBVF2JuQsLMhWj_A";
	private String validToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJxdyIsImV4cCI6MTczNDcyMjY1NDl9.6-gMpSEuDaIOwrfBqZh9-6RcuX3EOz0a8zh-BDN3RntW3AMBvukzgGQ_uOmKGYfscYgFuUgTH9xTKeJqryuMug";
	private String emptyToken = "";
	
	private ClientAndServer mockServerSuccess;
	private ClientAndServer mockServerError;

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void validateAuthorization_withErros() throws Exception {

		//Expired Token
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(validateUri).contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", expiredToken)).andReturn();
		Assert.assertThat(mvcResult.getResponse().getErrorMessage(), CoreMatchers.containsString("Unauthorized - Invalid session"));
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
		
		//Invalid Token
		mvcResult = mvc.perform(MockMvcRequestBuilders.post(validateUri).contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", invalidToken)).andReturn();
		Assert.assertThat(mvcResult.getResponse().getErrorMessage(), CoreMatchers.containsString("Unauthorized - Invalid token"));
		Assert.assertEquals(HttpStatus.UNAUTHORIZED.value(), mvcResult.getResponse().getStatus());
		
		//Empty Token
		mvcResult = mvc.perform(MockMvcRequestBuilders.post(validateUri).contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", emptyToken)).andReturn();
		Assert.assertThat(mvcResult.getResponse().getErrorMessage(), CoreMatchers.containsString("Unauthorized"));
		Assert.assertEquals(HttpStatus.UNAUTHORIZED.value(), mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void validateAuthorization_withSucess() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(validateUri).contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", validToken)).andReturn();
		Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}
	
	@Test
	public void signIn_withSuccess() throws Exception {

		mockServerSuccess = ClientAndServer.startClientAndServer(8090);
		mockServerSuccess.when(
				HttpRequest.request().withMethod("POST"))
		.respond(HttpResponse.response().withStatusCode(200));

		UserAuthDTO userAuthDTO = new UserAuthDTO();
		userAuthDTO.setLogin("login");
		userAuthDTO.setLogin("senha");

		String inputJson = super.mapToJson(userAuthDTO);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(signinUri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
		Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
		mockServerSuccess.close();
	}

}