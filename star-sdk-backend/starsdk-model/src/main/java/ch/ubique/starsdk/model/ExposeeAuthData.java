package ch.ubique.starsdk.model;

public class ExposeeAuthData {

	private String value;
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
