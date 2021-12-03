package helton.spring.jwt.api.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder
{
	private String salt = "persone connait mon salt";
	
	public String hashText(String plainText) throws NoSuchAlgorithmException
	{
		String textToHash = plainText + salt;
		
		MessageDigest hasher = MessageDigest.getInstance("SHA256");
		byte[] bHash = hasher.digest(textToHash.getBytes());

		String hash = new String(Base64.getEncoder().encode(bHash));
		
		
		return hash;
	}
}
