package com.games.clue_notepad.services.hand;

import com.games.clue_notepad.models.game.Game;
import com.games.clue_notepad.models.hand.Hand;
import com.games.clue_notepad.repos.hand.HandRepo;
import com.games.clue_notepad.web.hand.HandViewModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HandService {
    private final HandRepo handRepo;
    public void delete(Long id){
        handRepo.deleteById(id);
    }

    public Hand getById(Long id){
        return handRepo.findById(id).orElseThrow();
    }

    public List<Hand> getByGameId(Long id){
        return handRepo.findAllByGameId(id);
    }

    public Hand create(HandViewModel handViewModel, Game game){
        Hand hand = Hand.builder().game(game).build();
        return handRepo.save(sharedSave(handViewModel, hand));
    }

    public Hand update(Long id, HandViewModel handViewModel){
        Hand hand = getById(id);
        return sharedSave(handViewModel, hand);
    }

    public Hand sharedSave(HandViewModel handViewModel, Hand hand){
        hand.setPlayerName(handViewModel.getPlayerName());
        hand.setCardCount(handViewModel.getCardCount());
        hand.setCards(new HashSet<>(handViewModel.getCards()));

        return hand;
    }
}
