package com.games.clue_notepad.services.question;

import com.games.clue_notepad.models.game.Game;
import com.games.clue_notepad.models.question.Question;
import com.games.clue_notepad.repos.question.QuestionRepo;
import com.games.clue_notepad.services.hand.HandService;
import com.games.clue_notepad.web.question.QuestionViewModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {
    private final HandService handService;
    private final QuestionRepo questionRepo;

    public Question getById(Long id){
        return questionRepo.findById(id).orElseThrow();
    }

    public List<Question> getByGameId(Long gameId) {
        return questionRepo.findAllByGameId(gameId);
    }
    public Question create(QuestionViewModel questionViewModel, Game game){
        Question question = Question.builder().game(game).build();
        return questionRepo.save(sharedSave(questionViewModel, question));
    }

    public Question update(Long id, QuestionViewModel questionViewModel){
        Question question = getById(id);
        return sharedSave(questionViewModel, question);
    }

    public Question sharedSave(QuestionViewModel questionViewModel, Question question){
        question.setCardTypes(new HashSet<>(questionViewModel.getCardTypes()));
        question.setHand(handService.getById(questionViewModel.getHand().getId()));
        question.setCardTypeShown(questionViewModel.getCardTypeShown());
        question.setShowingCard(questionViewModel.isShowingCard());

        return question;
    }

    public void delete(Long id){
        questionRepo.deleteById(id);
    }
}
