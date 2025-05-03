package com.uhdi_apa.base.api.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HospitalErrorCode implements ErrorCode {
	NOT_EXIST_HOSPITAL(HttpStatus.NOT_FOUND, "해당 병원은 존재하지 않습니다");

	private final HttpStatus httpStatus;
	private final String message;
}
