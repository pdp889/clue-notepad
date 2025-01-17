package com.games.clue_notepad.usecase.game;

import com.games.clue_notepad.models.game.Game;
import com.games.clue_notepad.services.game.GameService;
import com.games.clue_notepad.web.game.GameViewModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetGamesUseCase {
    private final GameService gameService;

    @Transactional
    public List<GameViewModel> execute(){
        return gameService.getAll().stream()
                .sorted(Comparator.comparing(Game::getId))
                .map(GameMapper::toViewModel)
                .toList();
    }
}
