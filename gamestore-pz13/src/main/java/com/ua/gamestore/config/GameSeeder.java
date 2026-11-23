package com.ua.gamestore.config;

import com.ua.gamestore.domain.Game;
import com.ua.gamestore.repository.GameRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class GameSeeder implements ApplicationRunner {

    private final GameRepository gameRepository;

    public GameSeeder(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        gameRepository.save(new Game(
                null,
                "S.T.A.L.K.E.R. 2: Heart of Chornobyl",
                "GSC Game World",
                "PC / Xbox Series X",
                "Шутер",
                "Digital",
                2024,
                new BigDecimal("1399.00"),
                "/posters/stalker2.jpg",
                "Шутер від першої особи у відкритому світі Чорнобильської зони відчуження.",
                "18+"
        ));

        gameRepository.save(new Game(
                null,
                "Metro Exodus",
                "4A Games",
                "PC / PS5 / Xbox",
                "Шутер",
                "Disc",
                2019,
                new BigDecimal("699.00"),
                "/posters/metro-exodus.jpg",
                "Сюжетний шутер про подорож поїздом Аврора через постапокаліптичні землі.",
                "18+"
        ));

        gameRepository.save(new Game(
                null,
                "The Witcher 3: Wild Hunt",
                "CD Projekt Red",
                "PC / PS5 / Switch",
                "RPG",
                "Disc",
                2015,
                new BigDecimal("729.00"),
                "/posters/witcher3.jpg",
                "Рольова гра про відьмака Геральта з відкритим світом та нелінійними квестами.",
                "18+"
        ));

        gameRepository.save(new Game(
                null,
                "Hollow Knight",
                "Team Cherry",
                "PC / Switch / PS4",
                "Метроїдванія",
                "Digital",
                2017,
                new BigDecimal("325.00"),
                "/posters/hollow-knight.jpg",
                "Двовимірний платформер із дослідженням підземного королівства Халлоунест.",
                "7+"
        ));

        gameRepository.save(new Game(
                null,
                "Cyberpunk 2077",
                "CD Projekt Red",
                "PC / PS5 / Xbox Series X",
                "RPG",
                "Digital",
                2020,
                new BigDecimal("1099.00"),
                "/posters/cyberpunk2077.jpg",
                "Екшн-RPG у футуристичному мегаполісі Найт-Сіті.",
                "18+"
        ));
    }
}
