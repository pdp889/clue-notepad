package com.games.clue_notepad.web.game;

import com.games.clue_notepad.models.board.CellStatus;
import com.games.clue_notepad.models.card.CardType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class BoardViewModel {
    @Builder.Default
    List<CardType> rows = List.of(CardType.values());

    @Builder.Default
    List<ColumnViewModel> columns = new ArrayList<>();

    @Builder.Default
    List<List<CellStatus>> cells = new ArrayList<>();
}
