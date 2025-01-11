package com.games.clue_notepad.usecase.hand;

import com.games.clue_notepad.models.card.Card;
import com.games.clue_notepad.models.hand.Hand;
import com.games.clue_notepad.web.hand.CardViewModel;
import com.games.clue_notepad.web.hand.HandViewModel;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HandMapper {
    public static HandViewModel toViewModel(Hand hand){
        return HandViewModel.builder()
                .cardCount(hand.getCardCount())
                .playerName(hand.getPlayerName())
                .cards(hand.getCards().stream().map(HandMapper::toViewModel).toList())
                .id(hand.getId())
                .build();
    }

    public static CardViewModel toViewModel(Card card){
        return CardViewModel.builder()
                .id(card.getId())
                .cardType(card.getCardType())
                .build();
    }
}
