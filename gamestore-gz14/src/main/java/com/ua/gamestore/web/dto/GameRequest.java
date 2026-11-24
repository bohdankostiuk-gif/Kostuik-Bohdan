package com.ua.gamestore.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record GameRequest(
        @NotBlank(message = "назва обов'язкова")
        String title,

        @NotBlank(message = "студія обов'язкова")
        String studio,

        String platform,

        @NotBlank(message = "жанр обов'язковий")
        String genre,

        String format,

        @Positive(message = "рік випуску має бути додатним")
        Integer releaseYear,

        @NotNull(message = "ціна обов'язкова")
        @Positive(message = "ціна має бути додатною")
        BigDecimal price,

        String posterUrl,
        String description,
        String ageRating
) {
}
