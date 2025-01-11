package com.games.clue_notepad.usecase.game;

import com.games.clue_notepad.services.game.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteGameUseCase {
    private final GameService gameService;

    public void execute(Long id) {
        gameService.delete(id);
    }
}