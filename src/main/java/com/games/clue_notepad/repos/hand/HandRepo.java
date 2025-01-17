package com.games.clue_notepad.repos.hand;

import com.games.clue_notepad.models.hand.Hand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HandRepo extends JpaRepository<Hand, Long> {

    List<Hand> findAllByGameId(Long gameId);
}
