package com.games.clue_notepad.web.game;

import com.games.clue_notepad.usecase.game.CreateGameUseCase;
import com.games.clue_notepad.usecase.game.DeleteGameUseCase;
import com.games.clue_notepad.usecase.game.GetGameUseCase;
import com.games.clue_notepad.usecase.game.UpdateGameUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/game")
public class GameController {
    private final CreateGameUseCase createGameUseCase;
    private final DeleteGameUseCase deleteGameUseCase;
    private final UpdateGameUseCase updateGameUseCase;
    private final GetGameUseCase getGameUseCase;
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

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id){
        deleteGameUseCase.execute(id);
    }
}
