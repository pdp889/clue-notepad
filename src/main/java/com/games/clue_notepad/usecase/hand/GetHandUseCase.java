package com.games.clue_notepad.usecase.hand;

import com.games.clue_notepad.models.hand.Hand;
import com.games.clue_notepad.services.hand.HandService;
import com.games.clue_notepad.web.hand.HandViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetHandUseCase {

    private final HandService handService;

    public HandViewModel execute(Long id){
        Hand hand = handService.getById(id);
        return HandMapper.toViewModel(hand);
    }
}
