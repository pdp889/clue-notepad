package com.games.clue_notepad.web.game;

import com.games.clue_notepad.usecase.game.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/game")
public class GameController {
    private final CreateGameUseCase createGameUseCase;
    private final DeleteGameUseCase deleteGameUseCase;
    private final UpdateGameUseCase updateGameUseCase;
    private final GetGameUseCase getGameUseCase;
    private final GetGamesUseCase getGamesUseCase;


    @PostMapping()
    public ResponseEntity<GameViewModel> createGame(@RequestBody GameViewModel gameViewModel){
        return ResponseEntity.ok(createGameUseCase.execute(gameViewModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameViewModel> updateGame(@PathVariable Long id, @RequestBody GameViewModel gameViewModel){
        return ResponseEntity.ok(updateGameUseCase.execute(id, gameViewModel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameViewModel> getGame(@PathVariable Long id){
        return ResponseEntity.ok(getGameUseCase.execute(id));
    }

    @GetMapping()
    public ResponseEntity<List<GameViewModel>> getGames(){
        return ResponseEntity.ok(getGamesUseCase.execute());
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id){
        deleteGameUseCase.execute(id);
    }
}
