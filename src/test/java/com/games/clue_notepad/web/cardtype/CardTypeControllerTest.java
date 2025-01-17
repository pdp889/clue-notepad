package com.games.clue_notepad.web.cardtype;

import com.games.clue_notepad.models.card.CardCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
public class CardTypeControllerTest {
    @Autowired
    CardTypeController cardTypeController;

    @Test
    void testCardTypeController(){
        List<CardCategoryViewModel> cardCategoryViewModels =  cardTypeController.getCardTypesByCategory().getBody();
        assertThat(cardCategoryViewModels).extracting(CardCategoryViewModel::getValue).containsExactlyInAnyOrder(CardCategory.PERSON.name(), CardCategory.WEAPON.name(), CardCategory.ROOM.name());

        List<List<CardTypeViewModel>> cardTypes = cardCategoryViewModels.stream().map(CardCategoryViewModel::getCardTypes).toList();
        List<CardTypeViewModel> persons = cardTypes.get(0);
        List<CardTypeViewModel> weapons = cardTypes.get(1);
        List<CardTypeViewModel> rooms = cardTypes.get(2);

        assertThat(persons).hasSize(6);
        assertThat(weapons).hasSize(6);
        assertThat(rooms).hasSize(9);
    }
}
