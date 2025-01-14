package com.games.clue_notepad.usecase.question;

import com.games.clue_notepad.models.question.Question;
import com.games.clue_notepad.services.question.QuestionService;
import com.games.clue_notepad.web.question.QuestionViewModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetQuestionsUseCase {
    private final QuestionService questionService;

@Transactional
public List<QuestionViewModel> execute(Long id){
        List<Question> questions = questionService.getByGameId(id);
        return questions.stream().map(QuestionMapper::toViewModel).toList();
    }
}
