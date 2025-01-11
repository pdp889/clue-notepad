package com.games.clue_notepad.usecase.game;

import com.games.clue_notepad.models.game.Game;
import com.games.clue_notepad.services.game.GameService;
import com.games.clue_notepad.web.game.GameViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateGameUseCase {
    private final GameService gameService;
    public GameViewModel execute(GameViewModel gameViewModel){
        Game game = gameService.create(gameViewModel);
        return GameMapper.toViewModel(game);
    }
}
