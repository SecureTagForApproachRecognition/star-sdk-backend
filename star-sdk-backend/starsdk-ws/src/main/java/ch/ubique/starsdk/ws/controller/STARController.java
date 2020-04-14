package ch.ubique.starsdk.ws.controller;

import java.time.Duration;
import java.util.Base64;
import java.util.List;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import ch.ubique.openapi.docannotations.Documentation;
import ch.ubique.starsdk.data.EtagGenerator;
import ch.ubique.starsdk.data.EtagGeneratorInterface;
import ch.ubique.starsdk.data.STARDataService;
import ch.ubique.starsdk.model.ExposedOverview;
import ch.ubique.starsdk.model.Exposee;
import ch.ubique.starsdk.model.ExposeeAuthData;
import ch.ubique.starsdk.model.ExposeeRequest;

@Controller
@RequestMapping("/v1")
public class STARController {

	private final STARDataService dataService;
	private final EtagGeneratorInterface etagGenerator;
	private final String appSource;

	private static final DateTimeFormatter DAY_DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd")
			.withZone(DateTimeZone.UTC);

	private static final Logger logger = LoggerFactory.getLogger(STARController.class);

	public STARController(STARDataService dataService, EtagGeneratorInterface etagGenerator, String appSource) {
		this.dataService = dataService;
		this.appSource = appSource;
		this.etagGenerator = etagGenerator;
	}

	@CrossOrigin(origins = {
		"https://editor.swagger.io"
	})
	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody String hello() {
		return "Hello from STAR SDK WS";
	}

	@CrossOrigin(origins = {
		"https://editor.swagger.io"
	})
	@RequestMapping(value = "/exposed", method = RequestMethod.POST)
	@Documentation(
		description = "Enpoint used to publish the SecretKey.",
		responses = {
		"200 => Returns OK if successful",
		"400 => Key is not base64 encoded"
	})
	public @ResponseBody ResponseEntity<String> addExposee(@Valid @RequestBody @Documentation(description = "The ExposeeRequest contains the SecretKey from the guessed infection date, the infection date itself, and some authentication data to verify the test result") ExposeeRequest exposeeRequest,
			@RequestHeader(value = "User-Agent", required = true) @Documentation(description = "App Identifier (PackageName/BundleIdentifier) + App-Version + OS (Android/iOS) + OS-Version", example = "ch.ubique.android.starsdk;1.0;iOS;13.3") String userAgent) {
		if (isValidBase64(exposeeRequest.getKey())) {
			
			Exposee exposee = new Exposee();
			exposee.setKey(exposeeRequest.getKey());
			exposee.setOnset(exposeeRequest.getOnset());
			dataService.upsertExposee(exposee, appSource);
			return ResponseEntity.ok().build();
			
		} else {
			return new ResponseEntity<String>("No valid base64 key", HttpStatus.BAD_REQUEST);
		}
	}

	@Documentation(
		responses = {
			"200 => Returns ExposedOverview, which includes all secretkeys which were published on _dayDateStr_.",
			"400 => If dayDateStr has the wrong format"
		}
	)

	@CrossOrigin(origins = {
		"https://editor.swagger.io"
	})
	@RequestMapping(value = "/exposed/{dayDateStr}")
	public @ResponseBody ResponseEntity<ExposedOverview> getExposed(@PathVariable
	 @Documentation(description = "The date for which we want to get the SecretKey.", example = "2019-01-31") 
	 	String dayDateStr, WebRequest request) {
		DateTime dayDate = DAY_DATE_FORMATTER.parseDateTime(dayDateStr);
		List<Exposee> exposeeList = dataService.getExposedForDay(dayDate);
		int max = exposeeList.stream().map(e -> e.getId()).max(Integer::compareTo).get();
		ExposedOverview overview = new ExposedOverview(exposeeList);
		String etag = etagGenerator.getEtag(max);
		if (request.checkNotModified(etag)) {
			return null;
		}
		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(Duration.ofMinutes(5))).body(overview);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<?> invalidArguments() {
		return ResponseEntity.badRequest().build();
	}

	private boolean isValidBase64(String value) {
		try {
			Base64.getDecoder().decode(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
