package com.games.clue_notepad.services.hand;

import com.games.clue_notepad.models.card.Card;
import com.games.clue_notepad.models.game.Game;
import com.games.clue_notepad.models.hand.Hand;
import com.games.clue_notepad.repos.hand.HandRepo;
import com.games.clue_notepad.web.hand.CardViewModel;
import com.games.clue_notepad.web.hand.HandViewModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        hand.getCards().clear();

        handViewModel.getCards().stream()
                .map(this::toModel)
                .forEach(hand::addCard);

        return hand;
    }

    public Card toModel(CardViewModel cardViewModel){
        return Card.builder().cardType(cardViewModel.getCardType()).build();
    }
}
