package com.games.clue_notepad.web.cardtype;

import com.games.clue_notepad.usecase.cardtype.GetCardCategoriesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/card")
public class CardTypeController {
    private final GetCardCategoriesUseCase getCardCategoriesUseCase;

    @GetMapping("/categories")
    public ResponseEntity<List<CardCategoryViewModel>> getCardTypesByCategory(){
        return ResponseEntity.ok(getCardCategoriesUseCase.execute());
    }
}
