package com.uhdi_apa.hospital.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

public class GetHospitalDetailDto {

	@Getter
	@Builder
	public static class Parameter {
		private Long hospitalId;
		private Double latitude;
		private Double longitude;
	}

	@Getter
	@Builder
	public static class Response {

		private Long hospitalId;

		private String hospitalName;

		private String operatingHours;

		private String address;

		private Double longitude;

		private Double latitude;

		private Double distance;

		private Boolean isEmergencyRoom;

		private List<String> specialties;

		private String phoneNumber;

		private String hospitalImage;

	}

	@Getter
	@Builder
	public static class Location {

		private Double latitude;

		private Double longitude;

	}
}
