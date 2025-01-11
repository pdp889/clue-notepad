package com.games.clue_notepad.web.game;

import com.games.clue_notepad.web.hand.HandViewModel;
import com.games.clue_notepad.web.question.QuestionViewModel;
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
public class GameViewModel {
    Long id;
    String name;

    @Builder.Default
    List<HandViewModel> hands = new ArrayList<>();

    @Builder.Default
    List<QuestionViewModel> questions = new ArrayList<>();
}
