package ch.ubique.starsdk.ws.controller;

import java.util.Base64;
import java.util.List;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.ubique.openapi.docannotations.Documentation;
import ch.ubique.starsdk.data.STARDataService;
import ch.ubique.starsdk.model.ExposedOverview;
import ch.ubique.starsdk.model.Exposee;
import ch.ubique.starsdk.model.ExposeeAuthData;
import ch.ubique.starsdk.model.ExposeeRequest;

@Controller
@RequestMapping("/v1")
public class STARController {

	private final STARDataService dataService;
	private final String appSource;

	private static final DateTimeFormatter DAY_DATE_FORMATTER = DateTimeFormat.forPattern("yyyyMMdd")
			.withZone(DateTimeZone.UTC);

	private static final Logger logger = LoggerFactory.getLogger(STARController.class);

	public STARController(STARDataService dataService, String appSource) {
		this.dataService = dataService;
		this.appSource = appSource;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody String hello() {
		return "Hello from STAR SDK WS";
	}

	
	@RequestMapping(value = "/exposed", method = RequestMethod.POST)
	@Documentation(
		description = "Enpoint used to publish the secretkey.",
		responses = {
		"200 => Returns OK if successfull",
		"400 => Either key is not base64 or no auth data given"
	})
	public @ResponseBody ResponseEntity<String> addExposee(@Valid @RequestBody ExposeeRequest exposeeRequest,
			@RequestHeader(value = "User-Agent", required = true) String userAgent) {
		if (isValidBase64(exposeeRequest.getKey())) {
			if (isValiExposeeRequestAuth(exposeeRequest.getAuthData())) {
				Exposee exposee = new Exposee();
				exposee.setKey(exposeeRequest.getKey());
				dataService.upsertExposee(exposee, appSource);
				return ResponseEntity.ok().build();
			} else {
				return new ResponseEntity<String>("No valid auth data", HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<String>("No valid base64 key", HttpStatus.BAD_REQUEST);
		}
	}

	@Documentation(
		responses = {
			"200 => Returns ExposedOverview, which includes all secretkeys which were published on dayDateStr.",
			"500 => If the date time string is not parseable."
		}
	)
	@RequestMapping(value = "/exposed/{dayDateStr}")
	public @ResponseBody ResponseEntity<ExposedOverview> getExposed(@PathVariable String dayDateStr) {
		DateTime dayDate = DAY_DATE_FORMATTER.parseDateTime(dayDateStr);
		List<Exposee> exposeeList = dataService.getExposedForDay(dayDate);
		ExposedOverview overview = new ExposedOverview(exposeeList);
		return ResponseEntity.ok(overview);
	}

	private boolean isValidBase64(String value) {
		try {
			Base64.getDecoder().decode(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean isValiExposeeRequestAuth(ExposeeAuthData authData) {
		switch (authData.getMethod()) {
		case NONE:
			return true;
		case REDEEM_CODE:
			return dataService.validateRedeemCode(authData.getValue());
		default:
			return false;
		}
	}
}
