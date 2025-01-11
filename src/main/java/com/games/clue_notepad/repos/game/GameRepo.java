package com.games.clue_notepad.repos.game;

import com.games.clue_notepad.models.game.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepo extends CrudRepository<Game, Long> {
}
