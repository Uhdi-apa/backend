package com.uhdi_apa.hospital.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uhdi_apa.base.api.error.HospitalErrorCode;
import com.uhdi_apa.base.api.error.SymptomErrorCode;
import com.uhdi_apa.base.api.exception.RestApiException;
import com.uhdi_apa.hospital.converter.FindHospitalConverter;
import com.uhdi_apa.hospital.converter.GetHospitalDetailConverter;
import com.uhdi_apa.hospital.dto.FindHospitalDto;
import com.uhdi_apa.hospital.dto.GetHospitalDetailDto;
import com.uhdi_apa.hospital.model.Department;
import com.uhdi_apa.hospital.model.Hospital;
import com.uhdi_apa.hospital.model.HospitalDepartment;
import com.uhdi_apa.hospital.model.Symptom;
import com.uhdi_apa.hospital.model.SymptomDepartment;
import com.uhdi_apa.hospital.repository.HospitalDepartmentRepository;
import com.uhdi_apa.hospital.repository.HospitalRepository;
import com.uhdi_apa.hospital.repository.SymptomRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class HospitalService {

	private final HospitalRepository hospitalRepository;

	private final HospitalDepartmentRepository hospitalDepartmentRepository;

	private final SymptomRepository symptomRepository;

	private final GeminiService geminiService;

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

	public FindHospitalDto.Response findHospitalBySymptoms(FindHospitalDto.Parameter parameter) {
		String forFirstAidGuideLinePrompt = parameter.getPrompt()
			+ " 지금 이런 증상이 있는데 지금 당장 할 수 있는 응급조치법을 영어로 1,2,3 나눠서 간단하게 알려줘"
			+ ", 볼드체같은 강조 빼고 평문으로 해줘"
			+ ", 앞에 'Okay, here are the immediate first aid steps you should take' 같은 서론 없이 응급조치법만 알려줘";
		String firstAidGuideLine = geminiService.getContents(forFirstAidGuideLinePrompt);

		String forSymptomKeywordPrompt = parameter.getPrompt()
			+ " 지금 이런 증상이 있는데 이걸 lacerations, tetanus, incisions, cuts, bruises, abrasions, ruptures, fractures, frostbite, burns "
			+ "여기 있는 증상 중 지금 상황에 맞는 키워드를 하나 골라서 다른 대답 없이 키워드로만 대답해줘";
		String symptomKeyword = geminiService.getContents(forSymptomKeywordPrompt).trim();

		// 1. 키워드로 Symptom 조회
		Symptom symptom = symptomRepository.findByKeyword(symptomKeyword)
			.orElseThrow(() -> new RestApiException(SymptomErrorCode.NOT_EXIST_SYMPTOM));

		// 2. Symptom → SymptomDepartment → Department 추출
		List<Department> departments = symptom.getSymptomDepartments().stream()
			.map(SymptomDepartment::getDepartment)
			.distinct()
			.collect(Collectors.toList());

		// 3. Department → HospitalDepartment → Hospital 추출
		List<Hospital> hospitals = hospitalDepartmentRepository.findByDepartmentIn(departments)
			.orElseThrow(()-> new RestApiException(HospitalErrorCode.NOT_EXIST_HOSPITAL))
			.stream()
			.map(HospitalDepartment::getHospital)
			.distinct()
			.collect(Collectors.toList());

		return FindHospitalConverter.toResponse(firstAidGuideLine,hospitals,parameter.getLatitude(),parameter.getLongitude());

	}
}
