package ch.ubique.starsdk.model;

import java.util.ArrayList;
import java.util.List;

import ch.ubique.openapi.docannotations.Documentation;

public class ExposedOverview {
	@Documentation(description = "A list of all SecretKeys")
	List<Exposee> exposed = new ArrayList<>();

	public ExposedOverview() {
	}

	public ExposedOverview(List<Exposee> exposed) {
		this.exposed = exposed;
	}

	public List<Exposee> getExposed() {
		return exposed;
	}

	public void setExposed(List<Exposee> exposed) {
		this.exposed = exposed;
	}
}
