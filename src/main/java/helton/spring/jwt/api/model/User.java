package helton.spring.jwt.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "USER")
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String role;
	private String password;

	@JsonIgnore
	private int enabled = 1;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public int getEnabled()
	{
		return enabled;
	}

	public void setEnabled(int enabled)
	{
		this.enabled = enabled;
	}
	
	public String getRole()
	{
		return role;
	}

	public void setRole(String role)
	{
		this.role = role;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

}
