package com.games.clue_notepad.models.card;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;


public enum CardType {

    WHITE(CardCategory.PERSON, "Miss Scarlet"),
    MUSTARD(CardCategory.PERSON, "Colonel Mustard"),
    GREEN(CardCategory.PERSON, "Mr. Green"),
    PEACOCK(CardCategory.PERSON, "Mrs. Peacock"),
    PLUM(CardCategory.PERSON, "Professor Plum"),
    SCARLET(CardCategory.PERSON, "Mrs. Peacock"),

    CANDLESTICK(CardCategory.WEAPON, "Candlestick"),
    KNIFE(CardCategory.WEAPON, "Knife"),
    REVOLVER(CardCategory.WEAPON, "Revolver"),
    ROPE(CardCategory.WEAPON, "Rope"),
    PIPE(CardCategory.WEAPON, "Pipe"),
    WRENCH(CardCategory.WEAPON, "Wrench"),

    DINING_ROOM(CardCategory.ROOM, "Dining Room"),
    KITCHEN(CardCategory.ROOM, "Kitchen"),
    BALLROOM(CardCategory.ROOM, "Ballroom"),
    CONSERVATORY(CardCategory.ROOM, "Conservatory"),
    BILLIARD_ROOM(CardCategory.ROOM, "Billiard Room"),
    LIBRARY(CardCategory.ROOM, "Library"),
    STUDY(CardCategory.ROOM, "Study"),
    HALL(CardCategory.ROOM, "Hall"),
    LOUNGE(CardCategory.ROOM, "Lounge");

    @Getter
    private final CardCategory category;

    @Getter
    private final String label;

    CardType(CardCategory category, String label) {
        this.category = category;
        this.label = label;
    }

    public static List<CardType> getCardTypesForCategory(CardCategory category){
        return Arrays.stream(CardType.values()).filter(type -> category.equals(type.getCategory())).toList();
    }
}
