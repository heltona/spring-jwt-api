package helton.spring.jwt.api.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import helton.spring.jwt.api.model.ApiResponse;
import helton.spring.jwt.api.model.User;
import helton.spring.jwt.api.repositories.AdministratorJpaRepository;
import helton.spring.jwt.api.utils.PasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;


@RestController
public class AdministratorController
{

	@Autowired
	private AdministratorJpaRepository rep;

	@Autowired
	private PasswordEncoder pe;

	@RequestMapping(path = "/administrador", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse createAdministrator(@RequestBody User user)
	{
		try {
			
			user.setRole("ADMIN");
			user.setPassword(pe.hashText(user.getPassword()));

			rep.createAdministrator(user);
			
			return new ApiResponse(true);
			
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		return new ApiResponse(false);
	}

	@RequestMapping(path = "/administrador", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse editeAdministrator(@RequestBody User user)
	{
		try {
			
			user.setRole("ADMIN");
			user.setPassword(pe.hashText(user.getPassword()));

			user = rep.updateAdministrator(user);
			
			return new ApiResponse(true);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		return new ApiResponse(false);
	}

	@RequestMapping(path = "/administrador/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse disableAdministrator(@PathVariable("id") long id)
	{
		try {
			
			rep.disableAdministrator(id);
			return new ApiResponse(true);
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return new ApiResponse(false);

	}

	//
	@RequestMapping(path = "/administrador/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> listUsers(@RequestParam(required = false) Integer offset,
			@RequestParam(required = false) Integer limit)
	{

		return rep.getAllActiveNotAdministratorUsers(offset, limit);

	}
}
