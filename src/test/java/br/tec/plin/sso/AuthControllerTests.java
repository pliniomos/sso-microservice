package br.tec.plin.sso;

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

import br.tec.plin.sso.dto.UserAuthDTO;

public class AuthControllerTests extends AbstractTest {

	private String signinUri = "/signin";
	private String validateUri = "/validate";

	//Token expirado
	private String expiredToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aS5wbGluaW9AZ21haWwuY29tIiwiZXhwIjoxNTU3MDQ2NTExfQ.6rz8kC3ZreOzfS6EBiz29HoWMGVXlcdgERUBV4A6GtdbN7AI3VOUrgCIGvkXf19nD83jN1zBVF2JuQsLMhWj_A";
	private String invalidToken = "Bearer 12wqbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aS5wbGluaW9AZ21haWwuY29tIiwiZXhwIjoxNTU3MDQ2NTExfQ.6rz8kC3ZreOzfS6EBiz29HoWMGVXlcdgERUBV4A6GtdbN7AI3VOUrgCIGvkXf19nD83jN1zBVF2JuQsLMhWj_A";
	private String validToken = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJxdyIsImV4cCI6MTczNDcyMjY1NDl9.6-gMpSEuDaIOwrfBqZh9-6RcuX3EOz0a8zh-BDN3RntW3AMBvukzgGQ_uOmKGYfscYgFuUgTH9xTKeJqryuMug";
	private String emptyToken = "";

	private ClientAndServer mockResourceAccountServers;

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void validateAuthorization_withErros() throws Exception {

		//Expired Token
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(validateUri).contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", expiredToken)).andReturn();
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());

		//Invalid Token
		mvcResult = mvc.perform(MockMvcRequestBuilders.post(validateUri).contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", invalidToken)).andReturn();
		Assert.assertEquals(HttpStatus.UNAUTHORIZED.value(), mvcResult.getResponse().getStatus());

		//Empty Token
		mvcResult = mvc.perform(MockMvcRequestBuilders.post(validateUri).contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", emptyToken)).andReturn();
		Assert.assertEquals(HttpStatus.UNAUTHORIZED.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	public void validateAuthorization_withSucess() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(validateUri).contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", validToken)).andReturn();
		Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
	}

	@Test
	public void signIn_withError() throws Exception {

		mockResourceAccountServers = ClientAndServer.startClientAndServer(8090);
		mockResourceAccountServers.when(
				HttpRequest.request()
					.withMethod("POST")
					.withPath("/availability")
					.withBody("{\"login\":\"login\",\"password\":\"password\"}"))
		.respond(HttpResponse.response().withStatusCode(200));

		UserAuthDTO userAuthDTO = new UserAuthDTO();
		userAuthDTO.setLogin("login");
		userAuthDTO.setPassword("password");

		String inputJson = super.mapToJson(userAuthDTO);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(signinUri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
		Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
		mockResourceAccountServers.close();
	}

	@Test
	public void signIn_withSuccess() throws Exception {

		mockResourceAccountServers = ClientAndServer.startClientAndServer(8090);
		mockResourceAccountServers.when(
				HttpRequest.request()
					.withMethod("POST")
					.withPath("/availability")
					.withBody("{\"login\":\"login\",\"password\":\"password\"}"))
		.respond(HttpResponse.response().withStatusCode(200));

		UserAuthDTO userAuthDTO = new UserAuthDTO();
		userAuthDTO.setLogin("login");
		userAuthDTO.setPassword("password");

		String inputJson = super.mapToJson(userAuthDTO);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(signinUri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
		Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
		mockResourceAccountServers.close();
	}

}