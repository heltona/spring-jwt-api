package helton.spring.jwt.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;

@WebMvcTest(AdministratorController.class)
public class AdministratorControllerTest
{
	@Autowired
	private MockMvc mock;

	@Test
	public void testCreateAdministratorSuccess() throws Exception
	{
		String payload = "{\"name\":\"joel\", \"password\": \"iddqd\"}";
	}
}
