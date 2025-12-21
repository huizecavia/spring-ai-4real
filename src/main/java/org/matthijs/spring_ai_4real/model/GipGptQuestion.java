package org.matthijs.spring_ai_4real.model;

import jakarta.validation.constraints.NotBlank;

public record GipGptQuestion(
        @NotBlank(message = "Onderwerp is required") String onderwerp,
        @NotBlank(message = "Question is required") String question
) {
}
