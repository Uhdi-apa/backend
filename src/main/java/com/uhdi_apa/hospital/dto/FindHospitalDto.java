package com.uhdi_apa.hospital.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

public class FindHospitalDto {
	@Getter
	@Builder
	public static class Request {
		private String prompt;
		private Double latitude;
		private Double longitude;
	}

	@Getter
	@Builder
	public static class Parameter {
		private String prompt;
		private Double latitude;
		private Double longitude;
	}

	@Getter
	@Builder
	public static class Response {
		private List<MatchedHospitals> hospitals;

		private String firstAidGuideLine;
	}
	@Getter
	@Builder
	public static class MatchedHospitals {
		private Long hospitalId;

		private String hospitalName;

		private Double latitude;

		private Double longitude;

		private Double distance;

		private String phoneNumber;

		private String operatingHour;

		private Boolean isEmergency;

	}
}
