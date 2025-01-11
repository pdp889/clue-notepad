package com.games.clue_notepad.models.card;

import lombok.Getter;


public enum CardType {
    WHITE(CardCategory.PERSON), CANDLESTICK(CardCategory.WEAPON), DINING_ROOM(CardCategory.ROOM);



    @Getter
    private CardCategory category;

    CardType(CardCategory category){
        this.category = category;
    }
}
