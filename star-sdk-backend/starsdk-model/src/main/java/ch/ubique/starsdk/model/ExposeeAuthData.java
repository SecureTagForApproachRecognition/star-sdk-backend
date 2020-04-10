package ch.ubique.starsdk.model;

import ch.ubique.openapi.docannotations.Documentation;

public class ExposeeAuthData {

	@Documentation(description =  "Authentication data used to verify the test result (base64 encoded)", example = "TBD")
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
