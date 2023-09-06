package game.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable {
    private int triesLeft;
    private List<Character> lettersTried;
    private int correctWordsStreak;
    private String wordPresented;

    public GameState(Word newCurrentWord) {
        this.triesLeft = 10;
        this.lettersTried = new ArrayList<>();
        this.correctWordsStreak = 0;
        changeWordPresented(newCurrentWord);
    }

    public int getTriesLeft() {
        return triesLeft;
    }

    public void setTriesLeft(int triesLeft) {
        this.triesLeft = triesLeft;
    }

    public List<Character> getLettersTried() {
        return lettersTried;
    }

    public void setLettersTried(List<Character> lettersTried) {
        this.lettersTried = lettersTried;
    }

    public int getCorrectWordsStreak() {
        return correctWordsStreak;
    }

    public void setCorrectWordsStreak(int correctWordsStreak) {
        this.correctWordsStreak = correctWordsStreak;
    }

    public String getWordPresented() { return wordPresented; }

    public void setWordPresented(String wordPresented) { this.wordPresented = wordPresented; }

    public void changeWordPresented(Word newCurrentWord){
        int currentWordSize = newCurrentWord.getWord().length();
        StringBuilder wordBuilder = new StringBuilder("");
        for (int i = 0; i < currentWordSize; i++){
            wordBuilder.append('_');
        }
        this.wordPresented = wordBuilder.toString();
    }

}
