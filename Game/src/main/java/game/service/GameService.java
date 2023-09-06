package game.service;

import game.characterComparison.CharacterComparator;
import game.model.GameState;
import game.model.Word;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {
    public static final String GAME_STATE_SESSION_ATTRIBUTE = "gameState";
    public static final String CURRENT_WORD_SESSION_ATTRIBUTE = "currentWord";
    @Autowired
    WordService wordService;
    @Autowired
    CharacterComparator characterComparator;

    public GameState guess(Character letter, HttpSession session){
        GameState gameState = (GameState) session.getAttribute(GAME_STATE_SESSION_ATTRIBUTE);
        Word currentWord = (Word) session.getAttribute(CURRENT_WORD_SESSION_ATTRIBUTE);
        StringBuilder newWordBuilder = new StringBuilder("");
        int hits = 0;
        int misses = 0;
        letter = Character.toLowerCase(letter);
        validateGuess(letter, gameState.getLettersTried());
        gameState.getLettersTried().add(letter);
        char[] presentedWordCharArray = gameState.getWordPresented().toCharArray();
        char[] currentWordCharArray = currentWord.getWord().toCharArray();
        for (int index = 0; index < currentWordCharArray.length; index++){
            if (presentedWordCharArray[index] == '_'){
                Character wordLetter = currentWordCharArray[index];
                wordLetter = Character.toLowerCase(wordLetter);
                if (letterMatch(letter,wordLetter)){
                    newWordBuilder.append(letter);
                    hits++;
                }
                else {
                    newWordBuilder.append(presentedWordCharArray[index]);
                    misses++;
                }
            }
            else {
                newWordBuilder.append(presentedWordCharArray[index]);
            }
        }
        gameState.setWordPresented(newWordBuilder.toString());
        session.setAttribute(GAME_STATE_SESSION_ATTRIBUTE, gameState);
        if (hits == 0){
            handleMissedGuess(session, gameState);
        }
        else {
            if (misses == 0){
                handleGuessedWord(session, gameState);
            }
        }
        gameState = (GameState) session.getAttribute(GAME_STATE_SESSION_ATTRIBUTE);
        return gameState;
    }

    private void validateGuess(Character guessedLetter, List<Character> lettersTried){
        if (guessedLetter < 'a' || guessedLetter > 'z'){
            throw new IllegalArgumentException("Invalid letter guessed.");
        }
        if (lettersTried.contains(guessedLetter)){
            throw new IllegalArgumentException("Invalid letter guessed.");
        }
    }

    private boolean letterMatch(Character guessedLetter, Character wordLetter){
        if (guessedLetter.equals(wordLetter)){
            return true;
        }
        return characterComparator.specialCaseCharacterComparison(guessedLetter, wordLetter);
    }

    public GameState routeAccess(HttpSession session) {
        boolean unfilledSession = session.getAttribute(GAME_STATE_SESSION_ATTRIBUTE) == null ||
                session.getAttribute(CURRENT_WORD_SESSION_ATTRIBUTE) == null;
        if (session.isNew() || unfilledSession){
            startNewGame(session);
        }
        return (GameState) session.getAttribute(GAME_STATE_SESSION_ATTRIBUTE);
    }

    private void handleMissedGuess(HttpSession session, GameState gameState){
        gameState.setTriesLeft(gameState.getTriesLeft() - 1);
        if (gameState.getTriesLeft() <= 0){
            startNewGame(session);
        }
    }

    public void startNewGame(HttpSession session){
        getNewWord(session);
        Word newCurrentWord = (Word) session.getAttribute(CURRENT_WORD_SESSION_ATTRIBUTE);
        session.setAttribute(GAME_STATE_SESSION_ATTRIBUTE, new GameState(newCurrentWord));
    }

    private void handleGuessedWord(HttpSession session, GameState gameState){
        gameState.setCorrectWordsStreak(gameState.getCorrectWordsStreak() + 1);
        gameState.setLettersTried(new ArrayList<>());
        getNewWord(session);
        Word newCurrentWord = (Word) session.getAttribute(CURRENT_WORD_SESSION_ATTRIBUTE);
        gameState.changeWordPresented(newCurrentWord);
        session.setAttribute(GAME_STATE_SESSION_ATTRIBUTE, gameState);
    }

    private void getNewWord(HttpSession session){
        Word newCurrentWord = wordService.getRandomWord();
        session.setAttribute(CURRENT_WORD_SESSION_ATTRIBUTE, newCurrentWord);
    }

}
