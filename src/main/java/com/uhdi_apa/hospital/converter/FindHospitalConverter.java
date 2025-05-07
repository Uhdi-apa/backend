package com.uhdi_apa.hospital.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.uhdi_apa.hospital.dto.FindHospitalDto;
import com.uhdi_apa.hospital.model.Hospital;

public class FindHospitalConverter {
	public static FindHospitalDto.Parameter toParameter(FindHospitalDto.Request request){
		return FindHospitalDto.Parameter.builder()
			.prompt(request.getPrompt())
			.latitude(request.getLatitude())
			.longitude(request.getLongitude())
			.build();
	}

	public static FindHospitalDto.Response toResponse(String firstAidGuideLine, List<Hospital> hospitals,Double myLatitude,Double myLongitude){
		List<FindHospitalDto.MatchedHospitals> matchedHospitals =
		hospitals.stream()
			.map(hospital -> FindHospitalDto.MatchedHospitals.builder()
				.hospitalId(hospital.getId())
				.hospitalName(hospital.getName())
				.latitude(hospital.getLatitude())
				.longitude(hospital.getLongitude())
				.distance(calculateDistance(hospital.getLatitude(), hospital.getLongitude(), myLatitude, myLongitude))
				.phoneNumber(hospital.getPhoneNumber())
				.operatingHour(hospital.getOperatingHour())
				.isEmergency(hospital.getIsEmergencyRoom())
				.build())
			.collect(Collectors.toList());

		return FindHospitalDto.Response.builder()
			.hospitals(matchedHospitals)
			.firstAidGuideLine(firstAidGuideLine)
			.build();
	}

	public static Double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
		Double latDistance = Math.toRadians(lat2 - lat1);
		Double lonDistance = Math.toRadians(lon2 - lon1);

		Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
			+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
			* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return 6371.0 * c;
	}
}
