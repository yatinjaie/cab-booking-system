package org.codejudge.sb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.codejudge.sb.config.CustomException;
import org.codejudge.sb.model.Driver;
import org.codejudge.sb.model.Location;
import org.codejudge.sb.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AppController {

	@Autowired
	private DriverService driverService;

	@ApiOperation("This is the hello world api")
	@GetMapping("/")
	public String hello() {
		return "Hello World!!";
	}

	/**
	 *
	 * @param add
	 * @return
	 * @throws CustomException
	 */
	@ApiOperation("This is an api to register driver")
	@PostMapping("/api/v1/driver/register/")
	public ResponseEntity<Driver> add(@RequestBody @Valid Driver driver) throws CustomException {
		return new ResponseEntity<>(driverService.saveDriver(driver), HttpStatus.CREATED);
	}

	/**
	 *
	 * @param driver
	 * @return
	 */
	@ApiOperation("This is an api to update driver location")
	@PostMapping("/api/v1/driver/{id}/sendLocation/")
	public ResponseEntity<Boolean> updateDriverLocation(@PathVariable String id,
			@RequestBody @Valid Location location) {
		return new ResponseEntity<>(driverService.updateDriverLocation(id, location), HttpStatus.ACCEPTED);
	}

	/**
	 *
	 * @param id
	 * @param coordinates
	 * @return
	 */
	@ApiOperation("This is an api to gert available cars")
	@PostMapping("/api/v1/passenger/available_cabs/")
	public ResponseEntity<Object> add(@RequestBody @Valid Location location) {
		log.info("location : " + location);

		List<Driver> list = driverService.getAvailableCars(location);
		Map<String,Object> map = new HashMap<>();
		if(list.isEmpty())
			map.put("message", "No cabs available!");
		else
			map.put("available_cabs", list);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

}
