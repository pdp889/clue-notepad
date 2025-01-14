package com.games.clue_notepad.web.cardtype;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class CardTypeViewModel {
    String value;
    String label;
    CardCategoryViewModel cardCategory;
}
