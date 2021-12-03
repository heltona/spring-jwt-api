package helton.spring.jwt.api.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import helton.spring.jwt.api.model.User;
import helton.spring.jwt.api.repositories.AdministratorJpaRepository;
import helton.spring.jwt.api.utils.PasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@RestController
public class AdministratorController
{

	@Autowired
	private AdministratorJpaRepository rep;

	@Autowired
	private PasswordEncoder pe;

	@RequestMapping(path = "/administrador", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String createAdministrator(@RequestBody User user)
	{
		try {
			
			user.setRole("ADMIN");
			user.setPassword(pe.hashText(user.getPassword()));

			rep.createAdministrator(user);
			
			return "{\"success\": true}";
			
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		return "{\"success\": false}";
	}

	@RequestMapping(path = "/administrador", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String editeAdministrator(@RequestBody User user)
	{
		try {
			
			user.setRole("ADMIN");
			user.setPassword(pe.hashText(user.getPassword()));

			user = rep.updateAdministrator(user);
			
			return "{\"success\": true}";
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		return "{\"success\": false}";
	}

	@RequestMapping(path = "/administrador/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public User disableAdministrator(@PathVariable("id") long id)
	{
		return rep.disableAdministrator(id);

	}

	//
	@RequestMapping(path = "/administrador/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> listUsers(@RequestParam(required = false) Integer offset,
			@RequestParam(required = false) Integer limit)
	{

		return rep.getAllActiveNotAdministratorUsers(offset, limit);

	}
}
