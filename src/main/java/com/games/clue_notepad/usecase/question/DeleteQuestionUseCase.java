package com.games.clue_notepad.usecase.question;

import com.games.clue_notepad.services.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteQuestionUseCase {
    private final QuestionService questionService;

    public void execute(Long id) {
        questionService.delete(id);
    }
}