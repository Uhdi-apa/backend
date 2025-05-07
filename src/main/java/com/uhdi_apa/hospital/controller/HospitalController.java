package com.uhdi_apa.hospital.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uhdi_apa.base.api.ApiResponse;
import com.uhdi_apa.hospital.converter.FindHospitalConverter;
import com.uhdi_apa.hospital.converter.GetHospitalDetailConverter;
import com.uhdi_apa.hospital.dto.FindHospitalDto;
import com.uhdi_apa.hospital.dto.GetHospitalDetailDto;
import com.uhdi_apa.hospital.service.HospitalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Tag(name = "Hospital", description = "병원 관련 api")
@RestController
@RequestMapping("/hospitals")
public class HospitalController {

	private final HospitalService hospitalService;

	@Operation(summary = "병원 세부정보 조회 API", description = "해당 id의 병원을 조회합니다")
	@GetMapping("/details")
	public ApiResponse<GetHospitalDetailDto.Response> getCandidateMatchVote(
		@RequestParam("hospital-id") Long hospitalId,
		@RequestParam("latitude") Double latitude,
		@RequestParam("longitude") Double longitude
	) {
		GetHospitalDetailDto.Parameter parameter =
			GetHospitalDetailConverter.toParameter(hospitalId, latitude, longitude);

		GetHospitalDetailDto.Response response =
			hospitalService.getHospitalDetail(parameter);

		return ApiResponse.createSuccess(response);
	}

	@Operation(summary = "증상설명을 통한 병원 선정 api", description = "사용자가 증상을 설명하면 그에 맞는 병원을 찾아준다")
	@PostMapping("recommend/by-symptoms")
	public ApiResponse<FindHospitalDto.Response> findHospitalBySymptoms(
		@RequestBody FindHospitalDto.Request request
	) {
		FindHospitalDto.Parameter parameter =
			FindHospitalConverter.toParameter(request);

		FindHospitalDto.Response response =
		hospitalService.findHospitalBySymptoms(parameter);

		return ApiResponse.createSuccess(response);
	}

}
