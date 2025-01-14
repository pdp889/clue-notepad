package com.games.clue_notepad.repos.question;

import com.games.clue_notepad.models.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {

    List<Question> findAllByGameId(Long gameId);
}
