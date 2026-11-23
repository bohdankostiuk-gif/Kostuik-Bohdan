package com.ua.gamestore.service;

import com.ua.gamestore.domain.Game;
import com.ua.gamestore.exception.GameNotFoundException;
import com.ua.gamestore.repository.GameRepository;
import com.ua.gamestore.service.mapper.GameMapper;
import com.ua.gamestore.web.dto.GameCardDto;
import com.ua.gamestore.web.dto.GameDetailDto;
import com.ua.gamestore.web.dto.GameRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    public GameServiceImpl(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    @Override
    public List<GameCardDto> findAll(String genre) {
        List<Game> games = (genre == null || genre.isBlank())
                ? gameRepository.findAll()
                : gameRepository.findByGenre(genre);
        return games.stream()
                .map(gameMapper::toCard)
                .toList();
    }

    @Override
    public GameDetailDto findById(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(id));
        return gameMapper.toDetail(game);
    }

    @Override
    public GameDetailDto create(GameRequest request) {
        Game game = gameMapper.toDomain(request);
        game.setId(null);
        return gameMapper.toDetail(gameRepository.save(game));
    }

    @Override
    public GameDetailDto update(Long id, GameRequest request) {
        gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(id));
        Game game = gameMapper.toDomain(request);
        game.setId(id);
        return gameMapper.toDetail(gameRepository.save(game));
    }

    @Override
    public void delete(Long id) {
        if (!gameRepository.deleteById(id)) {
            throw new GameNotFoundException(id);
        }
    }
}
