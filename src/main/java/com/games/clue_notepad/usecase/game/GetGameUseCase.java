package com.games.clue_notepad.usecase.game;

import com.games.clue_notepad.models.game.Game;
import com.games.clue_notepad.services.game.GameService;
import com.games.clue_notepad.web.game.GameViewModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetGameUseCase {
    private final GameService gameService;

    @Transactional
    public GameViewModel execute(Long id){
        Game game = gameService.getById(id);
        return GameMapper.toViewModel(game);
    }
}
