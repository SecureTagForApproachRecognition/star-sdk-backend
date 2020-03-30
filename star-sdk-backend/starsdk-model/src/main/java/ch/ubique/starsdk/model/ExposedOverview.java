package ch.ubique.starsdk.model;

import java.util.ArrayList;
import java.util.List;

public class ExposedOverview {
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
