package com.ua.gamestore.repository;

import com.ua.gamestore.domain.Game;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryGameRepository implements GameRepository {

    private final Map<Long, Game> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public List<Game> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Game> findByGenre(String genre) {
        return storage.values()
                .stream()
                .filter(game -> genre.equalsIgnoreCase(game.getGenre()))
                .toList();
    }

    @Override
    public List<Game> findByFormat(String format) {
        return storage.values()
                .stream()
                .filter(game -> format.equalsIgnoreCase(game.getFormat()))
                .toList();
    }

    @Override
    public Optional<Game> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Game save(Game game) {
        if (game.getId() == null) {
            game.setId(idGenerator.incrementAndGet());
        }
        storage.put(game.getId(), game);
        return game;
    }

    @Override
    public boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }

    @Override
    public void deleteAll() {
        storage.clear();
        idGenerator.set(0);
    }
}
