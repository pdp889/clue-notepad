package com.games.clue_notepad.web.cardtype;

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
public class CardCategoryViewModel {
    String value;

    String label;

    @Builder.Default
    List<CardTypeViewModel> cardTypes = new ArrayList<>();
}
