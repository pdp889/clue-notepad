package com.games.clue_notepad.usecase.question;

import com.games.clue_notepad.models.question.Question;
import com.games.clue_notepad.services.question.QuestionService;
import com.games.clue_notepad.web.question.QuestionViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetQuestionUseCase {
    private final QuestionService questionService;

    public QuestionViewModel execute(Long id){
        Question question = questionService.getById(id);
        return QuestionMapper.toViewModel(question);
    }
}
