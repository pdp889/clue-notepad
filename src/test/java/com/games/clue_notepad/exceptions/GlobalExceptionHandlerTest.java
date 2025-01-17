package com.games.clue_notepad.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class GlobalExceptionHandlerTest {
    @Autowired
    GlobalExceptionHandler globalExceptionHandler;
    @Test
    void testGlobalExceptionHandler(){
        String details = "Error";
        GlobalExceptionHandler.ErrorResponse response = globalExceptionHandler.handleRuntimeException(new RuntimeException(details)).getBody();
        assertThat(response.getDetails()).isEqualTo(details);
        assertThat(response.getMessage()).isEqualTo(GlobalExceptionHandler.MESSAGE);
    }
}
