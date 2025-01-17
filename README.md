# Clue Notepad

A Spring Boot application that serves as a digital assistant for playing Clue. Only meant to be run locally, so no users, tokens, CORS, etc are implemented.

## Technical Stack

* Java
* Spring Boot
* Hibernate
* Flyway Database Migration
* HSQLDB (In-memory database)

## Features

* Tracks cards, hands, and questions
* Automatically deduces the best most complete possible board through several inference steps
* 100% Test Coverage

## Core Components

* `BoardService` - Handles deduction rules
* `BoardBacktrackOperation` - Handles complex deduction possible through backtracking

## Running the Application

### Prerequisites
* Java 21 or higher
* Gradle (or use the included Gradle wrapper)

### Steps to Run
1. Clone the repository
2. Navigate to the project root directory
3. Build and run the application using Gradle:

Using Gradle wrapper (recommended):
```bash
./gradlew bootRun
