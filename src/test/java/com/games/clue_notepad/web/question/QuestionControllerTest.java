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
import java.util.Objects;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

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

        questionViewModel = questionController.getQuestions(gameId1).getBody().stream().filter(q -> Objects.equals(q.id, questionId)).findFirst().orElseThrow();
        assertThat(questionViewModel.getCardTypes().stream()).containsExactlyInAnyOrder(CardType.BALLROOM, CardType.REVOLVER, CardType.PEACOCK);
        assertThat(questionViewModel.isShowingCard()).isFalse();

        List<QuestionViewModel> game1Questions = questionController.getQuestions(gameId1).getBody();
        assertThat(game1Questions).hasSize(1);
        assertThat(game1Questions.stream().findFirst().orElseThrow().getCardTypes().stream()).containsExactlyInAnyOrder(CardType.BALLROOM, CardType.REVOLVER, CardType.PEACOCK);
        assertThat(game1Questions).hasSize(1).extracting(q -> q.getHand().getPlayerName()).containsExactlyInAnyOrder("Player 2");

        questionViewModel.setShowingCard(true);

        questionViewModel = questionController.updateQuestion(questionId, questionViewModel).getBody();
        assertThat(questionViewModel.isShowingCard()).isTrue();

        handController.deleteHand(handId2);
        assertThat(handController.getHands(gameId1).getBody()).extracting(HandViewModel::getId).doesNotContain(handId2);
        assertThat(questionController.getQuestions(gameId1).getBody()).extracting(QuestionViewModel::getId).doesNotContain(questionId);

        questionViewModel = QuestionViewModel.builder().hand(HandViewModel.builder().id(handId3).build()).cardTypes(List.of(CardType.BALLROOM, CardType.REVOLVER, CardType.PEACOCK)).showingCard(false).build();

        Long questionId1 = questionController.createQuestion(gameId1, questionViewModel).getBody().getId();
        questionController.deleteQuestion(questionId1);

        assertThat(handController.getHands(gameId1).getBody()).extracting(HandViewModel::getId).contains(handId3);
        assertThat(questionController.getQuestions(gameId1).getBody()).extracting(QuestionViewModel::getId).doesNotContain(questionId1);
    }
}
