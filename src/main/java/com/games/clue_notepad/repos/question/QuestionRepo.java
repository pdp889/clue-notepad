package com.games.clue_notepad.repos.question;

import com.games.clue_notepad.models.question.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepo extends CrudRepository<Question, Long> {
}
