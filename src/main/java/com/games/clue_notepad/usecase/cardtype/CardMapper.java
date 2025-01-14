package com.games.clue_notepad.usecase.cardtype;

import com.games.clue_notepad.models.card.CardCategory;
import com.games.clue_notepad.models.card.CardType;
import com.games.clue_notepad.web.cardtype.CardCategoryViewModel;
import com.games.clue_notepad.web.cardtype.CardTypeViewModel;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class CardMapper {

    public CardCategoryViewModel toViewModel(CardCategory category, List<CardType> cardTypes){
        return CardCategoryViewModel.builder()
                .value(category.name())
                .label(category.getLabel())
                .cardTypes(cardTypes.stream().sorted().map(CardMapper::toViewModel).toList())
                .build();
    }

    public CardTypeViewModel toViewModel(CardType type){
        return CardTypeViewModel.builder()
                .value(type.name())
                .label(type.getLabel())
                .build();
    }
}
