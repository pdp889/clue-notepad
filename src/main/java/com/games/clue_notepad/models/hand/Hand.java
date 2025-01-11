package com.games.clue_notepad.models.hand;

import com.games.clue_notepad.models.card.Card;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Hand {
    List<Card> cards;

    String playerName;
}