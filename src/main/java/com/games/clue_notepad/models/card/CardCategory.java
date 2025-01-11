package com.games.clue_notepad.models.card;

import lombok.Getter;

public enum CardCategory {
    PERSON("Person"), WEAPON("Weapon"), ROOM("Room");

    @Getter
    private final String label;

    CardCategory(String label){
        this.label = label;
    }
}
