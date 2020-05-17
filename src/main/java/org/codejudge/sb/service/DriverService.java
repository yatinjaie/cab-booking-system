package org.codejudge.sb.service;

import java.util.ArrayList;
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

	/**
	 * @param driver
	 * @return
	 * @throws CustomException
	 */
	public Driver saveDriver(Driver driver) throws CustomException {
		checkValidPhoneNumber(driver.getPhoneNumber());
		return driverDao.saveDriver(driver);
	}

	/**
	 * @param phoneNumber
	 * @throws CustomException
	 */
	public void checkValidPhoneNumber(Long phoneNumber) throws CustomException {
		String s = String.valueOf(phoneNumber);
		Pattern p = Pattern.compile("[0-9]{10}");
		Matcher m = p.matcher(s);
		if (!(m.find() && m.group().equals(s))) {
			throw new CustomException("Phone number should be 10 digits.");
		}
	}

	/**
	 * @param id
	 * @param location
	 * @return
	 */
	public Boolean updateDriverLocation(String id, Location location) {
		return driverDao.updateDriverLocation(id, location);
	}

	/**
	 * @param location
	 * @return
	 */
	//	public List<Driver> getAvailableCars(Location location) {
	//		return driverDao.findAvailableCabs(location);
	//	}

	public List<Driver> getAvailableCars(Location location) {
		List<Driver> list = driverDao.findAll();
		List<Driver> availableList = new ArrayList<>();
		for (Driver driver : list) {
			Location driverLoc = driver.getLocation();
			if(driverLoc!=null) {
				double distance = haversine(driverLoc.getLatitude(), driverLoc.getLatitude(), location.getLatitude(),
						location.getLongitude());
				System.out.println("distance : " + distance);
				if (distance <= 4d) {
					availableList.add(driver);
				}
			}
		}
		return availableList;
	}

	static double haversine(double lat1, double lon1, double lat2, double lon2) {
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);

		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
		double rad = 6371;
		double c = 2 * Math.asin(Math.sqrt(a));
		return rad * c;
	}

}
