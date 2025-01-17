package com.games.clue_notepad.usecase.board;

import com.games.clue_notepad.models.board.Board;
import com.games.clue_notepad.services.board.BoardService;
import com.games.clue_notepad.web.game.BoardViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetBoardUseCase {
    private final BoardService boardService;

    public BoardViewModel execute(Long gameId){
        Board board = boardService.fillBoard(gameId);
        return BoardMapper.toViewModel(board);
    }
}
