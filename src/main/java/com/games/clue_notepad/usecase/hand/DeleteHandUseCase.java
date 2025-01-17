package com.games.clue_notepad.usecase.hand;

import com.games.clue_notepad.services.hand.HandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteHandUseCase {
    private final HandService handService;

    public void execute(Long id){
        handService.delete(id);
    }
}
