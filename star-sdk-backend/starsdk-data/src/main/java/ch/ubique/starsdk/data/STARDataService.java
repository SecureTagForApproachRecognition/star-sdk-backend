package ch.ubique.starsdk.data;

import org.joda.time.DateTime;

import ch.ubique.starsdk.model.Exposee;

import java.util.List;

public interface STARDataService {

	/**
	 * upserts the given exposee
	 * @param exposee
	 * @param appSource
	 */
	void upsertExposee(Exposee exposee, String appSource);

	/**
	 * returns all exposees for the given day [day: 00:00, day+1: 00:00)
	 * @param day
	 * @return
	 */
	List<Exposee> getExposedForDay(DateTime day);
}
