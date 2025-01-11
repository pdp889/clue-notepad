package com.games.clue_notepad.web.hand;

import com.games.clue_notepad.usecase.hand.CreateHandUseCase;
import com.games.clue_notepad.usecase.hand.DeleteHandUseCase;
import com.games.clue_notepad.usecase.hand.GetHandUseCase;
import com.games.clue_notepad.usecase.hand.UpdateHandUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hand")
public class HandController {


    private final CreateHandUseCase createHandUseCase;
    private final DeleteHandUseCase deleteHandUseCase;
    private final GetHandUseCase getHandUseCase;
    private final UpdateHandUseCase updateHandUseCase;

    @PostMapping("/{gameId}")
    public ResponseEntity<HandViewModel> createHand(@PathVariable Long gameId, @RequestBody HandViewModel handViewModel){
        return ResponseEntity.ok(createHandUseCase.execute(gameId, handViewModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HandViewModel> updateHand(@PathVariable Long id, @RequestBody HandViewModel handViewModel){
        return ResponseEntity.ok(updateHandUseCase.execute(id, handViewModel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HandViewModel> getHand(@PathVariable Long id){
        return ResponseEntity.ok(getHandUseCase.execute(id));
    }

    @DeleteMapping("/{id}")
    public void deleteHand(@PathVariable Long id){
        deleteHandUseCase.execute(id);
    }

    @GetMapping("/")
    public String get(){
        return "success";
    }
}
