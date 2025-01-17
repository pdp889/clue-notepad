package com.games.clue_notepad.usecase.game;

import com.games.clue_notepad.models.game.Game;
import com.games.clue_notepad.web.game.GameViewModel;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GameMapper {
    public static GameViewModel toViewModel(Game game){
        return GameViewModel.builder()
                .name(game.getName())
                .id(game.getId())
                .build();
    }

}
