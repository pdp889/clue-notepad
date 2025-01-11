package com.games.clue_notepad.services.game;

import com.games.clue_notepad.models.game.Game;
import com.games.clue_notepad.repos.game.GameRepo;
import com.games.clue_notepad.web.game.GameViewModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class GameService {
    private final GameRepo gameRepo;

    public Game getById(Long id){
        return gameRepo.findById(id).orElseThrow();
    }

    public Game create(GameViewModel gameViewModel){
        return gameRepo.save(sharedSave(gameViewModel, Game.builder().build()));
    }

    public Game update(Long id, GameViewModel gameViewModel){
        Game game = getById(id);
        return sharedSave(gameViewModel, game);
    }

    public Game sharedSave(GameViewModel gameViewModel, Game game){
        game.setName(gameViewModel.getName());
        return game;
    }

    public void delete(Long id){
        gameRepo.deleteById(id);
    }
}
