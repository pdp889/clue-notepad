package com.games.clue_notepad.usecase.question;

import com.games.clue_notepad.models.question.Question;
import com.games.clue_notepad.usecase.hand.HandMapper;
import com.games.clue_notepad.web.question.QuestionViewModel;
import lombok.experimental.UtilityClass;

@UtilityClass
public class QuestionMapper {

    public static QuestionViewModel toViewModel(Question question){
        return QuestionViewModel.builder()
                .id(question.getId())
                .hand(HandMapper.toViewModel(question.getHand()))
                .cardTypes(question.getCardTypes().stream().sorted().toList())
                .cardTypeShown(question.getCardTypeShown())
                .showingCard(question.isShowingCard())
                .build();
    }
}
