package com.ua.gamestore.service.mapper;

import com.ua.gamestore.domain.Game;
import com.ua.gamestore.web.dto.GameCardDto;
import com.ua.gamestore.web.dto.GameDetailDto;
import com.ua.gamestore.web.dto.GameRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class GameMapperTest {

    private final GameMapper mapper = new GameMapper();

    private Game sampleGame() {
        return new Game(
                1L,
                "Hades",
                "Supergiant Games",
                "PC / Switch",
                "Roguelike",
                "Digital",
                2020,
                new BigDecimal("515.00"),
                "/posters/hades.jpg",
                "Опис гри",
                "12+"
        );
    }

    @Test
    void toCard_mapsCardFields() {
        GameCardDto card = mapper.toCard(sampleGame());

        assertThat(card.id()).isEqualTo(1L);
        assertThat(card.title()).isEqualTo("Hades");
        assertThat(card.platform()).isEqualTo("PC / Switch");
        assertThat(card.price()).isEqualByComparingTo("515.00");
        assertThat(card.posterUrl()).isEqualTo("/posters/hades.jpg");
    }

    @Test
    void toDetail_mapsAllFields() {
        GameDetailDto detail = mapper.toDetail(sampleGame());

        assertThat(detail.id()).isEqualTo(1L);
        assertThat(detail.studio()).isEqualTo("Supergiant Games");
        assertThat(detail.genre()).isEqualTo("Roguelike");
        assertThat(detail.format()).isEqualTo("Digital");
        assertThat(detail.releaseYear()).isEqualTo(2020);
        assertThat(detail.description()).isEqualTo("Опис гри");
        assertThat(detail.ageRating()).isEqualTo("12+");
    }

    @Test
    void toDomain_mapsRequestWithoutId() {
        GameRequest request = new GameRequest(
                "Hades",
                "Supergiant Games",
                "PC / Switch",
                "Roguelike",
                "Digital",
                2020,
                new BigDecimal("515.00"),
                "/posters/hades.jpg",
                "Опис гри",
                "12+"
        );

        Game game = mapper.toDomain(request);

        assertThat(game.getId()).isNull();
        assertThat(game.getTitle()).isEqualTo("Hades");
        assertThat(game.getStudio()).isEqualTo("Supergiant Games");
        assertThat(game.getFormat()).isEqualTo("Digital");
        assertThat(game.getPrice()).isEqualByComparingTo("515.00");
    }
}
