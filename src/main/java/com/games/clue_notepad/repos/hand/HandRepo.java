package com.games.clue_notepad.repos.hand;

import com.games.clue_notepad.models.hand.Hand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HandRepo extends CrudRepository<Hand, Long> {
}
