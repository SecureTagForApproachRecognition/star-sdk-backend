package ch.ubique.starsdk.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ch.ubique.openapi.docannotations.Documentation;

public class ExposeeRequest {

	@NotNull
	@Documentation(description = "The SecretKey used to generate EphID")
	private String key;

	@NotNull
	@Size(max = 10)
	@Documentation(description = "The onset date of the secret key. Format: yyyy-MM-dd")
	private String onset;

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

	public String getOnset() {
		return onset;
	}

	public void setOnset(String onset) {
		this.onset = onset;
	}
}
