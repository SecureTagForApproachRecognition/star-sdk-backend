package ch.ubique.starsdk.ws.controller;

import java.util.Base64;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.ubique.starsdk.data.STARDataService;
import ch.ubique.starsdk.model.ExposedAction;
import ch.ubique.starsdk.model.Exposee;
import ch.ubique.starsdk.model.ExposeeRequest;

@Controller
@RequestMapping("/v1")
public class STARController {

	private static final Logger logger = LoggerFactory.getLogger(STARController.class);
	private final STARDataService dataService;
	private final String appSource;

	public STARController(STARDataService dataService, String appSource) {
		this.dataService = dataService;
		this.appSource = appSource;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody String hello() {
		return "Hello from Ubique STAR SDK WS";
	}

	@RequestMapping(value = "/exposed", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> addExposee(@Valid @RequestBody ExposeeRequest exposeeRequest,
			@RequestHeader(value = "User-Agent", required = true) String userAgent) {
		if (isValidBase64(exposeeRequest.getKey())) {
			Exposee exposee = new Exposee();
			exposee.setKey(exposeeRequest.getKey());
			exposee.setUserAgent(userAgent);
			exposee.setAction(ExposedAction.ADD);
			dataService.upsertExposee(exposee, appSource);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("No valid base64 key", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/removeexposed", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> removeExposee(@Valid @RequestBody ExposeeRequest exposeeRequest,
			@RequestHeader(value = "User-Agent", required = true) String userAgent) {
		if (isValidBase64(exposeeRequest.getKey())) {
			Exposee exposee = new Exposee();
			exposee.setKey(exposeeRequest.getKey());
			exposee.setUserAgent(userAgent);
			exposee.setAction(ExposedAction.REMOVE);
			dataService.upsertExposee(exposee, appSource);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("No valid base64 key", HttpStatus.BAD_REQUEST);
		}
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
