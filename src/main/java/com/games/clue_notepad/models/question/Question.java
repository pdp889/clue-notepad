package com.games.clue_notepad.models.question;

import com.games.clue_notepad.models.card.CardType;
import com.games.clue_notepad.models.game.Game;
import com.games.clue_notepad.models.hand.Hand;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@Table(name = "question")
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hand_id")
    Hand hand; //Hand being asked question

    @ElementCollection(targetClass = CardType.class)
    @CollectionTable(name = "question_card_types", joinColumns = @JoinColumn(name = "question_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "card_type")
    @Builder.Default
    Set<CardType> cardTypes = new HashSet<>();

    boolean showingCard;

    @Enumerated(EnumType.STRING)
    CardType cardTypeShown;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    Game game;

}
