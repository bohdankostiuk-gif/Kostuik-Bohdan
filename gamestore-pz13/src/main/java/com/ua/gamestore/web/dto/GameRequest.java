package com.ua.gamestore.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record GameRequest(
        @NotBlank String title,
        @NotBlank String studio,
        String platform,
        @NotBlank String genre,
        String format,
        @Positive Integer releaseYear,
        @NotNull @Positive BigDecimal price,
        String posterUrl,
        String description,
        String ageRating
) {
}
