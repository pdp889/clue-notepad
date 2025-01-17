package com.games.clue_notepad.web.question;

import com.games.clue_notepad.usecase.question.CreateQuestionUseCase;
import com.games.clue_notepad.usecase.question.DeleteQuestionUseCase;
import com.games.clue_notepad.usecase.question.GetQuestionsUseCase;
import com.games.clue_notepad.usecase.question.UpdateQuestionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/question")
public class QuestionController {
    private final CreateQuestionUseCase createQuestionUseCase;
    private final DeleteQuestionUseCase deleteQuestionUseCase;
    private final UpdateQuestionUseCase updateQuestionUseCase;
    private final GetQuestionsUseCase getQuestionsUseCase;

    @PostMapping("/{gameId}")
    public ResponseEntity<QuestionViewModel> createQuestion(@PathVariable Long gameId, @RequestBody QuestionViewModel questionViewModel){
        return ResponseEntity.ok(createQuestionUseCase.execute(gameId, questionViewModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionViewModel> updateQuestion(@PathVariable Long id, @RequestBody QuestionViewModel questionViewModel){
        return ResponseEntity.ok(updateQuestionUseCase.execute(id, questionViewModel));
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<List<QuestionViewModel>> getQuestions(@PathVariable Long gameId){
        return ResponseEntity.ok(getQuestionsUseCase.execute(gameId));
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id){
        deleteQuestionUseCase.execute(id);
    }
}
