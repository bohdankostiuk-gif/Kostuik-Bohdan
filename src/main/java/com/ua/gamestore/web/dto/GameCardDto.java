package com.ua.gamestore.web.dto;

import java.math.BigDecimal;

public record GameCardDto(
        Long id,
        String title,
        String platform,
        BigDecimal price,
        String posterUrl
) {
}
