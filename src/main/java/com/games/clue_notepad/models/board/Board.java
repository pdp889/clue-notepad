package com.games.clue_notepad.models.board;

import com.games.clue_notepad.models.card.CardType;
import com.games.clue_notepad.models.hand.Hand;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class Board {

    public static final String ENVELOPE_NAME = "Envelope";

    @Builder.Default
    List<Hand> hands = new ArrayList<>();

    private List<List<CellStatus>> cells;
    public Board(List<Hand> handList){
        Hand envelope = Hand.builder().playerName(ENVELOPE_NAME).cardCount(3).build();

        this.hands = new ArrayList<>();
        this.hands.add(envelope);
        this.hands.addAll(handList);

        this.cells = new ArrayList<>();
        for (int i = 0; i < CardType.values().length; i++) {
            List<CellStatus> row = new ArrayList<>();
            for (int j = 0; j < this.hands.size(); j++){
                row.add(CellStatus.UNKNOWN);
            }
            this.cells.add(row);
        }
    }

    public CellStatus getCell(CardType cardType, Hand hand){
        int handIndex = hands.indexOf(hand);
        return this.cells.get(cardType.ordinal()).get(handIndex);
    }

    public void setCell(CardType cardType, Hand hand, CellStatus val){
        int handIndex = hands.indexOf(hand);

        if (val == CellStatus.YES) {
            for (int i = 0; i < hands.size(); i++) {
                if (i != handIndex) {
                    this.cells.get(cardType.ordinal()).set(i, CellStatus.NO);
                }
            }
        }

        this.cells.get(cardType.ordinal()).set(handIndex, val);
    }

    public int cardCountByStatus(Hand hand, CellStatus cellStatus){
        int handIndex = hands.indexOf(hand);
        int i = 0;
        for (CardType cardType : CardType.values()){
            if(this.cells.get(cardType.ordinal()).get(handIndex) == cellStatus){
                i++;
            }
        }
        return i;
    }
}
