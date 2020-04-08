package ch.ubique.starsdk.model;

import javax.validation.constraints.NotNull;

public class ExposeeRequest {

	@NotNull
	private String key;

	@NotNull
	private ExposeeAuthData authData;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public ExposeeAuthData getAuthData() {
		return authData;
	}

	public void setAuthData(ExposeeAuthData authData) {
		this.authData = authData;
	}
}
