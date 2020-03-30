package ch.ubique.starsdk.ws.output;

import ch.ubique.starsdk.data.STARDataService;
import ch.ubique.starsdk.data.cache.CacheHeaderUtility;
import ch.ubique.starsdk.data.output.OutputHandler;
import ch.ubique.starsdk.data.output.OutputMetaData;
import ch.ubique.starsdk.data.output.OutputMetaDataUtility;
import ch.ubique.starsdk.data.util.DateTimeUtil;
import ch.ubique.starsdk.model.ExposedOverview;
import ch.ubique.starsdk.model.Exposee;
import ch.ubique.starsdk.ws.controller.STARController;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class Outputter {

	private static final Logger logger = LoggerFactory.getLogger(STARController.class);
	private final STARDataService dataService;
	private final List<OutputHandler> outputHandlerList;
	
	private static final int DAYS_TO_OUTPUT = 20;

	public Outputter(STARDataService dataService, List<OutputHandler> outputHandlerList) {
		this.dataService = dataService;
		this.outputHandlerList = outputHandlerList;
	}

	public void outputCurrentDay() {
		DateTime now = DateTime.now();
		
		for (int i = DAYS_TO_OUTPUT; i >= 0; i--) {
			DateTime day = now.minusDays(i);
			logger.info("Output for day: " + day);
			String outputFileName = DateTimeUtil.getDayDateNoHyphens(day) + ".json";
			logger.info("outputting exposed list: " + outputFileName);

			List<Exposee> exposedToday = dataService.getExposedForDay(day);
			logger.info("found " + exposedToday.size() + " exposees.");

			OutputMetaData outputMetaData = OutputMetaDataUtility.constructMetaData(OutputMetaDataUtility.JSON_CONTENT,
					new Date(), CacheHeaderUtility.EXPOSED_EXPIRES, CacheHeaderUtility.EXPOSED_NEXT_REFRESH);
			ExposedOverview overview = new ExposedOverview(exposedToday);
			for (OutputHandler outputHandler : outputHandlerList) {
				outputHandler.output(overview, outputFileName, outputMetaData);
			}
		}
		

		logger.info("output done");
	}
}
