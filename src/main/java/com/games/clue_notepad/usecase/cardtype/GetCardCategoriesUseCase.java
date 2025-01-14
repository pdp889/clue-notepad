package com.games.clue_notepad.usecase.cardtype;

import com.games.clue_notepad.models.card.CardCategory;
import com.games.clue_notepad.models.card.CardType;
import com.games.clue_notepad.web.cardtype.CardCategoryViewModel;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class GetCardCategoriesUseCase {
    public List<CardCategoryViewModel> execute() {
        return Arrays.stream(CardCategory.values())
                .sorted()
                .map(category -> CardMapper.toViewModel(category, CardType.getCardTypesForCategory(category)))
                .toList();
    }
}
