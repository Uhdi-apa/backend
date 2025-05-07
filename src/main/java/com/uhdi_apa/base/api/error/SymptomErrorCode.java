package com.uhdi_apa.base.api.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SymptomErrorCode implements ErrorCode {
	NOT_EXIST_SYMPTOM(HttpStatus.NOT_FOUND, "해당 키워드에 해당하는 증상이 없습니다");

	private final HttpStatus httpStatus;
	private final String message;
}
