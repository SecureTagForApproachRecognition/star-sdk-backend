package ch.ubique.starsdk.model;

import javax.validation.constraints.NotNull;

public class ExposeeRequest {

	@NotNull
	private String key;
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
}
