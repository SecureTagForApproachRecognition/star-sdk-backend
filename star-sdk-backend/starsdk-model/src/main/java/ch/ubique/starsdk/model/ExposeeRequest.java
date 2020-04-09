package ch.ubique.starsdk.model;

import javax.validation.constraints.NotNull;

import ch.ubique.openapi.docannotations.Documentation;

public class ExposeeRequest {

	@NotNull
	@Documentation(description = "The SecretKey used to generate EphID")
	private String key;

	@NotNull
	@Documentation(description = "AuthenticationData provided by the health institutes to verify the test results")
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
