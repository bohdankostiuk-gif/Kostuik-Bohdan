package com.ua.gamestore.service;

import com.ua.gamestore.domain.Game;
import com.ua.gamestore.exception.GameNotFoundException;
import com.ua.gamestore.repository.GameRepository;
import com.ua.gamestore.service.mapper.GameMapper;
import com.ua.gamestore.web.dto.GameCardDto;
import com.ua.gamestore.web.dto.GameDetailDto;
import com.ua.gamestore.web.dto.GameRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GameServiceImplTest {

    private final GameRepository repository = mock(GameRepository.class);
    private final GameMapper mapper = new GameMapper();
    private final GameService service = new GameServiceImpl(repository, mapper);

    private Game sampleGame(Long id, String title, String genre, String format) {
        return new Game(
                id,
                title,
                "CD Projekt Red",
                "PC / PS5",
                genre,
                format,
                2015,
                new BigDecimal("729.00"),
                "/posters/witcher3.jpg",
                "Опис гри",
                "18+"
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
    void findAll_withoutFilters_returnsAllCards() {
        when(repository.findAll()).thenReturn(List.of(
                sampleGame(1L, "The Witcher 3", "RPG", "Disc")
        ));

        List<GameCardDto> cards = service.findAll(null, null);

        assertThat(cards).hasSize(1);
        assertThat(cards.get(0).title()).isEqualTo("The Witcher 3");
        verify(repository, times(1)).findAll();
    }

    @Test
    void findAll_withGenre_callsFindByGenre() {
        when(repository.findByGenre("RPG")).thenReturn(List.of(
                sampleGame(1L, "The Witcher 3", "RPG", "Disc")
        ));

        List<GameCardDto> cards = service.findAll("RPG", null);

        assertThat(cards).hasSize(1);
        verify(repository, times(1)).findByGenre("RPG");
    }

    @Test
    void findAll_withFormat_callsFindByFormat() {
        when(repository.findByFormat("Disc")).thenReturn(List.of(
                sampleGame(1L, "The Witcher 3", "RPG", "Disc")
        ));

        List<GameCardDto> cards = service.findAll(null, "Disc");

        assertThat(cards).hasSize(1);
        verify(repository, times(1)).findByFormat("Disc");
    }

    @Test
    void findAll_withGenreAndFormat_filtersBoth() {
        when(repository.findByGenre("RPG")).thenReturn(List.of(
                sampleGame(1L, "The Witcher 3", "RPG", "Disc"),
                sampleGame(2L, "Cyberpunk 2077", "RPG", "Digital")
        ));

        List<GameCardDto> cards = service.findAll("RPG", "Disc");

        assertThat(cards).hasSize(1);
        assertThat(cards.get(0).title()).isEqualTo("The Witcher 3");
    }

    @Test
    void findById_returnsDetailDto() {
        when(repository.findById(1L)).thenReturn(Optional.of(
                sampleGame(1L, "The Witcher 3", "RPG", "Disc")
        ));

        GameDetailDto detail = service.findById(1L);

        assertThat(detail.id()).isEqualTo(1L);
        assertThat(detail.title()).isEqualTo("The Witcher 3");
        assertThat(detail.format()).isEqualTo("Disc");
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void findById_throwsWhenNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(999L))
                .isInstanceOf(GameNotFoundException.class);
        verify(repository, times(1)).findById(999L);
    }

    @Test
    void create_savesAndReturnsWithGeneratedId() {
        when(repository.save(any(Game.class))).thenAnswer(inv -> {
            Game game = inv.getArgument(0);
            game.setId(10L);
            return game;
        });

        GameDetailDto created = service.create(sampleRequest());

        assertThat(created.id()).isEqualTo(10L);
        assertThat(created.title()).isEqualTo("Hades");
        verify(repository, times(1)).save(any(Game.class));
    }

    @Test
    void update_existingGame_keepsId() {
        when(repository.findById(1L)).thenReturn(Optional.of(
                sampleGame(1L, "Old Title", "RPG", "Disc")
        ));
        when(repository.save(any(Game.class))).thenAnswer(inv -> inv.getArgument(0));

        GameDetailDto updated = service.update(1L, sampleRequest());

        assertThat(updated.id()).isEqualTo(1L);
        assertThat(updated.title()).isEqualTo("Hades");
    }

    @Test
    void update_missingGame_throws() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(999L, sampleRequest()))
                .isInstanceOf(GameNotFoundException.class);
    }

    @Test
    void delete_existingGame_callsDeleteById() {
        when(repository.deleteById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void delete_missingGame_throws() {
        when(repository.deleteById(999L)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(999L))
                .isInstanceOf(GameNotFoundException.class);
    }
}
