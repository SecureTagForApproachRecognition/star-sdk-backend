package ch.ubique.starsdk.model;

import ch.ubique.openapi.docannotations.Documentation;

public class ExposeeAuthData {

	@Documentation(description =  "Authentication data used to verify the test result", example = "TBD")
	private String value;
	@Documentation(description = "Specify the method, which should be used to verify data. Probably unused since, if multiple different methods used, we could trace back from which health institute the result came from.", example = "TBD")
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
