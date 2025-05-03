package com.uhdi_apa.hospital.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uhdi_apa.base.api.ApiResponse;
import com.uhdi_apa.hospital.converter.GetHospitalDetailConverter;
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

	@Operation(summary = "병원 조회 API", description = "해당 id의 병원을 조회합니다")
	@PostMapping("/details")
	public ApiResponse<GetHospitalDetailDto.Response> getCandidateMatchVote(
		@RequestParam("hospital-id") Long hospitalId,
		@RequestParam("longitude") Double longitude,
		@RequestParam("latitude") Double latitude
	) {
		GetHospitalDetailDto.Parameter parameter =
			GetHospitalDetailConverter.toParameter(hospitalId, longitude, latitude);

		GetHospitalDetailDto.Response response =
			hospitalService.getHospitalDetail(parameter);

		return ApiResponse.createSuccess(response);
	}
}
