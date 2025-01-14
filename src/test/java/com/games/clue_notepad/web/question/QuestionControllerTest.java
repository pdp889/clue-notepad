package com.games.clue_notepad.web.question;

import com.games.clue_notepad.models.card.CardType;
import com.games.clue_notepad.web.game.GameController;
import com.games.clue_notepad.web.game.GameViewModel;
import com.games.clue_notepad.web.hand.HandController;
import com.games.clue_notepad.web.hand.HandViewModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class QuestionControllerTest {
    @Autowired
    GameController gameController;

    @Autowired
    QuestionController questionController;

    @Autowired
    HandController handController;

    @Test
    void testHandsController(){
        Long gameId1 = gameController.createGame(GameViewModel.builder().name("Game 1").build()).getBody().getId();

        HandViewModel hand1 = HandViewModel.builder().playerName("Player 1").cardCount(4).cards(List.of(CardType.BALLROOM, CardType.SCARLET, CardType.CANDLESTICK, CardType.DINING_ROOM)).build();
        HandViewModel hand2 = HandViewModel.builder().playerName("Player 2").cardCount(4).build();
        HandViewModel hand3 = HandViewModel.builder().playerName("Player 3").cardCount(4).build();
        HandViewModel hand4 = HandViewModel.builder().playerName("Player 4").cardCount(4).build();

        handController.createHand(gameId1, hand1).getBody().getId();
        Long handId2 = handController.createHand(gameId1, hand2).getBody().getId();
        Long handId3 = handController.createHand(gameId1, hand3).getBody().getId();
        handController.createHand(gameId1, hand4);

        QuestionViewModel questionViewModel = QuestionViewModel.builder().hand(HandViewModel.builder().id(handId2).build()).cardTypes(List.of(CardType.BALLROOM, CardType.REVOLVER, CardType.PEACOCK)).showingCard(false).build();

        questionViewModel = questionController.createQuestion(gameId1, questionViewModel).getBody();
        Long questionId = questionViewModel.getId();

        assertThat(questionViewModel.getCardTypes().stream()).containsExactlyInAnyOrder(CardType.BALLROOM, CardType.REVOLVER, CardType.PEACOCK);
        assertThat(questionViewModel.isShowingCard()).isFalse();

        questionViewModel = questionController.getQuestion(questionId).getBody();
        assertThat(questionViewModel.getCardTypes().stream()).containsExactlyInAnyOrder(CardType.BALLROOM, CardType.REVOLVER, CardType.PEACOCK);
        assertThat(questionViewModel.isShowingCard()).isFalse();

        GameViewModel gameViewModel = gameController.getGame(gameId1).getBody();

        assertThat(gameViewModel.getQuestions()).hasSize(1);
        assertThat(gameViewModel.getQuestions().stream().findFirst().orElseThrow().getCardTypes().stream()).containsExactlyInAnyOrder(CardType.BALLROOM, CardType.REVOLVER, CardType.PEACOCK);
        assertThat(gameViewModel.getQuestions()).hasSize(1).extracting(q -> q.getHand().getPlayerName()).containsExactlyInAnyOrder("Player 2");

        questionViewModel.setShowingCard(true);

        questionViewModel = questionController.updateQuestion(questionId, questionViewModel).getBody();
        assertThat(questionViewModel.isShowingCard()).isTrue();

        handController.deleteHand(handId2);
        assertThrows(NoSuchElementException.class, () -> handController.getHand(handId2));
        assertThrows(NoSuchElementException.class, () -> questionController.getQuestion(questionId));

        questionViewModel = QuestionViewModel.builder().hand(HandViewModel.builder().id(handId3).build()).cardTypes(List.of(CardType.BALLROOM, CardType.REVOLVER, CardType.PEACOCK)).showingCard(false).build();

        Long questionId1 = questionController.createQuestion(gameId1, questionViewModel).getBody().getId();
        questionController.deleteQuestion(questionId1);

        assertThat(handController.getHand(handId3).getBody()).isNotNull();
        assertThrows(NoSuchElementException.class, () -> questionController.getQuestion(questionId1));
    }
}
