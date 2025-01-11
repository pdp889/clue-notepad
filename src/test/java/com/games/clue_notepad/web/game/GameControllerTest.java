package com.games.clue_notepad.web.game;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class GameControllerTest {
    @Autowired
    GameController gameController;

    @Test
    void testGameControllerFunctions(){
        GameViewModel gameViewModel = gameController.createGame(GameViewModel.builder().name("Game 1").build()).getBody();
        GameViewModel gameViewModel2 = gameController.createGame(GameViewModel.builder().name("Game 2").build()).getBody();

        assertThat(gameViewModel.getName()).isEqualTo("Game 1");
        assertThat(gameViewModel.getId()).isNotNull();
        assertThat(gameViewModel2.getName()).isEqualTo("Game 2");
        assertThat(gameViewModel2.getId()).isNotNull();

        GameViewModel gameViewModel1 = gameController.getGame(gameViewModel.getId()).getBody();
        assertThat(gameViewModel1.getName()).isEqualTo("Game 1");

        GameViewModel gameViewModel3 = gameController.updateGame(gameViewModel2.getId(), GameViewModel.builder().name("Game 2.1").build()).getBody();
        AssertionsForClassTypes.assertThat(gameViewModel3.getName()).isEqualTo("Game 2.1");

        GameViewModel gameViewModel4 = gameController.getGame(gameViewModel2.getId()).getBody();
        assertThat(gameViewModel4.getName()).isEqualTo("Game 2.1");


        Long deleteId = gameViewModel.getId();
        gameController.deleteGame(deleteId);
        assertThrows(NoSuchElementException.class, () -> gameController.getGame(deleteId));
    }
}
