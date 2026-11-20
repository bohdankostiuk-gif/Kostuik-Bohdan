package com.ua.gamestore.service.mapper;

import com.ua.gamestore.domain.Game;
import com.ua.gamestore.web.dto.GameCardDto;
import com.ua.gamestore.web.dto.GameDetailDto;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public GameCardDto toCard(Game game) {
        return new GameCardDto(
                game.getId(),
                game.getTitle(),
                game.getPlatform(),
                game.getPrice(),
                game.getPosterUrl()
        );
    }

    public GameDetailDto toDetail(Game game) {
        return new GameDetailDto(
                game.getId(),
                game.getTitle(),
                game.getStudio(),
                game.getPlatform(),
                game.getGenre(),
                game.getReleaseYear(),
                game.getPrice(),
                game.getPosterUrl(),
                game.getDescription(),
                game.getAgeRating()
        );
    }
}
