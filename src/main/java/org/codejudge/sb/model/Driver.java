package org.codejudge.sb.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Document(collection = "driver")
@Data
public class Driver {

	@Id
	@Field("id")
	private String id;

	@NotNull
	@Field("name")
	private String name;

	@Indexed(unique = true)
	@NotNull
	@Field(value = "email")
	private String email;

	@Field(value = "phone_number")
	@JsonProperty("phone_number")
	@NotNull
	@Indexed(unique = true)
	private Long phoneNumber;

	@Field(value = "license_number")
	@JsonProperty("license_number")
	@NotNull
	@Indexed(unique = true)
	private String licenseNumber;

	@Field(value = "car_number")
	@JsonProperty("car_number")
	@NotNull
	@Indexed(unique = true)
	private String carNumber;

	@Field(value = "location")
	private Location location;

}
