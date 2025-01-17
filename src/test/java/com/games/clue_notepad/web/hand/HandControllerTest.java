package com.games.clue_notepad.web.hand;

import com.games.clue_notepad.models.card.CardType;
import com.games.clue_notepad.web.game.GameController;
import com.games.clue_notepad.web.game.GameViewModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class HandControllerTest {
    @Autowired
    GameController gameController;

    @Autowired
    HandController handController;

    @Test
    void testHandsController(){
        Long gameId1 = gameController.createGame(GameViewModel.builder().name("Game 1").build()).getBody().getId();

        HandViewModel hand1 = HandViewModel.builder().playerName("Player 1").cards(List.of(CardType.BALLROOM, CardType.SCARLET, CardType.CANDLESTICK, CardType.DINING_ROOM)).build();
        HandViewModel hand2 = HandViewModel.builder().playerName("Player 2").cardCount(4).build();
        HandViewModel hand3 = HandViewModel.builder().playerName("Player 3").cardCount(4).build();
        HandViewModel hand4 = HandViewModel.builder().playerName("Player 4").cardCount(4).build();

        HandViewModel handViewModel1 = handController.createHand(gameId1, hand1).getBody();
        assertThat(handViewModel1.cardCount).isEqualTo(4);

        Long handId1 = handViewModel1.getId();
        Long handId2 = handController.createHand(gameId1, hand2).getBody().getId();
        handController.createHand(gameId1, hand3);
        handController.createHand(gameId1, hand4);

        List<HandViewModel> game1Hands = handController.getHands(gameId1).getBody();

        assertThat(game1Hands).hasSize(4).extracting(HandViewModel::getPlayerName).containsExactlyInAnyOrder("Player 1", "Player 2", "Player 3", "Player 4");
        assertThat(game1Hands).hasSize(4).extracting(HandViewModel::getCardCount).containsOnly(4);

        HandViewModel handViewModel = handController.getHands(gameId1).getBody().stream().filter(h -> Objects.equals(h.id, handId1)).findFirst().orElseThrow();
        assertThat(handViewModel.getCards()).hasSize(4).containsExactlyInAnyOrder(CardType.BALLROOM, CardType.SCARLET, CardType.CANDLESTICK, CardType.DINING_ROOM);

        handViewModel.setPlayerName("Player 1.0");
        handViewModel.setCards(List.of(CardType.BILLIARD_ROOM, CardType.SCARLET, CardType.CANDLESTICK, CardType.DINING_ROOM));

        handViewModel = handController.updateHand(handId1, handViewModel).getBody();
        assertThat(handViewModel.getCards()).hasSize(4).containsExactlyInAnyOrder(CardType.BILLIARD_ROOM, CardType.SCARLET, CardType.CANDLESTICK, CardType.DINING_ROOM);

        handController.deleteHand(handId2);
        assertThat(handController.getHands(gameId1).getBody()).extracting(HandViewModel::getId).doesNotContain(handId2);

        game1Hands = handController.getHands(gameId1).getBody();
        assertThat(game1Hands).hasSize(3).extracting(HandViewModel::getPlayerName).containsExactlyInAnyOrder("Player 1.0", "Player 3", "Player 4");

        gameController.deleteGame(gameId1);
        game1Hands = handController.getHands(gameId1).getBody();
        assertThat(game1Hands).isEmpty();
    }
}
