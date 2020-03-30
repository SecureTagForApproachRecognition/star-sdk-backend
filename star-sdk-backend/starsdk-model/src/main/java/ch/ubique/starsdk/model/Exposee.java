package ch.ubique.starsdk.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Exposee {

	private Integer Id;

	@NotNull
	private String key;
	@JsonIgnore
	private String userAgent;

	private ExposedAction action;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public ExposedAction getAction() {
		return action;
	}

	public void setAction(ExposedAction action) {
		this.action = action;
	}
}
