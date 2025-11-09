package org.matthijs.spring_ai_4real.dto;

//import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotBlank;

public record UserInput(@NotBlank String prompt,
                        String context
//                        ,ChatOptions chatOptions
) {
}
