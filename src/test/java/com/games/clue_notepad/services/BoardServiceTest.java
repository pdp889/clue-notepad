package com.games.clue_notepad.services;

import com.games.clue_notepad.models.board.Board;
import com.games.clue_notepad.models.board.CellStatus;
import com.games.clue_notepad.models.card.CardType;
import com.games.clue_notepad.models.game.Game;
import com.games.clue_notepad.models.hand.Hand;
import com.games.clue_notepad.models.question.Question;
import com.games.clue_notepad.repos.game.GameRepo;
import com.games.clue_notepad.repos.hand.HandRepo;
import com.games.clue_notepad.repos.question.QuestionRepo;
import com.games.clue_notepad.services.board.BoardService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    GameRepo gameRepo;

    @Autowired
    HandRepo handRepo;

    @Autowired
    QuestionRepo questionRepo;

    @Test
    void testBoardServiceEnvelopeWhenAllPlayersAreNo(){
        Long gameId = 1L;
        Game game = gameRepo.findById(gameId).orElseThrow();
        Hand player3Hand = handRepo.findById(3L).orElseThrow();

        Question question = Question.builder()
                .hand(player3Hand)
                .cardTypes(Set.of(CardType.WHITE, CardType.REVOLVER, CardType.LIBRARY))
                .showingCard(false)
                .game(game)
                .build();

        questionRepo.save(question);

        Board board = boardService.fillBoard(gameId);
        assertThat(board.getCell(CardType.REVOLVER, board.getHands().get(3))).isEqualTo(CellStatus.NO);
        assertThat(board.getCell(CardType.REVOLVER, board.getHands().get(0))).isEqualTo(CellStatus.YES);
    }

    @Test
    void testBoardServiceEnvelopeWhenAllItemsInCategoryArePlayerYeses(){
        Long gameId = 1L;
        Game game = gameRepo.findById(gameId).orElseThrow();
        Hand player3Hand = handRepo.findById(3L).orElseThrow();

        Question question = Question.builder()
                .hand(player3Hand)
                .cardTypes(Set.of(CardType.PEACOCK, CardType.REVOLVER, CardType.LIBRARY))
                .showingCard(true)
                .cardTypeShown(CardType.PEACOCK)
                .game(game)
                .build();

        Question question1 = Question.builder()
                .hand(player3Hand)
                .cardTypes(Set.of(CardType.PLUM, CardType.REVOLVER, CardType.LIBRARY))
                .showingCard(true)
                .cardTypeShown(CardType.PLUM)
                .game(game)
                .build();

        questionRepo.saveAll(List.of(question, question1));
        Board board = boardService.fillBoard(gameId);
        assertThat(board.getCell(CardType.SCARLET, board.getHands().get(0))).isEqualTo(CellStatus.YES);
    }



    @Test
    void testBoardServiceBasicQuestionInference(){
        //player 2 showed a card on [MUSTARD, KNIFE, HALL]
        //player 1 has MUSTARD and CANDLESTICK
        Long gameId = 1L;
        Game game = gameRepo.findById(gameId).orElseThrow();
        Hand player2Hand = handRepo.findById(2L).orElseThrow();



        //player 2 does not have hall, therefore player 2 has KNIFE
        Question question = Question.builder()
                .hand(player2Hand)
                .cardTypes(Set.of(CardType.MUSTARD, CardType.CANDLESTICK, CardType.HALL))
                .showingCard(false)
                .game(game)
                .build();

        questionRepo.save(question);
        Board board = boardService.fillBoard(gameId);
        assertThat(board.getCell(CardType.KNIFE, board.getHands().get(2))).isEqualTo(CellStatus.YES);
    }

    @Test
    void testBoardServiceCardCountInference(){
        //player 2 has a yes on library only
        //going to take player 2 down to 5 unknowns, and they should all turn to yeses
        Long gameId = 1L;
        Game game = gameRepo.findById(gameId).orElseThrow();
        Hand player2Hand = handRepo.findById(2L).orElseThrow();



        Question question = Question.builder()
                .hand(player2Hand)
                .cardTypes(Set.of(CardType.PEACOCK, CardType.ROPE, CardType.DINING_ROOM))
                .showingCard(false)
                .game(game)
                .build();

        Question question1 = Question.builder()
                .hand(player2Hand)
                .cardTypes(Set.of(CardType.PLUM, CardType.PIPE, CardType.KITCHEN))
                .showingCard(false)
                .game(game)
                .build();

        Question question2 = Question.builder()
                .hand(player2Hand)
                .cardTypes(Set.of(CardType.SCARLET, CardType.REVOLVER, CardType.BILLIARD_ROOM))
                .showingCard(false)
                .game(game)
                .build();

        questionRepo.save(question);
        questionRepo.save(question1);
        questionRepo.save(question2);
        Board board = boardService.fillBoard(gameId);

        assertThat(board.getCell(CardType.KNIFE, board.getHands().get(2))).isEqualTo(CellStatus.YES);
        assertThat(board.getCell(CardType.HALL, board.getHands().get(2))).isEqualTo(CellStatus.YES);
        assertThat(board.getCell(CardType.STUDY, board.getHands().get(2))).isEqualTo(CellStatus.YES);
        assertThat(board.getCell(CardType.LOUNGE, board.getHands().get(2))).isEqualTo(CellStatus.YES);
        assertThat(board.getCell(CardType.WRENCH, board.getHands().get(2))).isEqualTo(CellStatus.YES);
        assertThat(board.getCell(CardType.LIBRARY, board.getHands().get(2))).isEqualTo(CellStatus.YES);
    }

    @Test
    void testBoardBacktrack(){
        //player 2 has a yes on library only
        //going to give him 2 more yeses
        Long gameId = 1L;
        Game game = gameRepo.findById(gameId).orElseThrow();
        Hand player2Hand = handRepo.findById(2L).orElseThrow();



        Question question = Question.builder()
                .hand(player2Hand)
                .cardTypes(Set.of(CardType.PEACOCK, CardType.ROPE, CardType.DINING_ROOM))
                .showingCard(true)
                .cardTypeShown(CardType.PEACOCK)
                .game(game)
                .build();

        Question question1 = Question.builder()
                .hand(player2Hand)
                .cardTypes(Set.of(CardType.PLUM, CardType.PIPE, CardType.KITCHEN))
                .showingCard(true)
                .cardTypeShown(CardType.PIPE)
                .game(game)
                .build();

        //Now Player 2 has the LIBRARY, PEACOCK, PIPE and 3 unknown cards
        //from test data, we know Player 2 has at least 1 of MUSTARD, KNIFE, and HALL


        //add some questions to player 2, already has one for [KNIFE, HALL, MUSTARD]
        //the only answer that satisfies all of these questions in 3 cards is [KNIFE, KITCHEN, STUDY] for remaining cards

        Question question3 = Question.builder()
                .hand(player2Hand)
                .cardTypes(Set.of(CardType.WHITE, CardType.KNIFE, CardType.BILLIARD_ROOM))
                .showingCard(true)
                .game(game)
                .build();

        Question question4 = Question.builder()
                .hand(player2Hand)
                .cardTypes(Set.of(CardType.PLUM, CardType.REVOLVER, CardType.KITCHEN))
                .showingCard(true)
                .game(game)
                .build();

        Question question5 = Question.builder()
                .hand(player2Hand)
                .cardTypes(Set.of(CardType.GREEN, CardType.ROPE, CardType.KITCHEN))
                .showingCard(true)
                .game(game)
                .build();

        Question question6 = Question.builder()
                .hand(player2Hand)
                .cardTypes(Set.of(CardType.GREEN, CardType.WRENCH, CardType.STUDY))
                .showingCard(true)
                .game(game)
                .build();

        Question question7 = Question.builder()
                .hand(player2Hand)
                .cardTypes(Set.of(CardType.SCARLET, CardType.CANDLESTICK, CardType.STUDY))
                .showingCard(true)
                .game(game)
                .build();

        questionRepo.saveAll(List.of(question, question1, question3, question4, question5, question6, question7));
        Board board = boardService.fillBoard(gameId);

        assertThat(board.getCell(CardType.KNIFE, board.getHands().get(2))).isEqualTo(CellStatus.YES);
        assertThat(board.getCell(CardType.KITCHEN, board.getHands().get(2))).isEqualTo(CellStatus.YES);
        assertThat(board.getCell(CardType.STUDY, board.getHands().get(2))).isEqualTo(CellStatus.YES);
    }

    @Test
    void testBoardBacktrackMultipleSolutions(){
        //player 2 has a yes on library only
        //going to give him 2 more yeses
        Long gameId = 1L;
        Game game = gameRepo.findById(gameId).orElseThrow();
        Hand player2Hand = handRepo.findById(2L).orElseThrow();



        Question question = Question.builder()
                .hand(player2Hand)
                .cardTypes(Set.of(CardType.PEACOCK, CardType.ROPE, CardType.DINING_ROOM))
                .showingCard(true)
                .cardTypeShown(CardType.PEACOCK)
                .game(game)
                .build();

        Question question1 = Question.builder()
                .hand(player2Hand)
                .cardTypes(Set.of(CardType.PLUM, CardType.PIPE, CardType.KITCHEN))
                .showingCard(true)
                .cardTypeShown(CardType.PIPE)
                .game(game)
                .build();

        //Now Player 2 has the LIBRARY, PEACOCK, PIPE and 3 unknown cards
        //from test data, we know Player 2 has at least 1 of MUSTARD, KNIFE, and HALL


        //add some questions to player 2, already has one for [KNIFE, HALL, MUSTARD]
        //the only answer that satisfies all of these questions in 3 cards is [KNIFE, KITCHEN, STUDY] for remaining cards

        Question question3 = Question.builder()
                .hand(player2Hand)
                .cardTypes(Set.of(CardType.WHITE, CardType.KNIFE, CardType.BILLIARD_ROOM))
                .showingCard(true)
                .game(game)
                .build();

        Question question4 = Question.builder()
                .hand(player2Hand)
                .cardTypes(Set.of(CardType.PLUM, CardType.REVOLVER, CardType.KITCHEN))
                .showingCard(true)
                .game(game)
                .build();

        Question question5 = Question.builder()
                .hand(player2Hand)
                .cardTypes(Set.of(CardType.GREEN, CardType.ROPE, CardType.KITCHEN))
                .showingCard(true)
                .game(game)
                .build();

        Question question6 = Question.builder()
                .hand(player2Hand)
                .cardTypes(Set.of(CardType.GREEN, CardType.WRENCH, CardType.STUDY))
                .showingCard(true)
                .game(game)
                .build();

        //without question7, it could either be [KNIFE, KITCHEN, STUDY] or [KNIFE, KITCHEN, WRENCH]
        questionRepo.saveAll(List.of(question, question1, question3, question4, question5, question6));
        Board board = boardService.fillBoard(gameId);

        assertThat(board.getCell(CardType.KNIFE, board.getHands().get(2))).isEqualTo(CellStatus.UNKNOWN);
        assertThat(board.getCell(CardType.KITCHEN, board.getHands().get(2))).isEqualTo(CellStatus.UNKNOWN);
        assertThat(board.getCell(CardType.STUDY, board.getHands().get(2))).isEqualTo(CellStatus.UNKNOWN);
        assertThat(board.getCell(CardType.WRENCH, board.getHands().get(2))).isEqualTo(CellStatus.UNKNOWN);
    }
}
