package ch.ubique.starsdk.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Exposee {

	private Integer Id;

	@NotNull
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@JsonIgnore
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}
}
