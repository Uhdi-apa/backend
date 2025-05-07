package com.uhdi_apa.hospital.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class GeminiChatDto {
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ChatResponse {

		private List<Candidate> candidates;
		private PromptFeedback promptFeedback;

		@Getter
		@Setter
		public static class Candidate {
			private Content content;
			private String finishReason;
			private int index;
			private List<SafetyRating> safetyRatings;

		}

		@Getter @Setter
		@ToString
		public static class Content {
			private List<Parts> parts;
			private String role;

		}

		@Getter @Setter
		@ToString
		public static class Parts {
			private String text;

		}

		@Getter @Setter
		public static class SafetyRating {
			private String category;
			private String probability;
		}

		@Getter @Setter
		public static class PromptFeedback {
			private List<SafetyRating> safetyRatings;

		}
	}
	@NoArgsConstructor
	@Data
	@AllArgsConstructor
	@Builder
	public static class ChatRequest {
		private List<Content> contents;
		private GenerationConfig generationConfig;

		@Getter @Setter
		public static class Content {
			private Parts parts;
		}

		@Getter @Setter
		public static class Parts {
			private String text;

		}

		@Getter @Setter
		public static class GenerationConfig {
			private int candidate_count;
			private int max_output_tokens;
			private double temperature;

		}

		public ChatRequest(String prompt) {
			this.contents = new ArrayList<>();
			Content content = new Content();
			Parts parts = new Parts();

			parts.setText(prompt);
			content.setParts(parts);

			this.contents.add(content);
			this.generationConfig = new GenerationConfig();
			this.generationConfig.setCandidate_count(1);
			this.generationConfig.setMax_output_tokens(1000);
			this.generationConfig.setTemperature(0.7);
		}
	}
}
