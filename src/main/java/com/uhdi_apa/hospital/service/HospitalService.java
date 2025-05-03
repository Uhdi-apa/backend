package com.uhdi_apa.hospital.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uhdi_apa.base.api.error.HospitalErrorCode;
import com.uhdi_apa.base.api.exception.RestApiException;
import com.uhdi_apa.hospital.converter.GetHospitalDetailConverter;
import com.uhdi_apa.hospital.dto.GetHospitalDetailDto;
import com.uhdi_apa.hospital.model.Hospital;
import com.uhdi_apa.hospital.repository.HospitalRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class HospitalService {

	private final HospitalRepository hospitalRepository;

	public GetHospitalDetailDto.Response getHospitalDetail(GetHospitalDetailDto.Parameter parameter) {

		Hospital hospital = hospitalRepository.findById(parameter.getHospitalId())
			.orElseThrow(() -> new RestApiException(HospitalErrorCode.NOT_EXIST_HOSPITAL));

		Double distance = calculateDistance(parameter.getLatitude(), parameter.getLongitude(),
			hospital.getLatitude(), hospital.getLongitude());

		return GetHospitalDetailConverter.toResponse(hospital, distance);

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
