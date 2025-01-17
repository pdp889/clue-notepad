package com.games.clue_notepad.usecase.board;

import com.games.clue_notepad.models.card.CardType;
import lombok.experimental.UtilityClass;
import java.util.*;

@UtilityClass
public class BoardBacktrackOperation {

    public static Optional<Set<CardType>> findOneValidSolutionOptional(Set<Set<CardType>> questionCardSets, int numCards) {
        List<List<CardType>> questionCardLists = questionCardSets.stream().map(s -> s.stream().toList()).toList();
        Set<Set<CardType>> solutions = new HashSet<>();

        boolean foundMultipleSolutions  = backtrack(solutions, numCards, 0, new ArrayList<>(), questionCardLists);

        if (foundMultipleSolutions  || solutions.isEmpty()){
            return Optional.empty();
        }

        return solutions.stream().findAny();
    }

    private static boolean backtrack(Set<Set<CardType>> solutions, int numCards, int start, List<CardType> current, List<List<CardType>> questionCardLists) {
        Set<CardType> currentSet = new HashSet<>(current);
        if (currentSet.size() == numCards) {
            //we've hit a possible path of the correct size, we want to check if it is a valid solution for all the questionCardLists we're comparing against
            if (isValid(current, questionCardLists)) {
                solutions.add(currentSet);
            }

            //if we have more than 1, since we only care about this if there's exactly 1 valid solution
            return solutions.size() > 1;
        }

        for (int i = start; i < questionCardLists.size(); i++){
            //take the next set of questionCards
            List<CardType> questionCards = questionCardLists.get(i);

            for (int j = 0; j < questionCards.size(); j++){
                //backtrack down the options in this cardList

                current.add(questionCards.get(j));

                boolean foundMultipleSolutions  = backtrack(solutions, numCards, i+1, current, questionCardLists);

                if (foundMultipleSolutions) {
                    return true;
                }

                current.remove(current.size() - 1);
            }
        }
        return false;
    }

    private static boolean isValid(Collection<CardType> combination, List<List<CardType>> questionCardLists){
        for (Collection<CardType> questionCardList : questionCardLists) {
            //if any questionCardList does not contain a value in this combination, we know its invalid
            if (combination.stream().noneMatch(questionCardList::contains)) {
                return false;
            }
        }
        return true;
    }
}
