package com.games.clue_notepad.usecase.hand;

import com.games.clue_notepad.models.game.Game;
import com.games.clue_notepad.models.hand.Hand;
import com.games.clue_notepad.services.game.GameService;
import com.games.clue_notepad.services.hand.HandService;
import com.games.clue_notepad.web.hand.HandViewModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateHandUseCase {
    private final HandService handService;
    private final GameService gameService;

    @Transactional
    public HandViewModel execute(Long gameId, HandViewModel handViewModel){
        Game game = gameService.getById(gameId);
        Hand saved = handService.create(handViewModel, game);
        return HandMapper.toViewModel(saved);
    }
}
