package com.games.clue_notepad.web.hand;

import com.games.clue_notepad.models.card.CardType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class HandViewModel {
    Long id;

    @Builder.Default
    List<CardType> cards = new ArrayList<>();

    String playerName;

    Integer cardCount;
}
