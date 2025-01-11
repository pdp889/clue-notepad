package com.games.clue_notepad.web.hand;

import com.games.clue_notepad.models.card.CardType;
import com.games.clue_notepad.web.game.GameController;
import com.games.clue_notepad.web.game.GameViewModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

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

        CardViewModel card1 = CardViewModel.builder().cardType(CardType.BALLROOM).build();
        CardViewModel card2 = CardViewModel.builder().cardType(CardType.SCARLET).build();
        CardViewModel card3 = CardViewModel.builder().cardType(CardType.CANDLESTICK).build();
        CardViewModel card4 = CardViewModel.builder().cardType(CardType.DINING_ROOM).build();

        HandViewModel hand1 = HandViewModel.builder().playerName("Player 1").cardCount(4).cards(List.of(card1, card2, card3, card4)).build();
        HandViewModel hand2 = HandViewModel.builder().playerName("Player 2").cardCount(4).build();
        HandViewModel hand3 = HandViewModel.builder().playerName("Player 3").cardCount(4).build();
        HandViewModel hand4 = HandViewModel.builder().playerName("Player 4").cardCount(4).build();

        Long handId1 = handController.createHand(gameId1, hand1).getBody().getId();
        Long handId2 = handController.createHand(gameId1, hand2).getBody().getId();
        handController.createHand(gameId1, hand3);
        handController.createHand(gameId1, hand4);

        GameViewModel gameViewModel = gameController.getGame(gameId1).getBody();

        assertThat(gameViewModel.getHands()).hasSize(4).extracting(HandViewModel::getPlayerName).containsExactlyInAnyOrder("Player 1", "Player 2", "Player 3", "Player 4");
        assertThat(gameViewModel.getHands()).hasSize(4).extracting(HandViewModel::getCardCount).containsOnly(4);

        HandViewModel handViewModel = handController.getHand(handId1).getBody();
        assertThat(handViewModel.getCards()).hasSize(4).extracting(CardViewModel::getCardType).containsExactlyInAnyOrder(CardType.BALLROOM, CardType.SCARLET, CardType.CANDLESTICK, CardType.DINING_ROOM);

        handViewModel.setPlayerName("Player 1.0");
        handViewModel.getCards().forEach(cardViewModel -> {
            if (CardType.BALLROOM.equals(cardViewModel.getCardType())){
                cardViewModel.setCardType(CardType.BILLIARD_ROOM);
                cardViewModel.setId(null);
            }
        });

        handViewModel = handController.updateHand(handId1, handViewModel).getBody();
        assertThat(handViewModel.getCards()).hasSize(4).extracting(CardViewModel::getCardType).containsExactlyInAnyOrder(CardType.BILLIARD_ROOM, CardType.SCARLET, CardType.CANDLESTICK, CardType.DINING_ROOM);

        handController.deleteHand(handId2);
        assertThrows(NoSuchElementException.class, () -> handController.getHand(handId2));

        gameViewModel = gameController.getGame(gameId1).getBody();
        assertThat(gameViewModel.getHands()).hasSize(3).extracting(HandViewModel::getPlayerName).containsExactlyInAnyOrder("Player 1.0", "Player 3", "Player 4");

        gameController.deleteGame(gameId1);
        assertThrows(NoSuchElementException.class, () -> gameController.getGame(gameId1)); //Should have been deleted when game was deleted
    }
}
