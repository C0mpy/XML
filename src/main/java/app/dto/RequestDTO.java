package app.dto;

// DTO for all requests containing a single String parameter
public class RequestDTO {
	
	private String request;

	public RequestDTO() {
		super();
	}

	public RequestDTO(String request) {
		super();
		this.request = request;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	@Override
	public String toString() {
		return "RequestDTO [request=" + request + "]";
	}

}
