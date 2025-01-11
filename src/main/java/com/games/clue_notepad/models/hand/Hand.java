package com.games.clue_notepad.models.hand;

import com.games.clue_notepad.models.card.Card;
import com.games.clue_notepad.models.game.Game;
import com.games.clue_notepad.models.question.Question;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@Table(name = "hand")
@NoArgsConstructor
@AllArgsConstructor
public class Hand {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @Builder.Default
    @OneToMany(mappedBy = "hand", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Card> cards = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    Game game;

    @Builder.Default
    @OneToMany(mappedBy = "hand", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Question> questions = new HashSet<>();

    String playerName;

    Integer cardCount;

    public void addCard(Card card) {
        card.setHand(this);
        cards.add(card);
    }
    public void addQuestion(Question question) {
        question.setHand(this);
        questions.add(question);
    }

}