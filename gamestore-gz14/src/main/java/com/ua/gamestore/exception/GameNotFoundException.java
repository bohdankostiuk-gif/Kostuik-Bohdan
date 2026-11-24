package com.ua.gamestore.exception;

public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(Long id) {
        super("Гру з id=" + id + " не знайдено");
    }
}
