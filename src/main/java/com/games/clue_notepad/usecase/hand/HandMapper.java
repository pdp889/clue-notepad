package com.games.clue_notepad.usecase.hand;

import com.games.clue_notepad.models.hand.Hand;
import com.games.clue_notepad.web.hand.HandViewModel;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HandMapper {
    public static HandViewModel toViewModel(Hand hand){
        return HandViewModel.builder()
                .cardCount(hand.getCardCount())
                .playerName(hand.getPlayerName())
                .cards(hand.getCards().stream().sorted().toList())
                .id(hand.getId())
                .build();
    }
}
