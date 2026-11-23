package com.ua.gamestore.service.mapper;

import com.ua.gamestore.domain.Game;
import com.ua.gamestore.web.dto.GameCardDto;
import com.ua.gamestore.web.dto.GameDetailDto;
import com.ua.gamestore.web.dto.GameRequest;
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

    public Game toDomain(GameRequest request) {
        Game game = new Game();
        game.setTitle(request.title());
        game.setStudio(request.studio());
        game.setPlatform(request.platform());
        game.setGenre(request.genre());
        game.setReleaseYear(request.releaseYear());
        game.setPrice(request.price());
        game.setPosterUrl(request.posterUrl());
        game.setDescription(request.description());
        game.setAgeRating(request.ageRating());
        return game;
    }
}
