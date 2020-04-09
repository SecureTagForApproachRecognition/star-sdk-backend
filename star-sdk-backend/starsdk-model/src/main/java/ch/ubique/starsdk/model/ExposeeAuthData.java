package ch.ubique.starsdk.model;

import ch.ubique.openapi.docannotations.Documentation;

public class ExposeeAuthData {

	@Documentation(description =  "Authentication data used to verify the test result")
	private String value;
	@Documentation(description = "Specify the method, which should be used to verify data")
	private AuthDataMethod method;

	public AuthDataMethod getMethod() {
		return method;
	}

	public void setMethod(AuthDataMethod method) {
		this.method = method;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
