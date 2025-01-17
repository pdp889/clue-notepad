package com.games.clue_notepad.web.game;

import com.games.clue_notepad.models.board.CellStatus;
import com.games.clue_notepad.models.card.CardType;
import org.assertj.core.api.AssertionsForClassTypes;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class GameControllerTest {
    @Autowired
    GameController gameController;

    @Autowired
    Flyway flyway;

    @Test
    void testGameControllerFunctions(){
        GameViewModel gameViewModel = gameController.createGame(GameViewModel.builder().name("Game 1").build()).getBody();
        GameViewModel gameViewModel2 = gameController.createGame(GameViewModel.builder().name("Game 2").build()).getBody();

        assertThat(gameViewModel.getId()).isNotNull();
        assertThat(gameViewModel2.getId()).isNotNull();

        List<GameViewModel> gameViewModels = gameController.getGames().getBody();
        assertThat(gameViewModels).hasSizeGreaterThanOrEqualTo(2).extracting(GameViewModel::getName).contains("Game 2", "Game 1");

        GameViewModel gameViewModel1 = gameController.getGame(gameViewModel.getId()).getBody();
        assertThat(gameViewModel1.getName()).isEqualTo("Game 1");

        GameViewModel gameViewModel3 = gameController.updateGame(gameViewModel2.getId(), GameViewModel.builder().name("Game 2.1").build()).getBody();
        AssertionsForClassTypes.assertThat(gameViewModel3.getName()).isEqualTo("Game 2.1");

        GameViewModel gameViewModel4 = gameController.getGame(gameViewModel2.getId()).getBody();
        assertThat(gameViewModel4.getName()).isEqualTo("Game 2.1");


        Long deleteId = gameViewModel.getId();
        gameController.deleteGame(deleteId);
        assertThrows(NoSuchElementException.class, () -> gameController.getGame(deleteId));
    }

    @Test
    void testGameControllerBoardFunction(){
//        flyway.clean();
//        flyway.migrate();

        BoardViewModel boardViewModel = gameController.getBoard(1L).getBody(); //from flyway

        assertThat(boardViewModel).isNotNull();
        assertThat(boardViewModel.rows).hasSize(21);
        assertThat(boardViewModel.columns).extracting(ColumnViewModel::getName).containsExactlyInAnyOrder("Envelope", "Player 1", "Player 2", "Player 3");

        assertThat(boardViewModel.cells.get(CardType.WHITE.ordinal())).containsExactly(CellStatus.NO, CellStatus.YES, CellStatus.NO, CellStatus.NO);
        assertThat(boardViewModel.cells.get(CardType.MUSTARD.ordinal())).containsExactly(CellStatus.NO, CellStatus.YES, CellStatus.NO, CellStatus.NO);
        assertThat(boardViewModel.cells.get(CardType.GREEN.ordinal())).containsExactly(CellStatus.NO, CellStatus.YES, CellStatus.NO, CellStatus.NO);
        assertThat(boardViewModel.cells.get(CardType.PEACOCK.ordinal())).containsExactly(CellStatus.UNKNOWN, CellStatus.NO, CellStatus.UNKNOWN, CellStatus.UNKNOWN);
        assertThat(boardViewModel.cells.get(CardType.PLUM.ordinal())).containsExactly(CellStatus.UNKNOWN, CellStatus.NO, CellStatus.UNKNOWN, CellStatus.UNKNOWN);
        assertThat(boardViewModel.cells.get(CardType.SCARLET.ordinal())).containsExactly(CellStatus.UNKNOWN, CellStatus.NO, CellStatus.UNKNOWN, CellStatus.UNKNOWN);
        assertThat(boardViewModel.cells.get(CardType.CANDLESTICK.ordinal())).containsExactly(CellStatus.NO, CellStatus.YES, CellStatus.NO, CellStatus.NO);
        assertThat(boardViewModel.cells.get(CardType.KNIFE.ordinal())).containsExactly(CellStatus.UNKNOWN, CellStatus.NO, CellStatus.UNKNOWN, CellStatus.UNKNOWN);
        assertThat(boardViewModel.cells.get(CardType.REVOLVER.ordinal())).containsExactly(CellStatus.UNKNOWN, CellStatus.NO, CellStatus.NO, CellStatus.UNKNOWN);
        assertThat(boardViewModel.cells.get(CardType.ROPE.ordinal())).containsExactly(CellStatus.UNKNOWN, CellStatus.NO, CellStatus.UNKNOWN, CellStatus.UNKNOWN);
        assertThat(boardViewModel.cells.get(CardType.PIPE.ordinal())).containsExactly(CellStatus.UNKNOWN, CellStatus.NO, CellStatus.UNKNOWN, CellStatus.UNKNOWN);
        assertThat(boardViewModel.cells.get(CardType.WRENCH.ordinal())).containsExactly(CellStatus.UNKNOWN, CellStatus.NO, CellStatus.UNKNOWN, CellStatus.UNKNOWN);
        assertThat(boardViewModel.cells.get(CardType.DINING_ROOM.ordinal())).containsExactly(CellStatus.UNKNOWN, CellStatus.NO, CellStatus.UNKNOWN, CellStatus.UNKNOWN);
        assertThat(boardViewModel.cells.get(CardType.KITCHEN.ordinal())).containsExactly(CellStatus.UNKNOWN, CellStatus.NO, CellStatus.UNKNOWN, CellStatus.UNKNOWN);
        assertThat(boardViewModel.cells.get(CardType.BALLROOM.ordinal())).containsExactly(CellStatus.NO, CellStatus.YES, CellStatus.NO, CellStatus.NO);
        assertThat(boardViewModel.cells.get(CardType.CONSERVATORY.ordinal())).containsExactly(CellStatus.NO, CellStatus.YES, CellStatus.NO, CellStatus.NO);
        assertThat(boardViewModel.cells.get(CardType.BILLIARD_ROOM.ordinal())).containsExactly(CellStatus.UNKNOWN, CellStatus.NO, CellStatus.UNKNOWN, CellStatus.UNKNOWN);
        assertThat(boardViewModel.cells.get(CardType.LIBRARY.ordinal())).containsExactly(CellStatus.NO, CellStatus.NO, CellStatus.YES, CellStatus.NO);
        assertThat(boardViewModel.cells.get(CardType.STUDY.ordinal())).containsExactly(CellStatus.UNKNOWN, CellStatus.NO, CellStatus.UNKNOWN, CellStatus.UNKNOWN);
        assertThat(boardViewModel.cells.get(CardType.HALL.ordinal())).containsExactly(CellStatus.UNKNOWN, CellStatus.NO, CellStatus.UNKNOWN, CellStatus.UNKNOWN);
        assertThat(boardViewModel.cells.get(CardType.LOUNGE.ordinal())).containsExactly(CellStatus.UNKNOWN, CellStatus.NO, CellStatus.UNKNOWN, CellStatus.UNKNOWN);
    }
}
