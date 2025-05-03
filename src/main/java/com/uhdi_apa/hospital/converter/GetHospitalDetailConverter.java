package com.uhdi_apa.hospital.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.uhdi_apa.hospital.dto.GetHospitalDetailDto;
import com.uhdi_apa.hospital.model.Hospital;

public class GetHospitalDetailConverter {
	public static GetHospitalDetailDto.Parameter toParameter(Long hospitalId, Double latitude, Double longitude) {
		return GetHospitalDetailDto.Parameter.builder()
			.hospitalId(hospitalId)
			.latitude(latitude)
			.longitude(longitude)
			.build();
	}

	public static GetHospitalDetailDto.Response toResponse(Hospital hospital, Double distance) {
		List<String> specialties = hospital.getHospitalDepartments().stream()
			.map(hospitalDepartment -> hospitalDepartment.getDepartment().getName())
			.collect(Collectors.toList());

		return GetHospitalDetailDto.Response.builder()
			.address(hospital.getAddress())
			.hospitalImage(hospital.getImgUrl())
			.hospitalName(hospital.getName())
			.isEmergencyRoom(hospital.getIsEmergencyRoom())
			.hospitalId(hospital.getId())
			.latitude(hospital.getLatitude())
			.longitude(hospital.getLongitude())
			.operatingHours(hospital.getOperatingHour())
			.distance(distance)
			.phoneNumber(hospital.getPhoneNumber())
			.specialties(specialties)
			.build();

	}
}
