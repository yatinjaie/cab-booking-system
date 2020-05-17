package org.codejudge.sb.dao;

import java.util.List;

import org.codejudge.sb.model.Driver;
import org.codejudge.sb.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

@Repository
public class DriverDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * @param driver
	 * @return
	 */
	public Driver saveDriver(Driver driver) {
		return mongoTemplate.insert(driver);
	}

	/**
	 * @param id
	 * @param location
	 * @return
	 */
	public boolean updateDriverLocation(String id, Location location) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		final Update update = new Update();
		//		Double[] arr = new Double[] { location.getLatitude(), location.getLongitude() };
		update.set("location", location);
		final UpdateResult updateRes = mongoTemplate.updateFirst(query, update, Driver.class);
		return updateRes.wasAcknowledged();
	}

	/**
	 * @param location
	 * @return
	 */
	public List<Driver> findAvailableCabs(Location location) {
		Point point = new Point(location.getLatitude(), location.getLongitude());
		Distance distance = new Distance(4, Metrics.KILOMETERS);
		Circle circle = new Circle(point, distance);
		Criteria geoCriteria = Criteria.where("location").withinSphere(circle);
		Query query = Query.query(geoCriteria);
		query.fields().include("name");
		query.fields().include("phoneNumber");
		query.fields().include("carNumber");
		return mongoTemplate.find(query, Driver.class);
	}

	public List<Driver> findAll() {
		return mongoTemplate.findAll(Driver.class);
	}

}
