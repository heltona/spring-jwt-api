package helton.spring.jwt.api.model;

public class ApiResponse
{
	boolean success;

	public ApiResponse(boolean success)
	{
		this.success = success;
	}

	public boolean isSuccess()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}
	
	
	
	
}
