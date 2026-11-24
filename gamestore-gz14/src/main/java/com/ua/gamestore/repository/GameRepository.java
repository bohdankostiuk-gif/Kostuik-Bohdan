package com.ua.gamestore.repository;

import com.ua.gamestore.domain.Game;

import java.util.List;
import java.util.Optional;

public interface GameRepository {

    List<Game> findAll();

    List<Game> findByGenre(String genre);

    List<Game> findByFormat(String format);

    Optional<Game> findById(Long id);

    Game save(Game game);

    boolean deleteById(Long id);

    void deleteAll();
}
