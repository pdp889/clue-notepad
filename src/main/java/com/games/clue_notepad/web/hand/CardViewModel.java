package com.games.clue_notepad.web.hand;

import com.games.clue_notepad.models.card.CardType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class CardViewModel {
    Long id;
    CardType cardType;
}
