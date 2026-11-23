package com.ua.gamestore.service;

import com.ua.gamestore.web.dto.GameCardDto;
import com.ua.gamestore.web.dto.GameDetailDto;
import com.ua.gamestore.web.dto.GameRequest;

import java.util.List;

public interface GameService {

    List<GameCardDto> findAll(String genre);

    GameDetailDto findById(Long id);

    GameDetailDto create(GameRequest request);

    GameDetailDto update(Long id, GameRequest request);

    void delete(Long id);
}
