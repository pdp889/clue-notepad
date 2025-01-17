package com.games.clue_notepad.utils;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ListUtil {
    public static <T extends Enum<T>> List<List<T>> copy(List<List<T>> original) {
        List<List<T>> copy = new ArrayList<>(original.size());
        for (List<T> innerList : original) {
            copy.add(new ArrayList<>(innerList));
        }
        return copy;
    }
    public static <T extends Enum<T>> boolean deepEquals(List<List<T>> list1, List<List<T>> list2) {

        for (int i = 0; i < list1.size(); i++) {
            List<T> innerList1 = list1.get(i);
            List<T> innerList2 = list2.get(i);

            for (int j = 0; j < innerList1.size(); j++) {
                if (!innerList1.get(j).equals(innerList2.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
