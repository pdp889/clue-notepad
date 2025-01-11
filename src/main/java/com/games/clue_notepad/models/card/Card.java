package com.games.clue_notepad.models.card;

import com.games.clue_notepad.models.hand.Hand;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card")
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    CardType cardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hand_id")
    Hand hand;
}
