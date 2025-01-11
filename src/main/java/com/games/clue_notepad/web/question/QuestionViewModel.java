package com.games.clue_notepad.web.question;

import com.games.clue_notepad.models.card.CardType;
import com.games.clue_notepad.web.hand.HandViewModel;
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
public class QuestionViewModel {
    Long id;

    HandViewModel hand;

    @Builder.Default
    List<CardType> cardTypes = new ArrayList<>();

    boolean showingCard;

    CardType cardTypeShown;
}
