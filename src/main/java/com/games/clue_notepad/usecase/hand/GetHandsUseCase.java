package com.games.clue_notepad.usecase.hand;

import com.games.clue_notepad.models.hand.Hand;
import com.games.clue_notepad.services.hand.HandService;
import com.games.clue_notepad.web.hand.HandViewModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetHandsUseCase {

    private final HandService handService;

    @Transactional
    public List<HandViewModel> execute(Long gameId){
        List<Hand> hands = handService.getByGameId(gameId);
        return hands.stream()
                .sorted(Comparator.comparing(Hand::getId))
                .map(HandMapper::toViewModel).toList();
    }
}
