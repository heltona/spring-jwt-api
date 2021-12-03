package helton.spring.jwt.api.utils;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import helton.spring.jwt.api.model.User;
import helton.spring.jwt.api.repositories.UserRepository;

@Component
public class Authenticator
{
	private String secret = "je parie que vous ne savez pas mon secret";

	@Autowired
	private UserRepository rep;

	@Autowired
	private PasswordEncoder pe;

	public String authenticate(User user) throws NotAuthenticatedException
	{
		try {
			User rUser = rep.getUserById(user.getId());
			String hashedPassword = pe.hashText(user.getPassword());

			if (rUser.getId() == user.getId() && rUser.getPassword().equals(hashedPassword)) {
				return getJwtToken(rUser);
			}
			
		} catch (Exception ex) {

		}

		throw new NotAuthenticatedException();
	}

	private String getJwtToken(User user)
	{

		String token = JWT.create().withClaim("id", user.getId()).withClaim("name", user.getName())
				.withClaim("enabled", user.getEnabled()).withClaim("role", user.getRole()).sign(getAlgorithm());

		return token;

	}

	private Algorithm getAlgorithm()
	{
		return Algorithm.HMAC256(secret);
	}

	public boolean isAdmin(String token)
	{
		String role = getClaims(token).getClaim("role").asString();
		return role.equals("ADMIN");
	}

	public boolean isDefault(String token)
	{
		String role = getClaims(token).getClaim("role").asString();
		return role.equals("DEFAULT");
	}

	private DecodedJWT getClaims(String token)
	{
		JWTVerifier verifier = JWT.require(getAlgorithm()).build();
		DecodedJWT jwt = verifier.verify(token);

		return jwt;
	}

}
