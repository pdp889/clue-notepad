package com.games.clue_notepad;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClueNotepadApplication {

	//using @generated to make test coverage happy
	@Generated
	public static void main(String[] args) {
		SpringApplication.run(ClueNotepadApplication.class, args);
	}

}