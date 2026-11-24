package com.ua.gamestore.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.gamestore.exception.GameNotFoundException;
import com.ua.gamestore.service.GameService;
import com.ua.gamestore.web.dto.GameCardDto;
import com.ua.gamestore.web.dto.GameDetailDto;
import com.ua.gamestore.web.dto.GameRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GameService gameService;

    private GameDetailDto sampleDetail() {
        return new GameDetailDto(
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

    private GameRequest sampleRequest() {
        return new GameRequest(
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
    void getAll_returnsGameCards() throws Exception {
        when(gameService.findAll(null, null)).thenReturn(List.of(
                new GameCardDto(1L, "Hades", "PC / Switch", new BigDecimal("515.00"), "/posters/hades.jpg")
        ));

        mockMvc.perform(get("/api/games"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Hades"));
    }

    @Test
    void getAll_withFilters_passesParamsToService() throws Exception {
        when(gameService.findAll("RPG", "Disc")).thenReturn(List.of(
                new GameCardDto(3L, "The Witcher 3", "PC / PS5", new BigDecimal("729.00"), "/posters/w3.jpg")
        ));

        mockMvc.perform(get("/api/games")
                        .param("genre", "RPG")
                        .param("format", "Disc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("The Witcher 3"));

        verify(gameService).findAll("RPG", "Disc");
    }

    @Test
    void getById_returnsGameDetail() throws Exception {
        when(gameService.findById(1L)).thenReturn(sampleDetail());

        mockMvc.perform(get("/api/games/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.studio").value("Supergiant Games"))
                .andExpect(jsonPath("$.format").value("Digital"));
    }

    @Test
    void getById_returns404WithErrorBody() throws Exception {
        when(gameService.findById(999L)).thenThrow(new GameNotFoundException(999L));

        mockMvc.perform(get("/api/games/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Гру з id=999 не знайдено"))
                .andExpect(jsonPath("$.details").isArray());
    }

    @Test
    void create_returns201() throws Exception {
        when(gameService.create(any(GameRequest.class))).thenReturn(sampleDetail());

        mockMvc.perform(post("/api/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Hades"));
    }

    @Test
    void create_returns400WhenInvalid() throws Exception {
        String invalidJson = """
                {
                  "title": "",
                  "studio": "",
                  "genre": "RPG",
                  "price": -50
                }
                """;

        mockMvc.perform(post("/api/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details.length()").value(greaterThanOrEqualTo(3)));
    }

    @Test
    void update_returns200() throws Exception {
        when(gameService.update(eq(1L), any(GameRequest.class))).thenReturn(sampleDetail());

        mockMvc.perform(put("/api/games/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void update_returns404WithErrorBodyWhenMissing() throws Exception {
        when(gameService.update(eq(999L), any(GameRequest.class)))
                .thenThrow(new GameNotFoundException(999L));

        mockMvc.perform(put("/api/games/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRequest())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Гру з id=999 не знайдено"));
    }

    @Test
    void delete_returns204() throws Exception {
        mockMvc.perform(delete("/api/games/1"))
                .andExpect(status().isNoContent());

        verify(gameService).delete(1L);
    }

    @Test
    void delete_returns404WithErrorBodyWhenMissing() throws Exception {
        doThrow(new GameNotFoundException(999L)).when(gameService).delete(999L);

        mockMvc.perform(delete("/api/games/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }
}
