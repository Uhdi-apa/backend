package com.uhdi_apa.hospital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.uhdi_apa.hospital.dto.GeminiChatDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GeminiService {

	@Qualifier("geminiRestTemplate")
	private final RestTemplate restTemplate;

	@Value("${gemini.api.url}")
	private String apiUrl;

	@Value("${gemini.api.key}")
	private String geminiApiKey;

	public String getContents(String prompt) {
		String requestUrl = apiUrl + "?key=" + geminiApiKey;

		GeminiChatDto.ChatRequest request = new GeminiChatDto.ChatRequest(prompt);
		GeminiChatDto.ChatResponse response = restTemplate.postForObject(
			requestUrl, request, GeminiChatDto.ChatResponse.class
		);

		String message = response.getCandidates().get(0).getContent().getParts().get(0).getText().toString();
		return message;
	}
}
