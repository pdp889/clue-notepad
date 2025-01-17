package com.games.clue_notepad.usecase.question;

import com.games.clue_notepad.models.question.Question;
import com.games.clue_notepad.services.game.GameService;
import com.games.clue_notepad.services.question.QuestionService;
import com.games.clue_notepad.web.question.QuestionViewModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateQuestionUseCase {
    private final QuestionService questionService;
    private final GameService gameService;

    @Transactional
    public QuestionViewModel execute(Long gameId, QuestionViewModel questionViewModel){
        Question question = questionService.create(questionViewModel, gameService.getById(gameId));
        return QuestionMapper.toViewModel(question);
    }
}
