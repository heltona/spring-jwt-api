package helton.spring.jwt.api.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import helton.spring.jwt.api.model.User;
import helton.spring.jwt.api.repositories.UserRepository;
import helton.spring.jwt.api.utils.Authenticator;
import helton.spring.jwt.api.utils.PasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController
{

	@Autowired
	private UserRepository rep;

	@Autowired
	private Authenticator auth;

	@Autowired
	private PasswordEncoder pe;

	@RequestMapping(path = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String createUser(@RequestBody User user)
	{
		try {
			
			user.setRole("DEFAULT");
			user.setPassword(pe.hashText(user.getPassword()));
			
			user = rep.createUser(user);
			
			return "{\"success\": true}";
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return "{\"success\": false}";

	}

	@RequestMapping(path = "/user", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String editeUser(@RequestBody User user)
	{
		try {
			
			user.setRole("DEFAULT");
			user.setPassword(pe.hashText(user.getPassword()));

			rep.updateUser(user);
			
			return "{\"success\": false}";
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "{\"success\": false}";
	}

	@RequestMapping(path = "/user/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String disableUser(@PathVariable("id") long id)
	{
		try {
			rep.disableUser(id);
			return "{\"success\": true}";
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return "{\"success\": false}";
		
	}

	@RequestMapping(path = "/auth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String authenticate(@RequestBody User user, HttpServletResponse resp)
	{
		try {

			String token = auth.authenticate(user);

			resp.addHeader("Authorization", "Bearer " + token);

			return "{\"success\": true}";

		} catch (Exception ex) {
			ex.printStackTrace();
			return "{\"success\": false}";
		}
	}

}
