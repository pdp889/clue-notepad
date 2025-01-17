package com.games.clue_notepad.services.board;

import com.games.clue_notepad.models.board.Board;
import com.games.clue_notepad.models.board.CellStatus;
import com.games.clue_notepad.models.card.CardCategory;
import com.games.clue_notepad.models.card.CardType;
import com.games.clue_notepad.models.game.Game;
import com.games.clue_notepad.models.hand.Hand;
import com.games.clue_notepad.models.question.Question;
import com.games.clue_notepad.services.game.GameService;
import com.games.clue_notepad.usecase.board.BoardBacktrackOperation;
import com.games.clue_notepad.utils.ListUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.games.clue_notepad.models.card.CardType.getCardTypesForCategory;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final GameService gameService;
    public Board fillBoard(Long gameId){
        Game game = gameService.getById(gameId);

        List<Hand> sortedHands = game.getHands().stream()
                .sorted(Comparator.comparing(hand -> hand.getId() == null ? Long.MIN_VALUE : hand.getId()))
                .toList();

        Board board = new Board(sortedHands);

        //add basic knowledge from hands
        for (Hand hand : sortedHands){
            for (CardType cardType : hand.getCards()) {
                board.setCell(cardType, hand, CellStatus.YES);
            }
        }

        //add basic knowledge from questions
        for (Question question : game.getQuestions()) {
            if (question.getCardTypeShown() != null) {
                board.setCell(question.getCardTypeShown(), question.getHand(), CellStatus.YES);
            } else if (!question.isShowingCard()){
                for (CardType cardType : question.getCardTypes()) {
                    board.setCell(cardType, question.getHand(), CellStatus.NO);
                }
            }
        }

        List<List<CellStatus>> cells;

        //iterate over this to capture inferences we can deduce from other inferences
        do {
            cells = ListUtil.copy(board.getCells());
            doInferences(board, game);
        } while(!ListUtil.deepEquals(cells, board.getCells()));

        return board;
    }

    public void doInferences(Board board, Game game){
        doCardCountInferences(board);
        doEnvelopeInferences(board);
        doBasicQuestionInferences(board, game);
        doQuestionBacktracking(board, game);
    }

    public void doBasicQuestionInferences(Board board, Game game){
        List<Question> questionsAffectingInferences = getFilteredListOfQuestionsThatAffectInferences(game.getQuestions(), board);

        //cover situation where a card was shown to someone else, and we've eliminated 2 of the 3 possibilities so we know the 1 shown
        questionsAffectingInferences.forEach(question -> {
            Set<CardType> possibilities = getCardShownPossibilities(question, board);
            if (possibilities.size() == 1) {
                board.setCell(possibilities.stream().findAny().get(), question.getHand(), CellStatus.YES);
            }
        });
    }

    public void doQuestionBacktracking(Board board, Game game){
        List<Question> questionsAffectingInferences = getFilteredListOfQuestionsThatAffectInferences(game.getQuestions(), board);

        Map<Hand, Set<Question>> handQuestionSetMap = questionsAffectingInferences.stream().collect(Collectors.groupingBy(Question::getHand, Collectors.toSet()));

        for (Hand hand : handQuestionSetMap.keySet()){

            //this is a count of the cards in the player's hand that we don't know what they are
            int cardsRemaining = hand.getCardCount() - board.cardCountByStatus(hand, CellStatus.YES);

            Set<Question> questionsAffectingInferencesHand = handQuestionSetMap.get(hand);

            //create a set of sets of all the card possibilities where the player showed someone else a card, and eliminate cards we know they don't have.
            Set<Set<CardType>> cardTypes = questionsAffectingInferencesHand
                    .stream()
                    .map(question -> getCardShownPossibilities(question, board))
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toSet());

            //if they have less cards remaining then we have possibilities, we want to see if there is exactly 1 solution that explains the situation
            //for example, a player has 2 cards remaining. they showed cards to someone else on the following 3 questions: [A,B,C], [A,D,E], [B,F,G]. We know they must have A and B, otherwise they couldn't have showed a card everytime.
            //Alternatively, if the questions were [A,B,C], [A,B,D], and [B,D,C], we couldn't know what cards they had (could have [B], [C,A], [B,C] etc)
            if (cardsRemaining < cardTypes.size()) {
                Optional<Set<CardType>> oneValidSolutionOptional = BoardBacktrackOperation.findOneValidSolutionOptional(cardTypes, cardsRemaining);
                oneValidSolutionOptional.ifPresent(types -> types.forEach(cardType -> board.setCell(cardType, hand, CellStatus.YES)));
            }
        }
    }

    public void doCardCountInferences(Board board){
        for (Hand hand : board.getHands()) {
            //cover situation where we know all other cells must be NO because we've identified all cards in their hand
            if (hand.getCardCount().equals(board.cardCountByStatus(hand, CellStatus.YES))){
                for (CardType cardType : CardType.values()){
                    if (board.getCell(cardType, hand) != CellStatus.YES) {
                        board.setCell(cardType, hand, CellStatus.NO);
                    }
                }
            }

            //cover situation where we know what the remaining unknown cards in their hand must be
            int cellsInYesStatus = board.cardCountByStatus(hand, CellStatus.YES);
            int cellsInUnknownStatus = board.cardCountByStatus(hand, CellStatus.UNKNOWN);
            if (hand.getCardCount().equals(cellsInYesStatus + cellsInUnknownStatus)){
                for (CardType cardType : CardType.values()){
                    if (board.getCell(cardType, hand) == CellStatus.UNKNOWN) {
                        board.setCell(cardType, hand, CellStatus.YES);
                    }
                }
            }
        }
    }

    public void doEnvelopeInferences(Board board){
        Hand envelopeHand = board.getHands().get(0);

        //cover situation where all player cells are NO
        for (CardType cardType : CardType.values()){

            boolean envelopeIsUnknownForCardType = CellStatus.UNKNOWN.equals(board.getCell(cardType, envelopeHand));
            boolean allOtherCellsAreNo = true;

            if (envelopeIsUnknownForCardType){
                for (int i = 1; i < board.getHands().size(); i++) {
                    Hand hand = board.getHands().get(i);
                    CellStatus cellStatus = board.getCell(cardType, hand);
                    if (cellStatus != CellStatus.NO) {
                        allOtherCellsAreNo = false;
                    }
                }
            }
            if (envelopeIsUnknownForCardType && allOtherCellsAreNo) {
                board.setCell(cardType, envelopeHand, CellStatus.YES);
            }
        }

        //cover situation where we've eliminated all other options for the envelope in that category
        for (CardCategory category : CardCategory.values()) {
            List<CardType> unknownCardTypesInCategory = getCardTypesForCategory(category).stream().filter(cardType -> board.getCell(cardType, envelopeHand) == CellStatus.UNKNOWN).toList();
            if (unknownCardTypesInCategory.size() == 1){
                board.setCell(unknownCardTypesInCategory.stream().findFirst().get(), envelopeHand, CellStatus.YES);
            }

        }
    }

    private Set<CardType> getCardShownPossibilities(Question question, Board board){
        //we've eliminated questions that have a card with a yes value before this step
        return question.getCardTypes().stream().filter(cardType ->  board.getCell(cardType, question.getHand()) == CellStatus.UNKNOWN).collect(Collectors.toSet());
    }

    private List<Question> getFilteredListOfQuestionsThatAffectInferences(Set<Question> questions, Board board){
        return questions.stream()
                .filter(q -> q.isShowingCard() && q.getCardTypeShown() == null)
                .filter(q -> q.getCardTypes().stream().noneMatch(c -> board.getCell(c, q.getHand()) == CellStatus.YES))
                .toList();
    }
}
