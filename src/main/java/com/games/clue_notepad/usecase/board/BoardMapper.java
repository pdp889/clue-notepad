package com.games.clue_notepad.usecase.board;

import com.games.clue_notepad.models.board.Board;
import com.games.clue_notepad.models.hand.Hand;
import com.games.clue_notepad.web.game.BoardViewModel;
import com.games.clue_notepad.web.game.ColumnViewModel;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BoardMapper {

    public static BoardViewModel toViewModel(Board board){
        return BoardViewModel.builder()
                .columns(board.getHands().stream().map(BoardMapper::toViewModel).toList())
                .cells(board.getCells())
                .build();
    }

    private static ColumnViewModel toViewModel(Hand hand) {
        return ColumnViewModel.builder().handId(hand.getId()).name(hand.getPlayerName()).build();
    }
}
