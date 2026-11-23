package com.ua.gamestore.web.dto;

import java.math.BigDecimal;

public record GameDetailDto(
        Long id,
        String title,
        String studio,
        String platform,
        String genre,
        Integer releaseYear,
        BigDecimal price,
        String posterUrl,
        String description,
        String ageRating
) {
}
