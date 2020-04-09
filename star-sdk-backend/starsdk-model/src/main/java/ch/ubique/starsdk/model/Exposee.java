package ch.ubique.starsdk.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.ubique.openapi.docannotations.Documentation;

public class Exposee {

	private Integer Id;

	@NotNull
	@Documentation(description = "The SecretKey of an exposed.", example = "AAAA")
	private String key;

	@NotNull
	@Documentation(description = "The onset of an exposed.", example = "2020-04-06")
	private String onset;

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

	public String getOnset() {
		return onset;
	}

	public void setOnset(String onset) {
		this.onset = onset;
	}
}
