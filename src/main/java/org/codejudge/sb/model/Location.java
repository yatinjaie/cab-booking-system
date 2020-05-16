package org.codejudge.sb.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Location {

	@NotNull
	private Double latitude;

	@NotNull
	private Double longitude;

}
