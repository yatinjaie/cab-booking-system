package org.codejudge.sb.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codejudge.sb.config.CustomException;
import org.codejudge.sb.dao.DriverDao;
import org.codejudge.sb.model.Driver;
import org.codejudge.sb.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

	@Autowired
	private DriverDao driverDao;

	public Driver saveDriver(Driver driver) throws CustomException {
		checkValidPhoneNumber(driver.getPhoneNumber());
		return driverDao.saveDriver(driver);
	}

	public void checkValidPhoneNumber(Long phoneNumber) throws CustomException {
		String s = String.valueOf(phoneNumber);
		Pattern p = Pattern.compile("[0-9]{10}");
		Matcher m = p.matcher(s);
		if (!(m.find() && m.group().equals(s))) {
			throw new CustomException("Phone number should be 10 digits.");
		}
	}

	public Boolean updateDriverLocation(String id, Location location) {
		return driverDao.updateDriverLocation(id, location);
	}

	public List<Driver> getAvailableCars(Location location) {
		return driverDao.findAvailableCabs(location);
	}

}
