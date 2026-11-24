package com.ua.gamestore.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.gamestore.repository.GameRepository;
import com.ua.gamestore.web.dto.GameRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GameRepository gameRepository;

    @BeforeEach
    void clearState() {
        gameRepository.deleteAll();
    }

    private long givenGame(String title, String genre, String format) throws Exception {
        GameRequest request = new GameRequest(
                title,
                "Test Studio",
                "PC",
                genre,
                format,
                2023,
                new BigDecimal("499.00"),
                "/posters/test.jpg",
                "Опис",
                "12+"
        );
        String body = mockMvc.perform(post("/api/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return objectMapper.readTree(body).get("id").asLong();
    }

    @Test
    void getAll_returnsCreatedGames() throws Exception {
        givenGame("Game A", "RPG", "Digital");
        givenGame("Game B", "Shooter", "Disc");

        mockMvc.perform(get("/api/games"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getAll_whenEmpty_returnsEmptyList() throws Exception {
        mockMvc.perform(get("/api/games"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getAll_filtersByGenreAndFormat() throws Exception {
        givenGame("The Witcher 3", "RPG", "Disc");
        givenGame("Cyberpunk 2077", "RPG", "Digital");

        mockMvc.perform(get("/api/games")
                        .param("genre", "RPG")
                        .param("format", "Disc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("The Witcher 3"));
    }

    @Test
    void getById_returnsGameDetails() throws Exception {
        long id = givenGame("Hades", "Roguelike", "Digital");

        mockMvc.perform(get("/api/games/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value((int) id))
                .andExpect(jsonPath("$.title").value("Hades"))
                .andExpect(jsonPath("$.format").value("Digital"));
    }

    @Test
    void getById_missing_returns404WithErrorBody() throws Exception {
        mockMvc.perform(get("/api/games/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Гру з id=999 не знайдено"));
    }

    @Test
    void createUpdateAndDelete_fullFlow() throws Exception {
        long id = givenGame("Hollow Knight", "Metroidvania", "Digital");

        GameRequest updateRequest = new GameRequest(
                "Hollow Knight: Voidheart",
                "Team Cherry",
                "PC / Switch",
                "Metroidvania",
                "Disc",
                2018,
                new BigDecimal("399.00"),
                "/posters/hk.jpg",
                "Оновлений опис",
                "7+"
        );

        mockMvc.perform(put("/api/games/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Hollow Knight: Voidheart"))
                .andExpect(jsonPath("$.format").value("Disc"));

        mockMvc.perform(delete("/api/games/" + id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/games/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void create_invalid_returns400WithDetails() throws Exception {
        String invalidJson = """
                { "title": "", "studio": "", "genre": "", "price": -10 }
                """;

        mockMvc.perform(post("/api/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.details").isArray());
    }
}
